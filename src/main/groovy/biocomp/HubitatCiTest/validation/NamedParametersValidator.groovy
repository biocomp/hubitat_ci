package biocomp.hubitatCiTest.validation


import groovy.transform.TypeChecked

@TypeChecked
class NamedParametersValidator {
    static NamedParametersValidator make(@DelegatesTo(ParametersToValidate) Closure c) {
        def params = new ParametersToValidate()
        def makeParameters = c.rehydrate(params, c.owner, c.thisObject)
        makeParameters()

        return new NamedParametersValidator(params.supportedParameters, params.mandatoryParameters)
    }

    private NamedParametersValidator(
            HashMap<String, Parameter> supportedParameters, HashSet<String> mandatoryParameters)
    {
        this.supportedParameters = supportedParameters
        this.mandatoryParameters = mandatoryParameters
    }

    static Map addOptionAsNamedParam(Map options, String paramNameInMap, def valueToAdd) {
        if (options == null) {
            options = new HashMap<String, Object>()
        }

        if (!options.containsKey(paramNameInMap) && valueToAdd != null) {
            options[paramNameInMap] = valueToAdd
        }

        return options
    }

    @TypeChecked
    void validate(
            String context,
            Map options,
            Validator validator,
            boolean mustBeNonNull = false)
    {
        validate(context, [:], options, validator, mustBeNonNull)
    }

    @TypeChecked
    void validate(
            String context,
            Map unnamedOptions,
            Map options,
            Validator validator,
            boolean mustBeNonNull = false)
    {
        def mandatoryParameters = mandatoryParameters.clone() as HashSet<String>

        def validateOneParameter = { Map.Entry it ->
            assert it.key instanceof String: "${context}: Option's name '${it.key}' must be a String"
            def paramValidator = supportedParameters[it.key as String]?.validator
            assert paramValidator: "${context}: Option '${it.key}' is not supported. Valid options are: ${supportedParameters.keySet()}."
            paramValidator(validator, context, it.key, it.value)
            mandatoryParameters.remove(it.key)
        }

        if (unnamedOptions != null && options != null) {
            def keysInBothMaps = unnamedOptions.keySet().intersect(options.keySet());
            assert keysInBothMaps.size() == 0: "${context}: options ${keysInBothMaps} were passed both as named (via Map) and implicit. Please choose one way of passing it for simplicity."
        }

        unnamedOptions?.each(validateOneParameter)
        options?.each(validateOneParameter)

        assert mandatoryParameters.size() == 0: "${context}: mandatory parameters '${mandatoryParameters}' not set. All mandatory parameters are: ${supportedParameters.keySet().sort()}"
    }

    final HashMap<String, Parameter> supportedParameters
    final HashSet<String> mandatoryParameters
}