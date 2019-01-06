package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.AppExecutorApi
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/* Custom Script that redirects most unknown calls to app_, and does not use Binding.
* */
@CompileStatic
abstract class HubitatAppScript extends Script
//        Script implements
//        GroovyInterceptable
{
    private static HashSet forbiddenMethods_ = ["println"] as HashSet

    private static void ensureNotForbidden(String methodOrPropName) {
        //println "Checking '${methodOrPropName}'"
        //if (forbiddenMethods_.contains(methodOrPropName)) {
        //    throw new SecurityException("Usage of '${methodOrPropName}' is forbidden inside Hubitat scripts")
        //}
    }

    @Delegate
    AppExecutorApi api = null

    /*
    Don't let Script base class to redirect properties to binding.
    Also redirect to local methods (if present) first.
     */

    @Override
    @CompileStatic
    Object getProperty(String property) {
        ensureNotForbidden(property)
        // TODO: search here is linear, need to fix.
        def foundMethod = getMetaClass().methods.find({ it.name == property })
        if (foundMethod) {
            println "getProperty('${property}')"
            return foundMethod
        }

        return getMetaClass().getProperty(this as GroovyObjectSupport, property)
    }

    /*
    Don't let Script base class to redirect properties to binding.
     */

    @Override
    @TypeChecked
    void setProperty(String property, Object newValue) {
        getMetaClass().setProperty(this as GroovyObjectSupport, property, newValue)
    }

    @Override
    @TypeChecked
    def invokeMethod(String name, Object args) {
        ensureNotForbidden(name)
        println "invoking('${name}')..."
        return super.invokeMethod(name, args)//super.invokeMethod(name, args)//GroovyObjectSupport.getMethod("invokeMethod", String, Object).invoke(this, name, args)
    }

}
