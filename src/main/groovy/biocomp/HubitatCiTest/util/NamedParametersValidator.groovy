package biocomp.hubitatCiTest.util

import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked


@TupleConstructor
class Parameter
{
    final String name
    final boolean required
    final Closure validator
}

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
                    assert value != null: "${context}: ${name} value can't be null"
                    String valuePrinted = value.toString()
                    assert valuePrinted == "false" || valuePrinted == "true" : "${context}: ${name}'s value is not boolean, it's ${value}"
                }))
    }

    private static String validateStringValue(
            EnumSet<ValidationFlags> flags,
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

        if (!options.getOrDefault("canBeEmpty", false) && !flags.contains(ValidationFlags.AllowEmptyOptionValueStrings)) {
            assert val != "": "${context}: '${name}''s value can't be empty"
        }

        return val
    }
    
    void stringParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { EnumSet<ValidationFlags> flags,  String context, String name, def value ->
                    validateStringValue(flags, context, name, value, options)
                }))
    }

    void enumStringParameter(Map options)
    {
        assert options.name
        assert options.values

        def validValues = new HashSet<String>(options.values as List<String>)

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { EnumSet<ValidationFlags> flags, String context, String name, def value ->
                    def val = validateStringValue(flags, context, name, value, options)
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
                    assert value != null: "${context}: ${name} value can't be null"

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

@TypeChecked
class NamedParametersValidator
{
    static NamedParametersValidator make(@DelegatesTo(ParametersToValidate) Closure c)
    {
        def params = new ParametersToValidate()
        def makeParameters = c.rehydrate(params, c.owner, c.thisObject)
        makeParameters()

        return new NamedParametersValidator(params.supportedParameters, params.mandatoryParameters)
    }

    private NamedParametersValidator(HashMap<String, Parameter> supportedParameters, HashSet<String> mandatoryParameters)
    {
        this.supportedParameters = supportedParameters
        this.mandatoryParameters = mandatoryParameters
    }

    static Map addOptionAsNamedParam(Map options, String paramNameInMap, def valueToAdd) {
        if (options == null)
        {
            options = new HashMap<String, Object>()
        }

        if (!options.containsKey(paramNameInMap) && valueToAdd != null)
        {
            options[paramNameInMap] = valueToAdd
        }

        return options
    }

     @TypeChecked
     void validate(
            String context,
            Map options,
            EnumSet<ValidationFlags> validationFlags,
            boolean mustBeNonNull = false)
    {
        def mandatoryParameters = mandatoryParameters.clone() as HashSet<String>

        options?.each {
            assert it.key instanceof String: "${context}: Option's name '${it.key}' must be a String"
            def validator = supportedParameters[it.key as String]?.validator
            assert validator: "${context}: Option '${it.key}' is not supported. Valid options are: ${supportedParameters.keySet()}."
            validator(validationFlags, context, it.key, it.value)
            mandatoryParameters.remove(it.key)
        }

        assert mandatoryParameters.size() == 0: "${context}: mandatory parameters '${mandatoryParameters}' not set. All mandatory parameters are: ${supportedParameters.keySet().sort()}"
    }

    final HashMap<String, Parameter> supportedParameters
    final HashSet<String> mandatoryParameters
}
