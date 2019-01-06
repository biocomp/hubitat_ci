package biocomp.hubitatCiTest.util

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
                    assert value != null: "${context}: ${name} value can't be null"
                    assert value instanceof String: "${context}: ${name}'s value must be String, not ${value.class}"
                    String val = value as String
                    assert val != null: "${context}: ${name} value can't be null"
                    assert val != "": "${context}: ${name}'s value can't be empty"
                }))
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
        this.supportedParameters_ = supportedParameters
        this.mandatoryParameters_ = mandatoryParameters
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
        def mandatoryParameters = mandatoryParameters_.clone() as HashSet<String>

        options?.each {
            assert it.key instanceof String : "${context}: Option's name '${it.key}' must be a String"
            def validator = supportedParameters_[it.key as String]?.validator
            assert validator : "${context}: Option '${it.key}' is not supported"
            validator(context, it.key, it.value)
            mandatoryParameters.remove(it.key)
        }

        assert mandatoryParameters.size() == 0 : "${context}: mandatory parameters '${mandatoryParameters}' not set"
    }

    private HashMap<String, Parameter> supportedParameters_ = [] as HashMap
    private HashSet<String> mandatoryParameters_ = [] as HashSet
}
