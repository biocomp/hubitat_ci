package me.biocomp.hubitat_ci.validation

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * Validates map of options given map of supported/mandatory parameters and their validators.
 */
@TypeChecked
class NamedParametersValidator {
    static NamedParametersValidator make(@DelegatesTo(ParametersToValidate) Closure c) {
        def params = new ParametersToValidate()
        def makeParameters = c.rehydrate(params, c.owner, c.thisObject)
        
        makeParameters()

        return new NamedParametersValidator(params.supportedParameters, params.mandatoryParameters)
    }

    private NamedParametersValidator(
            HashMap<String, ParametersToValidate.Parameter> supportedParameters, HashSet<String> mandatoryParameters)
    {
        this.supportedParameters = supportedParameters
        this.mandatoryParameters = mandatoryParameters
    }

    @CompileStatic
    static Map addOptionAsNamedParam(Map options, String paramNameInMap, def valueToAdd) {
        if (options == null) {
            options = new HashMap<String, Object>()
        }

        if (!options.containsKey(paramNameInMap) && valueToAdd != null) {
            options[paramNameInMap] = valueToAdd
        }

        return options
    }

    /**
     * Copy contents into provided containers.
     */
    @CompileStatic
    void copyTo(
            HashMap<String, ParametersToValidate.Parameter> supportedParameters,
            HashSet<String> mandatoryParameters)
    {
        this.supportedParameters.each { supportedParameters[it.key] = it.value }
        this.mandatoryParameters.each { mandatoryParameters.add(it) }
    }

    enum ValidatorOption
    {
        IgnoreMissingMandatoryInputs
    }

    /**
     * Validate map of options.
     * @param context - string is added to any error reported by validator.
     * @param options - map to be validated.
     * @param validator - validator object is passed into validation callbacks (and used to enable/disable validations).
     * @param validatorOptions - extra options {@see #ValidationOption}
     */
    @CompileStatic
    void validate(
            String context,
            Map options,
            EnumSet<Flags> validationFlags,
            EnumSet<ValidatorOption> validatorOptions = EnumSet.noneOf(ValidatorOption))
    {
        validate(context, [:], options, validationFlags, validatorOptions)
    }


    /**
     * Validate map of options with unnamed parameters.
     * Same as {@link #validate(String, Map, EnumSet<Flags>, EnumSet<ValidationOption>)},
     *  but also takes unnamedOptions. This is used when method takes some arguments, and map of options.
     *  Map may also have same parameters that were passed separately.
     *  This method ensures this is not the case, and also applies validator logic to those extra parameters.
     *  <p>Example:</p>
     *  <code>
     *      // Two overloads of foo, first takes common parameter outside of Map of options.
     *      void foo(int usefulOption1, Map options); //  Where 'options' can also contain 'usefulOption'.
     *      void foo(Map options); //  Where 'options' can also contain 'usefulOption'.
     *  </code>
     * @param context - string is added to any error reported by validator.
     * @param Map unnamedOptions - options that were not named at method call.
     * @param options - map to be validated.
     * @param validator - validator object is passed into validation callbacks (and used to enable/disable validations).
     * @param validatorOptions - extra options {@see #ValidationOption}
     */
    @CompileStatic
    void validate(
            String context,
            Map unnamedOptions,
            Map options,
            EnumSet<Flags> validationFlags,
            EnumSet<ValidatorOption> validatorOptions = EnumSet.noneOf(ValidatorOption))
    {
        def mandatoryParameters = mandatoryParameters.clone() as HashSet<String>

        def validateOneParameter = { Map.Entry it ->
            assert it.key instanceof String: "${context}: Option's name '${it.key}' must be a String"
            def paramValidator = supportedParameters[it.key as String]?.validator
            assert paramValidator: "${context}: Option '${it.key}' is not supported. Valid options are: ${supportedParameters.keySet()}."
            paramValidator(validationFlags, context, it.value)
            mandatoryParameters.remove(it.key)
        }

        if (unnamedOptions != null && options != null) {
            def keysInBothMaps = unnamedOptions.keySet().intersect(options.keySet());
            assert keysInBothMaps.size() == 0: "${context}: options ${keysInBothMaps} were passed both as named (via Map) and implicit. Please choose one way of passing it for simplicity."
        }

        unnamedOptions?.each(validateOneParameter)
        options?.each(validateOneParameter)

        if (!validatorOptions.contains(ValidatorOption.IgnoreMissingMandatoryInputs)) {
            assert mandatoryParameters.size() == 0: "${context}: mandatory parameters '${mandatoryParameters}' not set. All mandatory parameters are: ${supportedParameters.keySet().sort()}"
        }
    }

    private final HashMap<String, ParametersToValidate.Parameter> supportedParameters
    private final HashSet<String> mandatoryParameters
}