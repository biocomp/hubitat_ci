package me.biocomp.hubitat_ci.app.preferences

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.capabilities.CapabilityAttributeInfo
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues
import me.biocomp.hubitat_ci.validation.GeneratedDeviceInputBase
import me.biocomp.hubitat_ci.validation.IInputValueFactory

import java.lang.reflect.Method

class DeviceInputValueFactory implements IInputValueFactory
{
    final List<Class> capabilities
    final Class generatedDevice

    DeviceInputValueFactory(List<Class> capabilities)
    {
        this.capabilities = capabilities

        /// Class is generated because of need to support all the attributes (via getCurrent<attribute>()) and methods.
        /// It seems trickier to use propertyMissing and methodMissing than to just generate the class.
        /// Also, error about invalid calls to such class should be more clear.
        this.generatedDevice = generateDeviceClass(capabilities)
    }

    DeviceInputValueFactory(Class capability, String capabilityName)
    {
        this.capabilities = [capability]

        /// Class is generated because of need to support all the attributes (via getCurrent<attribute>()) and methods.
        /// It seems trickier to use propertyMissing and methodMissing than to just generate the class.
        /// Also, error about invalid calls to such class should be more clear.
        this.generatedDevice = generateDeviceClass(capability, capabilityName)
    }

    @Override
    @CompileStatic
    def makeInputObject(String inputName, String inputType, DefaultAndUserValues userProvidedAndDefaultValues, boolean multipleValues)
    {
        if (userProvidedAndDefaultValues.userProvidedValue.hasValue) {
            userProvidedAndDefaultValues.userProvidedValue.value
        } else {
            if (multipleValues) {
                [ generatedDevice.newInstance(inputName, inputType, this.capabilities)]
            } else {
                generatedDevice.newInstance(inputName, inputType, this.capabilities)
            }
        }
    }

    @CompileStatic
    private static void printAttributeValue(StringBuilder builder, CapabilityAttributeInfo attribute) {
        builder.append("""def getCurrent${attribute.name.capitalize()}() {
            0
        }
        """)}

    @CompileStatic
    private static void printMethod(StringBuilder builder, Method method) {
        builder.append("""
    ${method.returnType.canonicalName} ${method.name}(
        ${method.parameters.collect{it.type.canonicalName + " " + it.name}.join(", ")}
    ) {
            null
    }
""")
    }

    @CompileStatic
    private static Class generateDeviceClass(List<Class> capabilities)
    {
        final def builder = new StringBuilder()

        def attributes = new ArrayList<CapabilityAttributeInfo>()
        def methods = new ArrayList<Method>()

        capabilities.each{
            attributes.addAll(Capabilities.readAttributes(it).values())
            methods.addAll(Capabilities.readMethods(it))
        }

        final def capabilityName = capabilities[0].class.getSimpleName()
        final def className = "Device_WithCapability_${capabilityName}_Impl"

        builder.append(
                """
import groovy.transform.CompileStatic

class ${className} extends ${GeneratedDeviceInputBase.canonicalName} {
    ${className}(String inputName, String inputType, List<Class> capabilities) { super(inputName, inputType, capabilities) }

    """)
        // Attributes
        attributes.each{printAttributeValue(builder, it)}

        // Methods
        methods.each{printMethod(builder, it)}

        // End of the class:
        builder.append("""
}"""
        )

        return new GroovyClassLoader().parseClass(builder.toString())
    }

    @CompileStatic
    private static Class generateDeviceClass(Class capability, String capabilityName)
    {
        final def builder = new StringBuilder()
        def attributes = capability ? Capabilities.readAttributes(capability).values() : new ArrayList<CapabilityAttributeInfo>()
        def methods = capability ? Capabilities.readMethods(capability) : new ArrayList<Method>()

        final def className = "Device_WithCapability_${capabilityName}_Impl"

        builder.append(
                """
import groovy.transform.CompileStatic

class ${className} extends ${GeneratedDeviceInputBase.canonicalName} {
    ${className}(String inputName, String inputType, List<Class> capabilities) { super(inputName, inputType, capabilities) }

    """)
        // Attributes
        attributes.each{printAttributeValue(builder, it)}

        // Methods
        methods.each{printMethod(builder, it)}

        // End of the class:
        builder.append("""
}"""
        )

        return new GroovyClassLoader().parseClass(builder.toString())
    }

}