package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.AppExecutorApi
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/* Custom Script that redirects most unknown calls to app_, and does not use Binding.
* */

@TypeChecked
abstract class HubitatAppScript extends Script
{
    private static HashSet forbiddenMethods_ = ["println"] as HashSet

    private void ensureNotForbidden(String methodOrPropName) {
        //println "checking '$methodOrPropName'"
        if (this.@forbiddenMethods_.contains(methodOrPropName)) {
            //throw new SecurityException("Usage of '${methodOrPropName}' is forbidden inside Hubitat scripts")
        }
    }

    @Delegate
    AppExecutorApi api = null

    /*
        Don't let Script base class to redirect properties to binding,
            it causes confusing issues when using non-supported methods and properties.

        Also, getProperty() is still going to be called for valid get*() methods added by @Delegate.
        So we need to intercept those separately.
     */
    @Override
    Object getProperty(String property) {
//        //ensureNotForbidden(property)
        //System.out.println("getProperty('${property}')")

        def methodName = "get${property.capitalize()}"
        try {
            // Simple implenmentation of redirecting getter back to script class (if present)
            // Don't need to support MOP here, everything can be mocked via AppExecutorBase interface.
            def getter = this.getClass().getMethod(methodName, new Class[0])
            //System.out.println "FOUND getter ${methodName}!"
            return getter.invoke(this);
        }
        catch (NoSuchMethodException)
        {
            // It's OK, let's hope it'll be found in metaclass
        }

        return getMetaClass().getProperty(this as GroovyObjectSupport, property)

//
////        def foundMethod = getMetaClass().methods.find({ it.name == property })
////        if (foundMethod) {
////            //System.out.println("setProperty - found method('${property}')")
////            //println "getProperty('${property}')"
////            return foundMethod
////        }
////
////        foundMethod = getMetaClass().methods.find({ it.name == "get" + property.capitalize() })
////        if (foundMethod) {
////            return foundMethod.invoke(this, new Object[0])
////        }
//
//        //System.out.println("setProperty('${property}')")
//        return getMetaClass().getProperty(this as GroovyObjectSupport, property)
    }

    /*
        Don't let Script base class to redirect properties to binding.
    */
    @Override
    void setProperty(String property, Object newValue) {
        //System.out.println("setProperty('${property}')")
        getMetaClass().setProperty(this as GroovyObjectSupport, property, newValue)
    }
//
//    @Override
//    @TypeChecked
//    //@CompileStatic
//    def invokeMethod(String name, Object args) {
//        //ensureNotForbidden(name)
//
//        if (name == "getMetaClass")
//        {
//            return metaClass
//        }
//
//        System.out.println("invokeMethod('${name}')")
//        metaClass.getMetaMethod(name,args).invoke(this, name, args)
//        //MetaMethod foundMethod = getMetaClass().methods.find({ it.name == name })
//        //foundMethod.inv(this, name, args)
//        //super.invokeMethod(name, args)
//        //return (metaClass as MetaClass).invokeMethod(name, args)
//        //this.getMetaClass().invokeMethod(this.class, this, name, args, true)
//    }
}
