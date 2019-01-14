package biocomp.hubitatCiTest.util

import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import com.sun.istack.internal.NotNull
import com.sun.org.apache.xpath.internal.operations.Bool
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

import java.lang.reflect.Type

class StringParameter
{
    void validate()
    {

    }
}

class BooleanParameter
{
    void validate()
    {

    }
}

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
                { @NotNull String context, @NotNull String name, def value ->
                    assert value != null: "${context}: ${name} value can't be null"
                    String valuePrinted = value.toString()
                    assert valuePrinted == "false" || valuePrinted == "true" : "${context}: ${name}'s value is not boolean, it's ${value}"
                }))
    }

    void stringParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { @NotNull String context, @NotNull String name, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"
                    assert value instanceof String: "${context}: '${name}''s value must be String, not ${value.class}"
                    String val = value as String

                    if (!options.getOrDefault("canBeEmpty", false)) {
                        assert val != "": "${context}: '${name}''s value can't be empty"
                    }
                }))
    }

    void enumStringParameter(Map options)
    {
        assert options.name
        assert options.values

        def validValues = new HashSet<String>(options.values as List<String>)

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { @NotNull String context, @NotNull String name, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"
                    assert value instanceof String: "${context}: '${name}''s value must be String, not ${value.class}"
                    String val = value as String

                    assert validValues.contains(val) : "${context}: '${name}''s value is not supported. Valid values: ${validValues}"
                }))
    }

    void mapParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { @NotNull String context, @NotNull String name, def value ->
                    assert value != null: "${context}: '${name}' value can't be null"
                    assert value instanceof Map: "${context}: '${name}''s value must be Map, not ${value.class}"
                }))
    }

    void intParameter(Map options)
    {
        assert options.name

        addParameter(new Parameter(options.name as String, options.get("required", false) as boolean,
                { @NotNull String context, @NotNull String name, def value ->
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

    private NamedParametersValidator(@NotNull HashMap<String, Parameter> supportedParameters, @NotNull HashSet<String> mandatoryParameters)
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
            @NotNull String context,
            Map options,
            boolean mustBeNonNull = false)
    {
        def mandatoryParameters = mandatoryParameters.clone() as HashSet<String>

        options?.each {
            assert it.key instanceof String: "${context}: Option's name '${it.key}' must be a String"
            def validator = supportedParameters[it.key as String]?.validator
            assert validator: "${context}: Option '${it.key}' is not supported"
            validator(context, it.key, it.value)
            mandatoryParameters.remove(it.key)
        }

        assert mandatoryParameters.size() == 0: "${context}: mandatory parameters '${mandatoryParameters}' not set. All mandatory parameters are: ${supportedParameters.keySet().sort()}"
    }

    final HashMap<String, Parameter> supportedParameters
    final HashSet<String> mandatoryParameters
}
