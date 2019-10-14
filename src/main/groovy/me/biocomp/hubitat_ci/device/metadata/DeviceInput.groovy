package me.biocomp.hubitat_ci.device.metadata


import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.util.NullableOptional
import me.biocomp.hubitat_ci.validation.BooleanInputObjectGenerator
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.IInputObjectGenerator
import me.biocomp.hubitat_ci.validation.InputCommon
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import me.biocomp.hubitat_ci.validation.NumberInputObjectGenerator
import me.biocomp.hubitat_ci.validation.TextInputObjectGenerator
import me.biocomp.hubitat_ci.validation.UnvalidatedInputObjectGenerator

/**
 * Information about 'input' in Device.
 */
@TypeChecked
@TupleConstructor
class DeviceInput {
    final Map unnamedOptions
    final Map options
    final EnumSet<Flags> validationFlags
    final IInputObjectGenerator typeWrapper
    final NullableOptional defaultValue // Map of one 'defaultValue' or 0 elements, meaning that there's no default value.
    final ArrayList<String> enumValues = new ArrayList<String>() // If type is enum, will contain enum values (and if values is map, then keys of the map)
    final ArrayList<String> enumDisplayValues = new ArrayList<String>() // If type is enum, will contain enum values (and if values is map, then values of the map)

    private static final HashMap<String, IInputObjectGenerator> validStaticInputTypes =
            [bool    : new BooleanInputObjectGenerator(),
             decimal : new NumberInputObjectGenerator(),
             email   : new TextInputObjectGenerator(),
             enum    : new TextInputObjectGenerator(), // Todo: make enum input type?
             number  : new NumberInputObjectGenerator(),
             password: new TextInputObjectGenerator(),
             phone   : new NumberInputObjectGenerator(),
             time    : new TextInputObjectGenerator(),
             text    : new TextInputObjectGenerator()] as HashMap<String, IInputObjectGenerator>


    private static final NamedParametersValidator inputOptionsValidator = NamedParametersValidator.make {
        stringParameter("name", required(), canBeEmpty(), [Flags.DontValidateDeviceInputName])
        enumStringParameter("type", required(), [
                'bool',
                'decimal',
                'email',
                'enum',
                'number',
                'password',
                'phone',
                'time',
                'text',
                'paragraph' // TODO: verify if it's really supported
        ])
        stringParameter("title", notRequired(), canBeEmpty())
        stringParameter("description", notRequired(), canBeEmpty())
        objParameter("defaultValue", notRequired(), canBeNull())
        boolParameter("required", notRequired())
        boolParameter("displayDuringSetup", notRequired())
        numericRangeParameter("range", notRequired())
        listOfStringsParameter("options", notRequired())
    }

    DeviceInput(Map unnamedOptions, Map options, EnumSet<Flags> validationFlags) {
        this.unnamedOptions = unnamedOptions
        this.options = options
        this.validationFlags = validationFlags

        this.defaultValue = InputCommon.readDefaultValue(options)
        this.typeWrapper = validateAndInitType(enumValues, enumDisplayValues)
    }

    String readName()
    {
        return unnamedOptions.name != null ? unnamedOptions.name : options.name
    }

    String readType()
    {
        return unnamedOptions.type != null ? unnamedOptions.type: options.type
    }

    private IInputObjectGenerator validateAndInitType(ArrayList<String> enumValues, ArrayList<String> enumDisplayValues)
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            inputOptionsValidator.validate(
                    toString(),
                    unnamedOptions,
                    options,
                    validationFlags,
                    validationFlags.contains(Flags.AllowMissingDeviceInputNameOrType)
                    ? EnumSet.of(NamedParametersValidator.ValidatorOption.IgnoreMissingMandatoryInputs)
                    : EnumSet.noneOf(NamedParametersValidator.ValidatorOption)
            )

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

        assert false: "Input ${this}'s type ${inputType} is not supported. Valid types are: ${validStaticInputTypes} + 'capability.yourCapabilityName' + 'device.yourDeviceName'"
    }

    @Override
    String toString()
    {
        return "input(unnamed options: ${unnamedOptions}, options = ${options})"
    }

    def makeInputObject(def userProvidedValue)
    {
        return typeWrapper.makeInputObject(readName(), readType(), InputCommon.makeDefaultAndUserValuesMap(userProvidedValue, defaultValue, readType(), enumValues, enumDisplayValues))
    }

    def makeInputObject()
    {
        return typeWrapper.makeInputObject(readName(), readType(),  DefaultAndUserValues.defaultValueOnly(InputCommon.readDefaultValueOrEnumFirstValue(defaultValue, readType(), enumValues, enumDisplayValues)))
    }
}
