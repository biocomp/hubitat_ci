package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.CompileStatic

@CompileStatic
class Input {
    final Map unnamedOptions
    final Map options
    final EnumSet<Flags> validationFlags
    final IInputObjectGenerator typeWrapper
    final Map<String, Object> defaultValue // Map of one 'defaultValue' or 0 elements, meaning that there's no default value.
    final ArrayList<String> enumValues = new ArrayList<String>() // If type is enum, will contain enum values (and if values is map, then keys of the map)
    final ArrayList<String> enumDisplayValues = new ArrayList<String>() // If type is enum, will contain enum values (and if values is map, then values of the map)

    Input(Map unnamedOptions, Map options, EnumSet<Flags> validationFlags) {
        this.unnamedOptions = unnamedOptions
        this.options = options
        this.validationFlags = validationFlags

        this.defaultValue = readDefaultValue()
        this.typeWrapper = validateAndInitType(enumValues, enumDisplayValues)
    }

    String readName()
    {
        return unnamedOptions.name ? unnamedOptions.name : options?.name
    }

    String readType()
    {
        return unnamedOptions.type ? unnamedOptions.type : options?.type
    }

    static boolean isCapabilityType(String type) {
        return type =~ /capability\.[a-zA-Z0-9._]+/
    }

    static Class findCapabilityFromTypeString(String type)
    {
        assert isCapabilityType(type), "Call this method only if input type points to capability. Current value: '${type}'"
        return (Class)Capabilities.capabilitiesByDeviceSelector.get(type.substring(11))
    }

    @Override
    String toString()
    {
        return "input(options: ${options}, unnamedOptions: ${unnamedOptions})"
    }

    def makeInputObject(def userProvidedValue)
    {
        return typeWrapper.makeInputObject(readName(), readType(), makeDefaultAndUserValuesMap(userProvidedValue))
    }

    def makeInputObject()
    {
        return typeWrapper.makeInputObject(readName(), readType(), readDefaultValueOrEnumFirstValue())
    }

    Map<String, Object> readDefaultValue()
    {
        if (options?.containsKey('defaultValue'))
        {
            return [defaultValue: options.defaultValue]
        }

        return [:]
    }

    private Map<String, Object> readDefaultValueOrEnumFirstValue()
    {
        if (defaultValue.containsKey('defaultValue'))
        {
            if (readType() == 'enum') {
                final def defaultValueStr = defaultValue.defaultValue.toString()
                final def indexOfDisplayValue = enumDisplayValues.findIndexOf { it == defaultValueStr }
                if (indexOfDisplayValue == -1) // Looks like enum value (not display value) was used
                {
                    return [defaultValue: (Object)defaultValueStr]
                }

                return [defaultValue: (Object) enumValues[indexOfDisplayValue]]
            }
            else
            {
                return defaultValue
            }
        }
        else if (readType() == 'enum')
        {
            return [defaultValue: (Object)enumValues[0]]
        }

        return [:]
    }

    private Map<String, Object> makeDefaultAndUserValuesMap(def userProvidedValue)
    {
        def extendedDefaultValue = readDefaultValueOrEnumFirstValue()
        if (extendedDefaultValue.containsKey('defaultValue'))
        {
            return [defaultValue: extendedDefaultValue.defaultValue, userProvidedValue: userProvidedValue]
        }

        return [userProvidedValue: userProvidedValue]
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

    private static final HashMap<String, IInputObjectGenerator> validStaticInputTypes = [
            bool: new BooleanInputObjectGenerator(),
            //"boolean",
            decimal: new NumberInputObjectGenerator(),
            email: new TextInputObjectGenerator(),
            enum: new TextInputObjectGenerator(), // Todo: make enum input type?
            hub: new TextInputObjectGenerator(),
            icon: new TextInputObjectGenerator(),
            number: new NumberInputObjectGenerator(),
            password: new TextInputObjectGenerator(),
            phone: new NumberInputObjectGenerator(),
            time: new TextInputObjectGenerator(),
            text: new TextInputObjectGenerator()
    ] as HashMap<String, IInputObjectGenerator>


    private IInputObjectGenerator validateAndInitType(ArrayList<String> enumValues, ArrayList<String> enumDisplayValues)
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            inputOptionsValidator.validate(
                    toString(),
                    unnamedOptions,
                    options,
                    validationFlags)

            return validateInputType(enumValues, enumDisplayValues)
        }

        return new UnvalidatedInputObjectGenerator();
    }

    /**
     * Insert unique values from source into uniqueValues, assert if there are duplicates.
     * @param keyOrValue - word 'key' or 'value' for better error message.
     * @param source - gets list from here
     * @param uniqueValues - receives list of unique enum values
     */
    private static void ensureAndInsertUniqueValue(String keyOrValue, List source, ArrayList<String> uniqueValues)
    {
        def unique = new HashSet<String>()
        source.each{
            def stringVal = it.toString()

            assert !unique.contains(stringVal) : "${this}: enum ${keyOrValue} '${it}' was duplicated"

            unique.add(stringVal)
            uniqueValues.add(stringVal)
        }
    }

    private void validateEnumInput(ArrayList<String> enumValues, ArrayList<String> enumDisplayValues)
    {
        assert options != null && options.containsKey('options'): "${this}: input of type 'enum' must have 'options' parameter with enum values"

        if (Map.isInstance(options.options)) {
            final def optionsMap = (Map)options.options
            ensureAndInsertUniqueValue("value", optionsMap.values().collect{it.toString()}, enumDisplayValues)
            ensureAndInsertUniqueValue("key", optionsMap.keySet().collect{it.toString()}, enumValues)
        }
        else if (List.isInstance(options.options)) {
            ensureAndInsertUniqueValue("value", options.options.collect{it.toString()}, enumDisplayValues)
            enumValues.addAll(enumDisplayValues)
        }
        else if (!validationFlags.contains(Flags.AllowNullEnumInputOptions))
        {
            assert false, "${this}: enum input's 'options' must be a list of values or map int->value, but it is: ${options.options?.class}. To allow null options, use ${Flags.AllowNullEnumInputOptions}."
        }

        if (defaultValue.containsKey('defaultValue'))
        {
            final def addedEnumValues = enumValues ? " or ${enumValues}" : ""
            assert enumDisplayValues.contains(defaultValue.defaultValue.toString()) || enumValues.contains(defaultValue.defaultValue.toString()) : "${this}: defaultValue '${defaultValue.defaultValue}' is not one of valid values: ${enumDisplayValues}${addedEnumValues}"
        }
    }

    private IInputObjectGenerator validateInputType(ArrayList<String> enumValues, ArrayList<String> enumDisplayValues)
    {
        final def inputType = readType()
        final def foundStaticType = validStaticInputTypes.get(inputType)

        if (inputType != 'enum') {
            assert !options || !options.containsKey('options'): "${this}: only 'enum' input type needs 'options' parameter."
        }

        if (foundStaticType)
        {
            if (inputType == 'enum') {
                validateEnumInput(enumValues, enumDisplayValues)
            }

            return foundStaticType
        }

        if (Input.isCapabilityType(inputType))
        {
            final def foundCapability = Input.findCapabilityFromTypeString(inputType)
            if (foundCapability)
            {
                return new DeviceInputObjectGenerator(foundCapability, foundCapability.simpleName)
            }
            else
            {
                assert false : "Input ${this}'s capability '${inputType}' is not supported. Supported capabilities: ${Capabilities.capabilitiesByDeviceSelector.keySet()}"
            }
        }

        if (inputType =~ /device\.[a-zA-Z0-9._]+/)
        {
            return new DeviceInputObjectGenerator(null, inputType.substring('device.'.length())) // Unknown capabilities, just using dummy device
        }

        assert false : "Input ${this}'s type ${inputType} is not supported. Valid types are: ${validStaticInputTypes} + 'capability.yourCapabilityName' + 'device.yourDeviceName'"
    }


}
