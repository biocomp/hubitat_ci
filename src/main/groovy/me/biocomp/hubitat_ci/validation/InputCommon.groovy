package me.biocomp.hubitat_ci.validation

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.util.NullableOptional
import org.apache.commons.lang.ObjectUtils

@CompileStatic
class DefaultAndUserValues
{
    static DefaultAndUserValues empty()
    {
        return new DefaultAndUserValues()
    }

    static DefaultAndUserValues defaultValueOnly(NullableOptional defaultValue)
    {
        return new DefaultAndUserValues(defaultValue)
    }

    static DefaultAndUserValues bothValues(NullableOptional defaultValue, NullableOptional userProvidedValue)
    {
        return new DefaultAndUserValues(defaultValue, userProvidedValue)
    }

    private DefaultAndUserValues() {}

    private DefaultAndUserValues(NullableOptional defaultValue)
    {
        this.@defaultValue = defaultValue
    }

    private DefaultAndUserValues(NullableOptional defaultValue, NullableOptional userProvidedValue)
    {
        this.@defaultValue = defaultValue
        this.@userProvidedValue = userProvidedValue
    }

    final NullableOptional defaultValue = NullableOptional.empty()
    final NullableOptional userProvidedValue = NullableOptional.empty()
}

@CompileStatic
class InputCommon {
    static void assertHasNoOptionsIfNotEnum(def input, String type, Map options)
    {
        if (type != 'enum') {
            assert !options || !options.containsKey('options'): "${input}: only 'enum' input type needs 'options' parameter."
        }
    }

    static NullableOptional readDefaultValue(Map options)
    {
        if (options?.containsKey('defaultValue'))
        {
            return NullableOptional.withValue(options.defaultValue)
        }

        return NullableOptional.empty()
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
            def input,
            Map options,
            NullableOptional defaultValue,
            ArrayList<String> enumValues,
            ArrayList<String> enumDisplayValues,
            EnumSet<Flags> validationFlags) {
        assert options != null && options.containsKey('options'): "${input}: input of type 'enum' must have 'options' parameter with enum values"

        if (Map.isInstance(options.options)) { // Map of key: value
            final def optionsMap = (Map) options.options

            assert optionsMap.size() != 0 : "${input}: enum options can't be empty"

            ensureAndInsertUniqueValue("value", optionsMap.values().collect { it.toString() }, enumDisplayValues)
            ensureAndInsertUniqueValue("key", optionsMap.keySet().collect { it.toString() }, enumValues)
        } else if (List.isInstance(options.options)) { // List
            final def optionsList = options.options as List

            assert optionsList.size() != 0 : "${input}: enum options can't be empty"

            if (optionsList.find{Map.isInstance(it)} != null) { // List of key-value pairs (represented as 1-item maps).
                final def validateMapElement = { it ->
                    assert Map.isInstance(it) : "${input}: if enum options is a list, it must be a list of values or maps. But '${it.inspect()}' isn't a map."
                    assert ((Map)it).size() == 1 : "${input}: when enum options is list of maps, each map must have one entry. But '${it.inspect()}' doesn't."
                }

                optionsList.each(validateMapElement)

                ensureAndInsertUniqueValue("value", optionsList.collect { ((Map)it).values().first().toString() }, enumDisplayValues)
                ensureAndInsertUniqueValue("key", optionsList.collect { ((Map)it).keySet().first().toString() }, enumValues)
            } else { // Just a list of options
                ensureAndInsertUniqueValue("value", options.options.collect { it.toString() }, enumDisplayValues)
                enumValues.addAll(enumDisplayValues)
            }
        } else if (!validationFlags.contains(Flags.AllowNullEnumInputOptions)) {
            assert false, "${input}: enum input's 'options' must be a list of values or map int->value or string->value, but it is: ${options.options} of class ${options.options?.class}. To allow null options, use ${Flags.AllowNullEnumInputOptions}."
        }

        if (defaultValue.hasValue) {
            final def addedEnumValues = enumValues ? " or ${enumValues}" : ""
            assert enumDisplayValues.contains(defaultValue.value.toString()) || enumValues.contains(defaultValue.value.toString()): "${input}: defaultValue '${defaultValue.value}' is not one of valid values: ${enumDisplayValues}${addedEnumValues}"
        }
    }

    static NullableOptional readDefaultValueOrEnumFirstValue(
            NullableOptional defaultValue,
            String type,
            ArrayList<String> enumValues,
            ArrayList<String> enumDisplayValues) {
        if (defaultValue.hasValue) {
            if (type == 'enum') {
                final def defaultValueStr = defaultValue.value.toString()
                final def indexOfDisplayValue = enumDisplayValues.findIndexOf { it == defaultValueStr }
                if (indexOfDisplayValue == -1) // Looks like enum value (not display value) was used
                {
                    return NullableOptional.withValue(defaultValueStr)
                }

                return NullableOptional.withValue(enumValues[indexOfDisplayValue])
            } else {
                return defaultValue
            }
        } else if (type == 'enum') {
            return NullableOptional.withValue(enumValues[0])
        }

        return NullableOptional.empty()
    }

    static DefaultAndUserValues makeDefaultAndUserValuesMap(
            def userProvidedValue,
            NullableOptional defaultValue,
            String type,
            ArrayList<String> enumValues,
            ArrayList<String> enumDisplayValues) {

        final def extendedDefaultValue = readDefaultValueOrEnumFirstValue(defaultValue, type, enumValues, enumDisplayValues)
        return DefaultAndUserValues.bothValues(extendedDefaultValue, NullableOptional.withValue(userProvidedValue))
    }

    static def returnUserOrDefaultOrCustomValue(DefaultAndUserValues userProvidedAndDefaultValues, Object customValue)
    {
        if (userProvidedAndDefaultValues.userProvidedValue.hasValue) {
            return userProvidedAndDefaultValues.userProvidedValue.value
        } else if (userProvidedAndDefaultValues.defaultValue.hasValue) {
            return userProvidedAndDefaultValues.defaultValue.value
        } else {
            return customValue
        }
    }
}
