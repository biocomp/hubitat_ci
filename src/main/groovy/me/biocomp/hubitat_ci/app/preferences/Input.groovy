package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.validation.BooleanInputObjectGenerator
import me.biocomp.hubitat_ci.validation.IInputObjectGenerator
import me.biocomp.hubitat_ci.validation.InputCommon
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.validation.NumberInputObjectGenerator
import me.biocomp.hubitat_ci.validation.TextInputObjectGenerator
import me.biocomp.hubitat_ci.validation.UnvalidatedInputObjectGenerator

@CompileStatic
class Input {
    final Map unnamedOptions
    final Map options
    final EnumSet<Flags> validationFlags
    final IInputObjectGenerator typeWrapper
    
    // Map of one 'defaultValue' or 0 elements, meaning that there's no default value.
    final Map<String, Object> defaultValue
    
    // If type is enum, will contain enum values (and if values is map, then keys of the map)
    final ArrayList<String> enumValues = new ArrayList<String>()
    
    // If type is enum, will contain enum values (and if values is map, then values of the map)
    final ArrayList<String> enumDisplayValues = new ArrayList<String>()
    
    Input(Map unnamedOptions, Map options, EnumSet<Flags> validationFlags) {
        this.unnamedOptions = unnamedOptions
        this.options = options
        this.validationFlags = validationFlags

        this.defaultValue = InputCommon.readDefaultValue(options)
        this.typeWrapper = validateAndInitType(enumValues, enumDisplayValues)
    }

    String readName() {
        return unnamedOptions.name ? unnamedOptions.name : options?.name
    }

    String readType() {
        return unnamedOptions.type ? unnamedOptions.type : options?.type
    }

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
        return typeWrapper.makeInputObject(readName(), readType(), InputCommon.makeDefaultAndUserValuesMap(userProvidedValue, defaultValue, readType(), enumValues, enumDisplayValues))
    }

    def makeInputObject() {
        return typeWrapper.makeInputObject(readName(), readType(), InputCommon.readDefaultValueOrEnumFirstValue(defaultValue, readType(), enumValues, enumDisplayValues))
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

    private static final HashMap<String, IInputObjectGenerator> validStaticInputTypes = [bool    : new BooleanInputObjectGenerator(),
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
                                                                                         text    : new TextInputObjectGenerator()] as HashMap<String, IInputObjectGenerator>


    private IInputObjectGenerator validateAndInitType(
            ArrayList<String> enumValues, ArrayList<String> enumDisplayValues)
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            inputOptionsValidator.validate(toString(),
                    unnamedOptions,
                    options,
                    validationFlags)

            return validateInputType(enumValues, enumDisplayValues)
        }

        return new UnvalidatedInputObjectGenerator();
    }

    private IInputObjectGenerator validateInputType(ArrayList<String> enumValues, ArrayList<String> enumDisplayValues) {
        final def inputType = readType()
        final def foundStaticType = validStaticInputTypes.get(inputType)

        InputCommon.assertHasNoOptionsIfNotEnum(this, readType(), options)

        if (foundStaticType) {
            if (inputType == 'enum') {
                InputCommon.validateEnumInput(this, options, defaultValue, enumValues, enumDisplayValues, validationFlags)
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
