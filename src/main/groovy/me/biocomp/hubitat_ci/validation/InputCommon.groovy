package me.biocomp.hubitat_ci.validation

import groovy.transform.CompileStatic

@CompileStatic
class InputCommon {
    static void assertHasNoOptionsIfNotEnum(def input, String type, Map options)
    {
        if (type != 'enum') {
            assert !options || !options.containsKey('options'): "${input}: only 'enum' input type needs 'options' parameter."
        }
    }

    static Map<String, Object> readDefaultValue(Map options)
    {
        if (options?.containsKey('defaultValue'))
        {
            return [defaultValue: options.defaultValue]
        }

        return [:]
    }

    /**
     * Insert unique values from source into uniqueValues, assert if there are duplicates.
     * @param keyOrValue - word 'key' or 'value' for better error message.
     * @param source - gets list from here
     * @param uniqueValues - receives list of unique enum values
     */
    private static void ensureAndInsertUniqueValue(String keyOrValue, List source, ArrayList<String> uniqueValues) {
        def unique = new HashSet<String>()
        source.each {
            def stringVal = it.toString()

            assert !unique.contains(stringVal): "${this}: enum ${keyOrValue} '${it}' was duplicated"

            unique.add(stringVal)
            uniqueValues.add(stringVal)
        }
    }

    static void validateEnumInput(
            Map options,
            Map defaultValue,
            ArrayList<String> enumValues,
            ArrayList<String> enumDisplayValues,
            EnumSet<Flags> validationFlags) {
        assert options != null && options.containsKey('options'): "${this}: input of type 'enum' must have 'options' parameter with enum values"

        if (Map.isInstance(options.options)) {
            final def optionsMap = (Map) options.options
            ensureAndInsertUniqueValue("value", optionsMap.values().collect { it.toString() }, enumDisplayValues)
            ensureAndInsertUniqueValue("key", optionsMap.keySet().collect { it.toString() }, enumValues)
        } else if (List.isInstance(options.options)) {
            ensureAndInsertUniqueValue("value", options.options.collect { it.toString() }, enumDisplayValues)
            enumValues.addAll(enumDisplayValues)
        } else if (!validationFlags.contains(Flags.AllowNullEnumInputOptions)) {
            assert false, "${this}: enum input's 'options' must be a list of values or map int->value or string->value, but it is: ${options.options} of class ${options.options?.class}. To allow null options, use ${Flags.AllowNullEnumInputOptions}."
        }

        if (defaultValue.containsKey('defaultValue')) {
            final def addedEnumValues = enumValues ? " or ${enumValues}" : ""
            assert enumDisplayValues.contains(defaultValue.defaultValue.toString()) || enumValues.contains(defaultValue.defaultValue.toString()): "${this}: defaultValue '${defaultValue.defaultValue}' is not one of valid values: ${enumDisplayValues}${addedEnumValues}"
        }
    }

    static Map<String, Object> readDefaultValueOrEnumFirstValue(
            Map defaultValue,
            String type,
            ArrayList<String> enumValues,
            ArrayList<String> enumDisplayValues) {
        if (defaultValue.containsKey('defaultValue')) {
            if (type == 'enum') {
                final def defaultValueStr = defaultValue.defaultValue.toString()
                final def indexOfDisplayValue = enumDisplayValues.findIndexOf { it == defaultValueStr }
                if (indexOfDisplayValue == -1) // Looks like enum value (not display value) was used
                {
                    return [defaultValue: (Object) defaultValueStr]
                }

                return [defaultValue: (Object) enumValues[indexOfDisplayValue]]
            } else {
                return defaultValue
            }
        } else if (type == 'enum') {
            return [defaultValue: (Object) enumValues[0]]
        }

        return [:]
    }

    static Map<String, Object> makeDefaultAndUserValuesMap(
            def userProvidedValue,
            Map defaultValue,
            String type,
            ArrayList<String> enumValues,
            ArrayList<String> enumDisplayValues) {

        final def extendedDefaultValue = readDefaultValueOrEnumFirstValue(defaultValue, type, enumValues, enumDisplayValues)
        if (extendedDefaultValue.containsKey('defaultValue')) {
            return [defaultValue: extendedDefaultValue.defaultValue, userProvidedValue: userProvidedValue]
        }

        return [userProvidedValue: userProvidedValue]
    }

    static def returnUserOrDefaultOrCustomValue(Map<String, Object> userProvidedAndDefaultValues, Object customValue)
    {
        if (userProvidedAndDefaultValues.containsKey('userProvidedValue'))
        {
            return userProvidedAndDefaultValues.userProvidedValue
        }
        else if (userProvidedAndDefaultValues.containsKey('defaultValue'))
        {
            return userProvidedAndDefaultValues.defaultValue
        }
        else
        {
            return customValue
        }
    }
}
