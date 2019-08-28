package me.biocomp.hubitat_ci.app

import biocomp.hubitatCi.api.app_api.AppExecutor
import biocomp.hubitatCi.app.preferences.Preferences
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.NamedParametersValidator
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

/**
 * This sanbox can load script from file or string,
 * parse it, while wrapping into a sandbox.
 *
 * Then it can run initialization methods and
 * produce script object that user can then test further by calling its methods.
 */
@TypeChecked
class HubitatAppSandbox {
    /**
     * Read script from file.
     * @param file
     */
    HubitatAppSandbox(File file) {
        this.file = file
        assert file
    }

    /**
     * Read script from string.
     * @param scriptText
     */
    HubitatAppSandbox(String scriptText) {
        this.text = scriptText
    }

    /**
     * Compile script only.
     * @param options:
     *
     * @return script object. User can now call script's methods manually.
     */
    HubitatAppScript compile(Map options = [:]) {
        addFlags(options, [Flags.DontRunScript])
        return setupImpl(options)
    }

    /**
     * Compile script and run initialization methods.
     * @param options (see compile() method for options).
     * @return script object. User can now call script's methods manually.
     */
    HubitatAppScript run(Map options = [:]) {
        return setupImpl(options)
    }

    /**
     * Calls run() with Flags.DontValidateDefinition and returns juts preferences, not script.
     * @param options
     * @return
     */
    Preferences readPreferences(Map options = [:]) {
        addFlags(options, [Flags.DontValidateDefinition])
        setupImpl(options).getProducedPreferences()
    }

    private HubitatAppScript setupImpl(Map options) {validateAndUpdateSandboxOptions(options)

        def validator = readValidator(options)

        HubitatAppScript script = file ? validator.parseScript(file) : validator.parseScript(text);

        script.initialize(options.api as AppExecutor, validator, readUserSettingValues(options), options.customizeScriptBeforeRun as Closure)

        if (!validator.hasFlag(Flags.DontRunScript)) {
            script.run()
        }

        return script
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    private static AppValidator readValidator(Map options) {
        if (options.validationFlags)
        {
            return new AppValidator(options.validationFlags as List<String>)
        }
        else if (options.validator)
        {
            assert options.validator
            return options.validator as AppValidator
        }

        return new AppValidator()
    }

    private static Map readUserSettingValues(Map options) {
        return options.userSettingValues ? options.userSettingValues as Map : [:]
    }

    private static final NamedParametersValidator optionsValidator = NamedParametersValidator.make {
        objParameter("api", notRequired(), canBeNull(), { v -> new Tuple2("AppExecutor", v instanceof AppExecutor)} )
        objParameter("userSettingValues", notRequired(), mustNotBeNull(), { v -> new Tuple2("Map<String, Object>", v as Map<String, Object>) })
        objParameter("customizeScriptBeforeRun", notRequired(), mustNotBeNull(), { v -> new Tuple2("Closure taking HubitatAppScript", v as Closure) })
        objParameter("validationFlags", notRequired(), mustNotBeNull(), { v -> new Tuple2("List<Flags>", v as List<Flags>) })
        objParameter("validator", notRequired(), mustNotBeNull(), { v -> new Tuple2("AppValidator", v as AppValidator) })
        boolParameter("noValidation", notRequired())
    }

    @CompileStatic
    private static void validateAndUpdateSandboxOptions(Map options) {
        optionsValidator.validate("Validating sandbox options", options, EnumSet.noneOf(Flags))

        if (options.noValidation) {
            addFlags(options, [Flags.DontValidateDefinition, Flags.DontValidatePreferences])
        }
    }

    static private void addFlags(Map options, List<Flags> flags) {
        options.putIfAbsent('validationFlags', [])
        (options.validationFlags as List<Flags>).addAll(flags)
    }

    final private File file = null
    final private String text = null
}