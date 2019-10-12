package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.Device
import me.biocomp.hubitat_ci.capabilities.AttributeImpl
import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.capabilities.CapabilityAttributeInfo
import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.validation.IInputObjectGenerator

import java.lang.reflect.Method

class DeviceInputObjectGenerator implements IInputObjectGenerator
{
    final Class capability
    final Class generatedDevice

    DeviceInputObjectGenerator(Class capability, String deviceOrCapabilityName)
    {
        this.capability = capability
        this.generatedDevice = generateDeviceClass(capability, deviceOrCapabilityName)
    }

    @CompileStatic
    private static void printAttributeInfo(StringBuilder builder, CapabilityAttributeInfo attribute) {
        builder.append("""results.add(new ${AttributeImpl.canonicalName}('${attribute.name}', '${attribute.typeString}'))
""")}

    @CompileStatic
    private static void printAttributeValue(StringBuilder builder, CapabilityAttributeInfo attribute) {
        builder.append("""def getCurrent${attribute.name.capitalize()}() 
        { 
            if (hasUserProvidedValue())
            {
                getUserProvidedValue().getCurrent${attribute.name.capitalize()}()
            }
            else
            {
                0
            }
        }
        """)}

    @CompileStatic
    private static void printMethod(StringBuilder builder, Method method) {
        builder.append("""
    ${method.returnType.canonicalName} ${method.name}(
        ${method.parameters.collect{it.type.canonicalName + " " + it.name}.join(", ")}
    )
    { 
        if (hasUserProvidedValue())
        {
            getUserProvidedValue().${method.name}(${method.parameters.collect{it.name}.join(", ")})
        }
        else
        {
            null
        }
    }
""")
    }


    @CompileStatic
    private static Class generateDeviceClass(Class capability, String deviceOrCapabilityName)
    {
        def builder = new StringBuilder()
        def attributes = capability ? Capabilities.readAttributes(capability).values() : new ArrayList<CapabilityAttributeInfo>()
        def methods = capability ? Capabilities.readMethods(capability) : new ArrayList<Method>()

        builder.append(
                """
import groovy.transform.CompileStatic

class Device_WithCapability_${deviceOrCapabilityName}_Impl implements ${Device.canonicalName}
{
    Device_WithCapability_${deviceOrCapabilityName}_Impl(Map<String, Object> userProvidedValueMap) { this.userProvidedValueMap = userProvidedValueMap }

    private final Map<String, Object> userProvidedValueMap

    @CompileStatic
    private boolean hasUserProvidedValue()
    {
        return userProvidedValueMap.containsKey('userProvidedValue')
    }
    
    @CompileStatic
    private def getUserProvidedValue()
    {
        return userProvidedValueMap.userProvidedValue
    }

    @CompileStatic
    @Override
    Class getCapability() { return ${capability ? capability.canonicalName : "null"} }

    @CompileStatic
    @Override
    List<${Attribute.canonicalName}> getSupportedAttributes(){
        def results = new ArrayList<${Attribute.canonicalName}>()
""")
        attributes.each{printAttributeInfo(builder, it)}

    builder.append(
"""     return results
    }
""")
        // Attributes
        attributes.each{printAttributeValue(builder, it)}

        // Methods
        methods.each{printMethod(builder, it)}

        // End of the class:
        builder.append("}")

        return new GroovyClassLoader().parseClass(builder.toString())
    }

    @Override
    def makeInputObject(String inputName, String inputType, Map<String, Object> userProvidedAndDefaultValues)
    {
        return generatedDevice.newInstance(userProvidedAndDefaultValues)
    }
}