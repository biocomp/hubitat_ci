package biocomp.hubitatCiTest.validation


import biocomp.hubitatCiTest.validation.Flags
import biocomp.hubitatCiTest.validation.NamedParametersValidator
import biocomp.hubitatCiTest.validation.Parameter
import groovy.transform.TypeChecked
import biocomp.hubitatCiTest.util.SimpleRange


@TypeChecked
class ParametersToValidate
{
    private void addParameter(Parameter p)
    {
        supportedParameters[p.name] = p

        if (p.required)
        {
            mandatoryParameters << p.name
        }
    }

    void boolParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String,
                options.get("required", false) as boolean,
                { def flags, String context, String name, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"
                    String valuePrinted = value.toString()
                    assert valuePrinted == "false" || valuePrinted == "true" : "${context}: ${name}'s value is not boolean, it's ${value}"
                }))
    }

    void objParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean, { def flags, def context, def name, def value -> }))
    }

    void numericRangeParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean, { def flags, String context, String name, def value ->
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

    void listOfStringsParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean, { Validator validator, String context, String name, def value ->
                if (!validator.hasFlag(Flags.AllowNullListOptions)) {
                    assert value != null: "${context}: '${name}' value can't be null"
                }

                if (value != null) {
                    assert (value as List<String> != null): "${context}: ${name}'s value must be convertible to a list of strings, but it's ${value.class} = ${value}"
                }
            }))
    }

    private static String validateStringValue(
            Validator validator,
            String context, String name,
            def value,
            Map options)
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

        if (!options.getOrDefault("canBeEmpty", false) && !validator.hasFlag(Flags.AllowEmptyOptionValueStrings)) {
            assert val != "": "${context}: '${name}''s value can't be empty"
        }

        return val
    }
    
    void stringParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { Validator validator,  String context, String name, def value ->
                    validateStringValue(validator, context, name, value, options)
                }))
    }

    void enumStringParameter(Map options)
    {
        assert options.name
        assert options.values

        def validValues = new HashSet<String>(options.values as List<String>)

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { Validator validator, String context, String name, def value ->
                    def val = validateStringValue(validator, context, name, value, options)
                    assert validValues.contains(val) : "${context}: '${name}''s value is not supported. Valid values: ${validValues}"
                }))
    }

    void mapParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { def flags, String context, String name, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"
                    assert value instanceof Map: "${context}: '${name}''s value must be Map, not ${value.class}"
                }))
    }

    void intParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { def flags, String context, String name, def value ->
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
    final HashSet<String> mandatoryParameters = [] as HashSet
}

