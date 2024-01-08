package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.validation.BooleanInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues
import me.biocomp.hubitat_ci.validation.IInputValueFactory
import me.biocomp.hubitat_ci.validation.InputCommon
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.validation.NumberInputValueFactory
import me.biocomp.hubitat_ci.validation.TextInputValueFactory

@CompileStatic
class Input extends InputCommon {
    static boolean isCapabilityType(String type) {
        return type =~ /capability\.[a-zA-Z0-9._]+/
    }

    static Class findCapabilityFromTypeString(String type) {
        assert isCapabilityType(type), "Call this method only if input type points to capability. Current value: '${type}'"
        return (Class) Capabilities.capabilitiesByDeviceSelector.get(type.substring(11))
    }

    private static final NamedParametersValidator inputOptionsValidator = NamedParametersValidator.make {
        boolParameter("capitalization", notRequired())
        objParameter("defaultValue", notRequired(), canBeNull())
        stringParameter("name", required(), mustNotBeEmpty())
        stringParameter("title", notRequired(), mustNotBeEmpty())
        stringParameter("description", notRequired(), mustNotBeEmpty())
        boolParameter("multiple", notRequired())
        numericRangeParameter("range", notRequired())
        boolParameter("required", notRequired())
        boolParameter("submitOnChange", notRequired())
        listOfStringsParameter("options", notRequired())
        stringParameter("type", required(), mustNotBeEmpty())
        boolParameter("hideWhenEmpty", notRequired())
    }

    // @formatter:off
    private static final HashMap<String, IInputValueFactory> validStaticInputTypes = [
         bool    : new BooleanInputValueFactory(),
         //"boolean",
         decimal : new NumberInputValueFactory(),
         email   : new TextInputValueFactory(),
         enum    : new TextInputValueFactory(), // Todo: make enum input type?
         hub     : new TextInputValueFactory(),
         icon    : new TextInputValueFactory(),
         number  : new NumberInputValueFactory(),
         password: new TextInputValueFactory(),
         phone   : new NumberInputValueFactory(),
         time    : new TextInputValueFactory(),
         text    : new TextInputValueFactory()
    ] as HashMap<String, IInputValueFactory>
    // @formatter:on

    Input(Map unnamedOptions, Map options, EnumSet<Flags> validationFlags) {
        super(unnamedOptions, options, validationFlags, validStaticInputTypes)
    }

    @Override
    boolean validateInputBasics()
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            inputOptionsValidator.validate(toString(),
                    unnamedOptions,
                    options,
                    validationFlags)
            return true
        }

        return false
    }

    @Override
    IInputValueFactory typeNotFoundInTypeTable(String typeName) {
        if (Input.isCapabilityType(typeName)) {
            final def foundCapability = Input.findCapabilityFromTypeString(typeName)
            if (foundCapability) {
                return new DeviceInputValueFactory([foundCapability])
            } else {
                assert false: "Input ${this}'s capability '${typeName}' is not supported. Supported capabilities: ${Capabilities.capabilitiesByDeviceSelector.keySet()}"
            }
        }

        if (typeName =~ /device\.[a-zA-Z0-9._]+/) {
            return new DeviceInputValueFactory([])
            // Unknown capabilities, just using dummy device
        }

        assert false: "Input ${this}'s type ${typeName} is not supported. Valid types are: ${validStaticInputTypes} + 'capability.yourCapabilityName' + 'device.yourDeviceName'"
    }
}
