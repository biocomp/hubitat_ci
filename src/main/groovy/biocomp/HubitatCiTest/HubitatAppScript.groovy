package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.apppreferences.Preferences
import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
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

    @TypeChecked
    void initialize(AppExecutor api, EnumSet<ValidationFlags> validationFlags, Map userSettingValues)
    {
        this.preferencesReader = new AppPreferencesReader(this, api, validationFlags, userSettingValues)
        api = this.preferencesReader;

        this.definitionReader = new AppDefinitionReader(api, validationFlags)
        api = this.definitionReader

        this.mappingsReader = new AppMappingsReader(api, this, validationFlags)
        api = mappingsReader

        this.api = api
        this.settingsMap = preferencesReader.getSettings()
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

    /*
        Don't let Script base class to redirect properties to binding,
            it causes confusing issues when using non-supported methods and properties.

        Also, getProperty() is still going to be called for valid get*() methods added by @Delegate.
        So we need to intercept those separately.
     */
    @Override
    Object getProperty(String property) {
        if (property == "metaClass")
        {
            return getMetaClass();
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
        Don't let Script base class to redirect properties to binding.
    */
    @Override
    void setProperty(String property, Object newValue) {
        if("metaClass".equals(property))
            setMetaClass((MetaClass)newValue);
        else
            this.@settingsMap.put(property, newValue)
    }

    @Override
    def run()
    {
        scriptBody()
        getProducedPreferences() // Just to trigger validation
        definitionReader.getDefinitions() // Trigger definition validation
    }

    abstract void scriptBody()
}
