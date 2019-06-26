package biocomp.hubitatCi.app.preferences

import biocomp.hubitatCi.api.Device
import biocomp.hubitatCi.api.Attribute
import biocomp.hubitatCi.capabilities.AttributeImpl
import biocomp.hubitatCi.capabilities.Capabilities
import biocomp.hubitatCi.capabilities.CapabilityAttributeInfo
import groovy.transform.CompileStatic

import java.lang.reflect.Method

class DeviceInputType implements IInputType
{
    final Class capability
    final Class generatedDevice

    DeviceInputType(Class capability)
    {
        this.capability = capability

        this.generatedDevice = generateDeviceClass(capability)
    }

    @CompileStatic
    private static void printAttributeInfo(StringBuilder builder, CapabilityAttributeInfo attribute) {
        builder.append("""results.add(new ${AttributeImpl.canonicalName}('${attribute.name}', '${attribute.typeString}'))
""")}

    @CompileStatic
    private static void printAttributeValue(StringBuilder builder, CapabilityAttributeInfo attribute) {
        builder.append("""def getCurrent${attribute.name.capitalize()}() 
        { 
            if (userProvidedObject != null)
            {
                userProvidedObject.getCurrent${attribute.name.capitalize()}()
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
        if (userProvidedObject != null)
        {
            userProvidedObject.${method.name}(${method.parameters.collect{it.name}.join(", ")})
        }
        else
        {
            null
        }
    }
""")
    }


    @CompileStatic
    private static Class generateDeviceClass(Class capability)
    {
        def builder = new StringBuilder()
        def attributes = Capabilities.readAttributes(capability).values()

        builder.append(
                """
class Device_WithCapability_${capability.simpleName}_Impl implements ${Device.canonicalName}
{
    Device_WithCapability_${capability.simpleName}_Impl(def userProvidedObject) { this.userProvidedObject = userProvidedObject }

    private final def userProvidedObject

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
        Capabilities.readMethods(capability).each{printMethod(builder, it)}

        // End of the class:
        builder.append("}")

        return new GroovyClassLoader().parseClass(builder.toString())
    }

    @Override
    def makeInputObject(String inputName, String inputType, Object userProvidedValue)
    {
        return generatedDevice.newInstance(userProvidedValue)
    }

    @Override
    def makeInputObject(String inputName, String inputType) {
        return generatedDevice.newInstance(null)
    }
}