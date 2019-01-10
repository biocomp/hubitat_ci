package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import groovy.transform.TypeChecked

/* Custom Script that redirects most unknown calls to app_, and does not use Binding.
* */

@TypeChecked
abstract class HubitatAppScript extends Script
{
    private static HashSet forbiddenMethods_ = ["println"] as HashSet

    SettingsProvider settingsMap

    private void ensureNotForbidden(String methodOrPropName) {
        //println "checking '$methodOrPropName'"
        if (this.@forbiddenMethods_.contains(methodOrPropName)) {
            //throw new SecurityException("Usage of '${methodOrPropName}' is forbidden inside Hubitat scripts")
        }
    }

    @Delegate
    AppExecutor api = null

    /*
        Don't let Script base class to redirect properties to binding,
            it causes confusing issues when using non-supported methods and properties.

        Also, getProperty() is still going to be called for valid get*() methods added by @Delegate.
        So we need to intercept those separately.
     */
    @Override
    Object getProperty(String property) {
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
            return this.@settingsMap.getSetting(property)
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
            this.@settingsMap.setSetting(property, newValue)
    }
}
