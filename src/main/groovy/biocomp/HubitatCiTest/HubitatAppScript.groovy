package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.AppExecutorApi
import groovy.transform.AutoImplement

/* Custom Script that redirects most unknown calls to app_, and does not use Binding.
* */
@AutoImplement
abstract class HubitatAppScript extends
        Script implements
        GroovyInterceptable,
        AppExecutorApi
{
    private static HashSet forbiddenMethods_ = ["println"] as HashSet

    private static void ensureNotForbidden(String methodOrPropName) {
        println "Checking '${methodOrPropName}'"
        if (forbiddenMethods_.contains(methodOrPropName)) {
            throw new SecurityException("Usage of '${methodOrPropName}' is forbidden inside Hubitat scripts")
        }
    }
//
//    void setApp(AppExecutorApi app) {
//        this.app_ = app
//    }

    /*
    Don't let Script base class to redirect properties to binding.
    Also redirect to local methods (if present) first.
     */

    @Override
    Object getProperty(String property) {
        ensureNotForbidden(property)
        // TODO: search here is linear, need to fix.
        def foundMethod = getMetaClass().methods.find({ it.name == property })
        if (foundMethod) {
            return foundMethod
        }

        return getMetaClass().getProperty(this as GroovyObjectSupport, property)
    }

    /*
    Don't let Script base class to redirect properties to binding.
     */

    @Override
    void setProperty(String property, Object newValue) {
        getMetaClass().setProperty(this as GroovyObjectSupport, property, newValue)
    }

    @Override
    def invokeMethod(String name, Object args) {
        ensureNotForbidden(name)
        return super.invokeMethod(name, args)
    }
}
