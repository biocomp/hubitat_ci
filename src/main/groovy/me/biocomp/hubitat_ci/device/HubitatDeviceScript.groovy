package me.biocomp.hubitat_ci.device

import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.device.metadata.Definition
import me.biocomp.hubitat_ci.device.metadata.DeviceInput
import me.biocomp.hubitat_ci.device.metadata.DeviceMetadataReader
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.util.ScriptUtil
import me.biocomp.hubitat_ci.validation.DebuggerDetector

/**
 * Custom Script that redirects most unknown calls to app_, and does not use Binding.
 */
@TypeChecked
abstract class HubitatDeviceScript extends Script
{
    private Map userSettingsMap
    private DeviceMetadataReader metadataReader = null
    private DeviceValidator validator = null
    private DeviceData data = null

    @Delegate
    private DeviceExecutor api = null

    @CompileStatic
    void initialize(DeviceExecutor api, DeviceValidator validator, Map userSettingValues, Closure customizeScriptBeforeRun)
    {
        customizeScriptBeforeRun?.call(this)

        this.data = new DeviceData()

        this.metadataReader = new DeviceMetadataReader(api/*this, api*/, validator, userSettingValues, this.getMetaClass(), data, new DebuggerDetector())
        api = this.metadataReader

        this.api = api

        this.userSettingsMap = this.metadataReader.settings

        this.validator = validator
    }

    /**
     * Initialize with just SettingsContainer for stack trace detection
    */
    @CompileStatic
    void initializeForStackDetection(Map settingsContainer) {
        this.userSettingsMap = settingsContainer
    }

    @CompileStatic
    Definition getProducedDefinition()
    {
        metadataReader.getProducedDefinition()
    }

    @CompileStatic
    List<DeviceInput> getProducedPreferences()
    {
        return data.producedPreferences
    }

    /**
     * Call to this method is injected into every user's method.
     * This allows additional validations while calling separate methods on script object.
     */
    @CompileStatic
    void hubitatciValidateAfterMethodCall(String methodName)
    {
        //println "hubitatciValidateAfterMethodCall(${methodName} called!)"
        this.metadataReader.settings.validateAfterPreferences(methodName)
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
        ScriptUtil.handleSetProperty(property, this, newValue, this.@userSettingsMap)
    }

    @Override
    @CompileStatic
    def run()
    {
        scriptBody()
        validator.validateAfterRun(this.metadataReader)
    }

    abstract void scriptBody()
}
