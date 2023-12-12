package me.biocomp.hubitat_ci.validation

import groovy.transform.AutoImplement
import groovy.transform.TupleConstructor
import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.Capability
import me.biocomp.hubitat_ci.api.Command
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.capabilities.GeneratedCapability

/**
 * This object is a base for object returned when accessing input in the script for device inputs
 * if user did not provide mocks for them.
 * Most methods are not used by our validations, and thus are auto-implemented to do nothing.
 * If better implementation is needed, user should provide it via mock.
 */
@AutoImplement
class GeneratedDeviceInputBase implements DeviceWrapper {
    GeneratedDeviceInputBase(String inputName, String inputType, List<Class> capabilityClasses) {
        capabilities = new ArrayList<Capability>()
        supportedAttributes = new ArrayList<Attribute>()
        supportedCommands = new ArrayList<Command>()

        capabilityClasses.each { capabilityClass ->
            if (capabilityClass != null) {
                def generatedCapability = new GeneratedCapability(capabilityClass)
                if (generatedCapability != null) {
                    capabilities.add(generatedCapability)

                    if (generatedCapability.attributes != null && generatedCapability.attributes.size() > 0) {
                        supportedAttributes.addAll(generatedCapability.attributes)
                    }

                    if (generatedCapability.commands != null && generatedCapability.commands.size() > 0) {
                        supportedCommands.addAll(generatedCapability.commands)
                    }
                }
            }
        }

        this.inputName = inputName
        this.inputType = inputType
    }

    @Override
    String getLabel() {
        "label_generated_from_${inputName}"
    }

    @Override
    String getName() {
        "name_generated_from_${inputName}"
    }

    @Override
    List<Attribute> getSupportedAttributes() {
        return supportedAttributes
    }

    @Override
    List<Command> getSupportedCommands() {
        return supportedCommands
    }

    @Override
    List<Capability> getCapabilities() {
        return capabilities
    }

    @Override
    String toString() {
        "GeneratedDevice(input: ${inputName}, type: ${inputType})"
    }

    private final List<Attribute> supportedAttributes
    private final List<Command> supportedCommands
    private final List<Capability> capabilities
    private final String inputName
    private final String inputType
}
