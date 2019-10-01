package me.biocomp.hubitat_ci.util

import groovy.transform.CompileStatic

import java.lang.reflect.Method

@CompileStatic
class ScriptUtil {

    static def handleGetProperty(String property, def scriptObject, Map userSettingsMap)
    {
        switch (property) {
            case "metaClass":
                return scriptObject.getMetaClass()

        // default: - continue processing below
        }

        final def getterMethodName = "get${property.capitalize()}"
        try {
            // Simple implementation of redirecting getter back to script class (if present)
            final def getter = scriptObject.getClass().getMethod(getterMethodName, [] as Class[])
            return getter.invoke(scriptObject);
        } catch (NoSuchMethodException e) {
            // It's OK, it might be handled below
        }

        final def scriptMetaClass = scriptObject.getMetaClass()

        // There's a property, return it.
        // This is to support overriding inputs via meta classes
        if (scriptMetaClass.hasProperty(scriptObject, property)) {
            return scriptMetaClass.getProperty(scriptObject as GroovyObjectSupport, property)
        }
        // It's a method. Hubitat returns string in this case.
        else if (scriptMetaClass.methods.find{ it.name == property } != null) {
            return property
        }

        // Use settings map
        return userSettingsMap.get(property)
    }

    // Just pick first method with same name and one parameter.
    // We can't distinguish parameters because we may not know newValue class if it's null.
    private static Method findSetterMethod(String methodName, def scriptObject)
    {
        final def foundMethods = scriptObject.getClass().methods.findAll{ it.name == methodName && it.parameters.size() == 1 }
        if (!foundMethods.empty)
        {
            return foundMethods.first()
        }

        null
    }

    static void handleSetProperty(String property, def scriptObject, def newValue, Map userSettingsMap)
    {
        // Handle special cases
        switch (property)
        {
            case "metaClass":
                scriptObject.setMetaClass((MetaClass) newValue);
                return;

        // default: - continue processing below
        }

        // Simple implementation of redirecting getter back to the script class (if present)
        final def setterMethodName = "set${property.capitalize()}"
        final def setter = findSetterMethod(setterMethodName, scriptObject)
        if (setter != null)
        {
            setter.invoke(scriptObject, newValue)
            return
        }

        // Use settings
        userSettingsMap.put(property, newValue)
    }
}
