package me.biocomp.hubitat_ci.device

import biocomp.hubitatCi.api.device_api.DeviceExecutor
import biocomp.hubitatCi.device.metadata.Definition
import biocomp.hubitatCi.device.metadata.DeviceInput
import biocomp.hubitatCi.device.metadata.DeviceMetadataReader
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * Custom Script that redirects most unknown calls to app_, and does not use Binding.
 */
@TypeChecked
abstract class HubitatDeviceScript extends Script
{
    private Map settingsMap
    private DeviceMetadataReader metadataReader = null
    private DeviceValidator validator = null
    private DeviceData data = null

    @Delegate
    private DeviceExecutor api = null

//    @TypeChecked
//    @CompileStatic
//    void initialize(HubitatDeviceScript parent)
//    {
//        this.api = parent.@api
//        this.preferencesReader = parent.@preferencesReader
//        this.definitionReader = parent.@definitionReader
//        this.mappingsReader = parent.@mappingsReader
//        this.userSettingsMap = parent.@userSettingsMap
//    }

//    private Map<String, Object> injectedMappingHandlerData = [:]
//    @CompileStatic
//    void installMappingInjectedProps(def params, def request)
//    {
//        injectedMappingHandlerData['params'] = params
//        injectedMappingHandlerData['request'] = request
//    }

    @CompileStatic
    void initialize(DeviceExecutor api, DeviceValidator validator/*, Map userSettingValues, Closure customizeScriptBeforeRun*/)
    {
//        customizeScriptBeforeRun?.call(this)
//
        this.data = new DeviceData()

        this.metadataReader = new DeviceMetadataReader(api/*this, api*/, validator, this.getMetaClass()/*, userSettingValues*/, data)
        api = this.metadataReader

        this.api = api

        this.settingsMap = this.metadataReader.settings

        this.validator = validator
    }

//    Preferences getProducedPreferences()
//    {
//        metadataReader.getProducedPreferences()
//    }

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
        this.metadataReader.settings.validateAfterPreferences()
    }
//
//    Map<String, MappingPath> getProducedMappings()
//    {
//        mappingsReader.getMappings()
//    }
//
//    AppMappingsReader getMappingsReader()
//    {
//        return mappingsReader
//    }

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

//            case "params":
//                if (this.@injectedMappingHandlerData != null) {
//                    return this.@injectedMappingHandlerData['params']
//                }
//
//                break;
//
//            case "request":
//                if (this.@injectedMappingHandlerData != null) {
//                    return this.@injectedMappingHandlerData['request']
//                }
//
//                break;

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

//            case "params":
//                if (this.@injectedMappingHandlerData != null) {
//                    throw new ReadOnlyPropertyException("'params' injected value in mapping handler is for reading only", this.class)
//                }
//
//                break;
//
//            case "request":
//                if (this.@injectedMappingHandlerData != null) {
//                    throw new ReadOnlyPropertyException(
//                            "'request' injected value in mapping handler is for reading only", this.class)
//                }
//
//                break;
        }

        this.@settingsMap.put(property, newValue)
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
