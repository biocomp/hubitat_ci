package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.app.preferences.AppPreferencesReader
import me.biocomp.hubitat_ci.app.preferences.Preferences
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.util.ScriptUtil
import me.biocomp.hubitat_ci.validation.DebuggerDetector

/*
    Custom Script that redirects most unknown calls to app_, and does not use Binding.
*/

@TypeChecked
abstract class HubitatAppScript extends
        Script
{
    private Map userSettingsMap
    private AppPreferencesReader preferencesReader = null
    private AppDefinitionReader definitionReader = null
    private AppMappingsReader mappingsReader = null
    private AppSubscriptionReader subscriptionReader = null
    final private List<Closure> validateAfterRun = []
    private AppValidator validator = null
    private final AppData data = new AppData()

    private final HashSet<String> existingMethods = InitExistingMethods()

    @Delegate
    private AppExecutor api = null

    @CompileStatic
    private static HashSet<String> InitExistingMethods() {
        return getMetaClass().methods.collect { m -> m.name } as HashSet<String>;
    }

    @TypeChecked
    @CompileStatic
    void initialize(HubitatAppScript parent) {
        this.api = parent.@api
        this.preferencesReader = parent.@preferencesReader
        this.definitionReader = parent.@definitionReader
        this.mappingsReader = parent.@mappingsReader
        this.userSettingsMap = parent.@userSettingsMap
    }

    private Map<String, Object> injectedMappingHandlerData = [:]

    @CompileStatic
    void installMappingInjectedProps(def params, def request) {
        injectedMappingHandlerData['params'] = params
        injectedMappingHandlerData['request'] = request
    }

    @CompileStatic
    void initialize(
            AppExecutor api,
            AppValidator validator,
            Map userSettingValues,
            Closure customizeScriptBeforeRun)
    {
        customizeScriptBeforeRun?.call(this)

        // This guy needs to be first - it checks if its api is null,
        // and then does nothing in subscribe().
        this.subscriptionReader = new AppSubscriptionReader(api, validator, data)
        api = this.subscriptionReader
        validateAfterRun.add(this.subscriptionReader.&initializationComplete)

        this.preferencesReader = new AppPreferencesReader(this,
                api,
                validator,
                userSettingValues,
                data.preferences,
                data,
                new DebuggerDetector())
        api = this.preferencesReader
        validateAfterRun.add(this.preferencesReader.&validateAfterRun)

        this.definitionReader = new AppDefinitionReader(api, validator.flags, data.definitions)
        api = this.definitionReader
        validateAfterRun.add(this.definitionReader.&validateAfterRun)

        this.mappingsReader = new AppMappingsReader(api, this, validator)
        api = this.mappingsReader
        validateAfterRun.add(this.mappingsReader.&validateAfterRun)

        validateAfterRun.add(validator.&validateAfterRun.curry(data))

        this.api = api
        this.userSettingsMap = preferencesReader.getSettings()

        this.validator = validator
    }

    /**Initialize with just SettingsContainer for stack trace detection*/
    @CompileStatic
    void initializeForStackDetection(Map settingsContainer) {
        this.userSettingsMap = settingsContainer
    }

    /**
     * Call to this method is injected into every user's method.
     * This allows additional validations while calling separate methods on script object.*/
    @CompileStatic
    void hubitatciValidateAfterMethodCall(String methodName) {
        this.preferencesReader.validateAfterMethodCall(methodName)
    }

    Preferences getProducedPreferences() {
        data.preferences
    }

    Map<String, Object> getProducedDefinition() {
        data.definitions
    }

    Map<String, MappingPath> getProducedMappings() {
        mappingsReader.getMappings()
    }

    AppMappingsReader getMappingsReader() {
        return mappingsReader
    }

    /*
        Don't let Script base class to redirect properties to binding,
            it causes confusing issues when using non-supported methods and properties.

        Also, getProperty() is still going to be called for valid get*() methods added by @Delegate.
        So we need to intercept those separately.
     */

    @Override
    @CompileStatic
    Object getProperty(String property) {
        switch (property) {
            case "params":
                if (this.@injectedMappingHandlerData != null) {
                    return this.@injectedMappingHandlerData['params']
                }

                break;

            case "request":
                if (this.@injectedMappingHandlerData != null) {
                    return this.@injectedMappingHandlerData['request']
                }

                break;

        // default: - continue processing below
        }

        return ScriptUtil.handleGetProperty(property, this, this.@userSettingsMap)
    }

    /*
        Don't let Script base class to redirect properties to binding
        + handle special cases of 'params' and 'props'
    */

    @Override
    @CompileStatic
    void setProperty(String property, Object newValue) {
        switch (property) {
            case "params":
                if (this.@injectedMappingHandlerData != null) {
                    throw new ReadOnlyPropertyException(
                            "'params' injected value in mapping handler is for reading only", this.class)
                }

                break;

            case "request":
                if (this.@injectedMappingHandlerData != null) {
                    throw new ReadOnlyPropertyException(
                            "'request' injected value in mapping handler is for reading only", this.class)
                }

                break;
        }

        ScriptUtil.handleSetProperty(property, this, newValue, this.@userSettingsMap)
    }

    @Override
    def run() {
        scriptBody()
        validateAfterRun.each { it() }
    }

    abstract void scriptBody()
}
