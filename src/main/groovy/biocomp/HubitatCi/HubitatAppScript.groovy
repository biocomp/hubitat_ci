package biocomp.hubitatCi

import biocomp.hubitatCi.apppreferences.AppPreferencesReader
import biocomp.hubitatCi.apppreferences.Preferences
import biocomp.hubitatCi.validation.Validator
import biocomp.hubitatCi.emulation.appApi.AppExecutor
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/* Custom Script that redirects most unknown calls to app_, and does not use Binding.
* */

@TypeChecked
abstract class HubitatAppScript extends Script
{
    private Map settingsMap
    private AppPreferencesReader preferencesReader = null
    private AppDefinitionReader definitionReader = null
    private AppMappingsReader mappingsReader = null
    private Validator validator = null

    @Delegate
    private AppExecutor api = null

    @TypeChecked
    @CompileStatic
    void initialize(HubitatAppScript parent)
    {
        this.api = parent.@api
        this.preferencesReader = parent.@preferencesReader
        this.definitionReader = parent.@definitionReader
        this.mappingsReader = parent.@mappingsReader
        this.settingsMap = parent.@settingsMap
    }

    private Map<String, Object> injectedMappingHandlerData = [:]
    @CompileStatic
    void installMappingInjectedProps(def params, def request)
    {
        injectedMappingHandlerData['params'] = params
        injectedMappingHandlerData['request'] = request
    }

    void initialize(AppExecutor api, Validator validator, Map userSettingValues, Closure customizeScriptBeforeRun)
    {
        customizeScriptBeforeRun?.call(this)

        this.preferencesReader = new AppPreferencesReader(this, api, validator, userSettingValues)
        api = this.preferencesReader;

        this.definitionReader = new AppDefinitionReader(api, validator)
        api = this.definitionReader

        this.mappingsReader = new AppMappingsReader(api, this, validator)
        api = mappingsReader

        this.api = api
        this.settingsMap = preferencesReader.getSettings()

        this.validator = validator
    }

    Preferences getProducedPreferences()
    {
        preferencesReader.getProducedPreferences()
    }

    Map<String, Object> getProducedDefinition()
    {
        definitionReader.getDefinitions()
    }

    Map<String, MappingPath> getProducedMappings()
    {
        mappingsReader.getMappings()
    }

    AppMappingsReader getMappingsReader()
    {
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
            case "metaClass":
                return getMetaClass();

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

        def methodName = "get${property.capitalize()}"
        try {
            // Simple implementation of redirecting getter back to script class (if present)
            // Don't need to support MOP here, everything can be mocked via AppExecutorBase interface.
            def getter = this.getClass().getMethod(methodName, new Class[0])
            //System.out.println "FOUND getter ${methodName}!"
            return getter.invoke(this);
        }
        catch (NoSuchMethodException)
        {
            // It's OK, let's hope it'll be found in metaclass
        }

        if (!getMetaClass().hasProperty(this, property)) {
            return this.@settingsMap.get(property)
        }

        return getMetaClass().getProperty(this as GroovyObjectSupport, property)
    }

    /*
        Don't let Script base class to redirect properties to binding
        + handle special cases of 'params' and 'props'
    */
    @Override
    @CompileStatic
    void setProperty(String property, Object newValue) {
        switch (property)
        {
            case "metaClass":
                setMetaClass((MetaClass)newValue);
                return;

            case "params":
                if (this.@injectedMappingHandlerData != null) {
                    throw new ReadOnlyPropertyException("'params' injected value in mapping handler is for reading only", this.class)
                }

                break;

            case "request":
                if (this.@injectedMappingHandlerData != null) {
                    throw new ReadOnlyPropertyException(
                            "'request' injected value in mapping handler is for reading only", this.class)
                }

                break;
        }

        this.@settingsMap.put(property, newValue)
    }

    @Override
    def run()
    {
        scriptBody()
        validator.validateAfterRun(definitionReader, preferencesReader, mappingsReader)
    }

    abstract void scriptBody()
}
