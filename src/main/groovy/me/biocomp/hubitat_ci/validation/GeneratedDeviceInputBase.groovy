package me.biocomp.hubitat_ci.validation

import groovy.transform.AutoImplement
import groovy.transform.TupleConstructor
import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.Capability
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
    GeneratedDeviceInputBase(String inputName, String inputType, Class capabilityClass) {
        capability = new GeneratedCapability(capabilityClass)
        supportedAttributes = capability.attributes
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
    List<Capability> getCapabilities() {
        [capability]
    }

    @Override
    String toString() {
        "GeneratedDevice(input: ${inputName}, type: ${inputType})"
    }

    private final List<Attribute> supportedAttributes
    private final Capability capability
    private final String inputName
    private final String inputType
}
