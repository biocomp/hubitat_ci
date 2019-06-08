package biocomp.hubitatCi.validation

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import biocomp.hubitatCi.util.SimpleRange


@TypeChecked
class ParametersToValidate
{
    @TypeChecked
    protected class Parameter
    {
        Parameter(String name, IsRequired required, List<Flags> dontValidateIfFlags, Closure validator)
        {
            this([name: name, required: required == IsRequired.Yes, dontValidateIfFlags: dontValidateIfFlags], validator)
        }

        Parameter(Map<String, Object> options, Closure validator)
        {
            // Check that option's keys only has keys from known ones below and
            // extraValidParameterNames
            assert((
                    options.keySet()
                            - ((["name", "required", "dontValidateIfFlags"] as Set)
                            )).size() == 0)

            assert(options.name)
            name = options.name

            required = options.get("required", false)

            def dontValidateWithFlagsValue = options.get("dontValidateIfFlags")
            if (dontValidateWithFlagsValue == null)
            {
                dontValidateWithTheseFlags = EnumSet.noneOf(Flags)
            }
            else if (dontValidateWithFlagsValue instanceof List<Flags>)
            {
                dontValidateWithTheseFlags = EnumSet.noneOf(Flags)
                dontValidateWithTheseFlags.addAll(dontValidateWithFlagsValue as List<Flags>)
            }
            else if (dontValidateWithFlagsValue instanceof EnumSet)
            {
                dontValidateWithTheseFlags = dontValidateWithFlagsValue as EnumSet
            }
            else
            {
                assert false, "${dontValidateWithFlagsValue} is not a supported way of passing set of flags for 'dontValidateIfFlags' parameter"
            }

            this.validator = {
                ValidatorBase validatorObject, context, value ->
                    if (!validatorObject.hasAnyOfFlags(dontValidateWithTheseFlags))
                    {
                        validator(validatorObject, context, value)
                    }
            }
        }

        final String name
        final boolean required
        final EnumSet<Flags> dontValidateWithTheseFlags
        final Closure validator
    }

    enum IsRequired
    {
        Yes,
        No
    }

    static IsRequired required()
    {
        return IsRequired.Yes
    }

    static IsRequired notRequired()
    {
        return IsRequired.No
    }

    private void addParameter(Parameter p)
    {
        supportedParameters[p.name] = p

        if (p.required)
        {
            mandatoryParameters << p.name
        }
    }

    @CompileStatic
    void boolParameter(String name, IsRequired required)
    {
        addParameter(new Parameter(name, required, [],
                { def validator, String context, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"
                    String valuePrinted = value.toString()
                    assert valuePrinted == "false" || valuePrinted == "true" : "${context}: ${name}'s value is not boolean, it's ${value}"
                }))
    }

    enum CanBeNull
    {
        Yes,
        No
    }

    static CanBeNull canBeNull()
    {
        return CanBeNull.Yes
    }

    static CanBeNull mustNotBeNull()
    {
        return CanBeNull.No
    }

    @CompileStatic
    void objParameter(
            String name,
            IsRequired required,
            CanBeNull canBeNull,
            Closure<Tuple2<String, Object>> validateType = null)
    {
        addParameter(new Parameter(name, required, [], {
            def flags, def context, def value ->
                if (canBeNull != CanBeNull.Yes)
                {
                    assert value != null: "${context}: '${name}' object can't be null"
                }

                if (validateType && value != null)
                {
                    try {
                        def typeValidationResult = validateType(value)
                        assert typeValidationResult.second != null : "${context}: '${name}' value must be of type '${typeValidationResult.first}', but is '${value.class}'"
                    }
                    catch (java.lang.ClassCastException e)
                    {
                        assert false, "${context}: '${name} is of incorrect type: '${e}'"
                    }
                }
        }))
    }

    @CompileStatic
    void numericRangeParameter(String name, IsRequired required)
    {
        addParameter(new Parameter(name, required, [], { def flags, String context, def value ->
                assert value != null: "${context}: '${name}' value can't be null"
                
                try
                {
                    SimpleRange.parse(value as String)
                }
                catch (IllegalArgumentException e)
                {
                    assert false : "${context}: ${name}'s value must be a valid numeric range, but parsing of ${value} failed with: ${e}"
                }
            }))
    }

    void listOfStringsParameter(String name, IsRequired required)
    {
        addParameter(new Parameter(name, required, [], { ValidatorBase validator, String context, def value ->
                if (!validator.hasFlag(Flags.AllowNullListOptions)) {
                    assert value != null: "${context}: '${name}' value can't be null"
                }

                if (value != null) {
                    assert (value as List<String> != null): "${context}: ${name}'s value must be convertible to a list of strings, but it's ${value.class} = ${value}"
                }
            }))
    }

    private static String validateStringValue(
            ValidatorBase validator,
            String context,
            String name,
            def value,
            CanBeEmpty canBeEmpty)
    {
        assert value != null: "${context}: '${name}' value can't be null"

        String val = null

        if (value instanceof String)
        {
            val = value as String
        }
        else if (value instanceof GString)
        {
            val = (value as GString).toString()
        }
        else
        {
            assert false: "${context}: '${name}''s value must be String/GString, not ${value.class}"
        }

        if (canBeEmpty != CanBeEmpty.Yes && !validator.hasFlag(Flags.AllowEmptyOptionValueStrings)) {
            assert val != "": "${context}: '${name}''s value can't be empty"
        }

        return val
    }

    enum CanBeEmpty
    {
        Yes,
        No
    }

    static CanBeEmpty canBeEmpty()
    {
        return CanBeEmpty.Yes
    }

    static CanBeEmpty mustNotBeEmpty()
    {
        return CanBeEmpty.No
    }
    
    void stringParameter(String name, IsRequired required, CanBeEmpty canBeEmpty, List<Flags> dontValidateIfFlags = null)
    {
        addParameter(new Parameter(
                name, required, dontValidateIfFlags,
                { ValidatorBase validator,  String context, def value ->
                    validateStringValue(validator, context, name, value, canBeEmpty)
                }))
    }

    void enumStringParameter(String name, IsRequired required, List<String> values)
    {
        assert values

        def validValues = new HashSet<String>(values)

        addParameter(new Parameter(name, required, [],
                { ValidatorBase validator, String context, def value ->
                    def val = validateStringValue(validator, context, name, value, CanBeEmpty.No)
                    assert validValues.contains(val) : "${context}: '${name}''s value ('${val}') is not supported. Valid values: ${validValues}"
                }))
    }

    void mapParameter(String name, IsRequired required)
    {
        addParameter(new Parameter(name, required, [],
                { def flags, String context, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"
                    assert value instanceof Map: "${context}: '${name}''s value must be Map, not ${value.class}"
                }))
    }

    void intParameter(String name, IsRequired required)
    {
        addParameter(new Parameter(name, required, [],
                { def flags, String context, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"

                    int result = 0
                    try
                    {
                        result = Integer.parseInt(value as String)
                    }
                    catch (NumberFormatException e)
                    {
                        assert false : "${context}: Integer option '${name}' value = '${value}' can't be formatted as int: ${e.message}"
                    }
                }))
    }

    /**
     * Add validations fom another validator.
     * If names match, it's caller's problem.
     * @param copyFrom
     */
    void add(NamedParametersValidator copyFrom)
    {
        copyFrom.supportedParameters.each { supportedParameters[it.key] = it.value }
        copyFrom.mandatoryParameters.each { mandatoryParameters.add(it) }
    }

    final HashMap<String, Parameter> supportedParameters = [] as HashMap
    final HashSet<String> mandatoryParameters = [] as HashSet<String>
}

