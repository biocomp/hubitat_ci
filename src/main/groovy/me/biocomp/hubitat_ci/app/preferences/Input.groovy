package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.util.NullableOptional
import me.biocomp.hubitat_ci.validation.BooleanInputObjectGenerator
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues
import me.biocomp.hubitat_ci.validation.IInputObjectGenerator
import me.biocomp.hubitat_ci.validation.InputCommon
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.validation.NumberInputObjectGenerator
import me.biocomp.hubitat_ci.validation.TextInputObjectGenerator
import me.biocomp.hubitat_ci.validation.UnvalidatedInputObjectGenerator

@CompileStatic
class Input extends InputCommon {
    static boolean isCapabilityType(String type) {
        return type =~ /capability\.[a-zA-Z0-9._]+/
    }

    static Class findCapabilityFromTypeString(String type) {
        assert isCapabilityType(type), "Call this method only if input type points to capability. Current value: '${type}'"
        return (Class) Capabilities.capabilitiesByDeviceSelector.get(type.substring(11))
    }

    @Override
    String toString() {
        return "input(options: ${options}, unnamedOptions: ${unnamedOptions})"
    }

    def makeInputObject(def userProvidedValue) {
        return typeWrapper.makeInputObject(readName(), readType(),
                InputCommon.makeDefaultAndUserValuesMap(userProvidedValue, defaultValue, readType(), enumValues,
                        enumDisplayValues))
    }

    def makeInputObject() {
        return typeWrapper.makeInputObject(readName(), readType(),
                DefaultAndUserValues.defaultValueOnly(InputCommon.readDefaultValueOrEnumFirstValue(defaultValue,
                        readType(), enumValues, enumDisplayValues)))
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
    private static final HashMap<String, IInputObjectGenerator> validStaticInputTypes = [
         bool    : new BooleanInputObjectGenerator(),
         //"boolean",
         decimal : new NumberInputObjectGenerator(),
         email   : new TextInputObjectGenerator(),
         enum    : new TextInputObjectGenerator(), // Todo: make enum input type?
         hub     : new TextInputObjectGenerator(),
         icon    : new TextInputObjectGenerator(),
         number  : new NumberInputObjectGenerator(),
         password: new TextInputObjectGenerator(),
         phone   : new NumberInputObjectGenerator(),
         time    : new TextInputObjectGenerator(),
         text    : new TextInputObjectGenerator()
    ] as HashMap<String, IInputObjectGenerator>
    // @formatter:on

    Input(Map unnamedOptions, Map options, EnumSet<Flags> validationFlags) {
        super(unnamedOptions, options, validationFlags)
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
    IInputObjectGenerator validateAndInitType(boolean basicValidationDone)
    {
        if (basicValidationDone) {
            return validateInputType()
        }

        return new UnvalidatedInputObjectGenerator();
    }

    private IInputObjectGenerator validateInputType() {
        final def inputType = readType()
        final def foundStaticType = validStaticInputTypes.get(inputType)

        if (foundStaticType) {
            if (inputType == 'enum') {
                validateEnumInput()
            }

            return foundStaticType
        }

        if (Input.isCapabilityType(inputType)) {
            final def foundCapability = Input.findCapabilityFromTypeString(inputType)
            if (foundCapability) {
                return new DeviceInputObjectGenerator(foundCapability, foundCapability.simpleName)
            } else {
                assert false: "Input ${this}'s capability '${inputType}' is not supported. Supported capabilities: ${Capabilities.capabilitiesByDeviceSelector.keySet()}"
            }
        }

        if (inputType =~ /device\.[a-zA-Z0-9._]+/) {
            return new DeviceInputObjectGenerator(null, inputType.substring('device.'.length()))
            // Unknown capabilities, just using dummy device
        }

        assert false: "Input ${this}'s type ${inputType} is not supported. Valid types are: ${validStaticInputTypes} + 'capability.yourCapabilityName' + 'device.yourDeviceName'"
    }
}
