package me.biocomp.hubitat_ci.device

import groovy.transform.PackageScope
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.validation.DebuggerDetector
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

@TypeChecked
class HubitatDeviceSandbox {
    /**
     * Read script from file.
     * @param file
     */
    HubitatDeviceSandbox(File file) {
        this.file = file
        assert file
    }

    /**
     * Read script from string.
     * @param scriptText
     */
    HubitatDeviceSandbox(String scriptText) {
        this.text = scriptText
    }

    /**
     * Compile script only.
     * @param options:
     *
     * @return script object. User can now call script's methods manually.
     */
    HubitatDeviceScript compile(Map options = [:]) {
        addFlags(options, [Flags.DontRunScript])
        return setupImpl(options)
    }

    /**
     * Compile script and run initialization methods.
     * @param options (see compile() method for options).
     * @return script object. User can now call script's methods manually.
     */
    HubitatDeviceScript run(Map options = [:]) {
        return setupImpl(options)
    }

    private HubitatDeviceScript setupImpl(Map options) {
        validateAndUpdateSandboxOptions(options)

        def validator = readValidator(options)

        HubitatDeviceScript script = file ? validator.parseScript(file) : validator.parseScript(text);

        script.initialize(
                options.api as DeviceExecutor,
                validator, readUserSettingValues(options),
                options.customizeScriptBeforeRun as Closure)

        if (!validator.hasFlag(Flags.DontRunScript)) {
            script.run()
        }

        return script
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    private static DeviceValidator readValidator(Map options) {
        if (options.validationFlags)
        {
            return new DeviceValidator(options.validationFlags as List<String>)
        }

        return new DeviceValidator()
    }

    private static Map readUserSettingValues(Map options) {
        return options.userSettingValues ? options.userSettingValues as Map : [:]
    }

    private static final NamedParametersValidator optionsValidator = NamedParametersValidator.make {
        objParameter("api", notRequired(), canBeNull(), { v -> new Tuple2("DeviceExecutor", v instanceof DeviceExecutor)} )
        objParameter("userSettingValues", notRequired(), mustNotBeNull(), { v -> new Tuple2("Map<String, Object>", v as Map<String, Object>) })
        objParameter("customizeScriptBeforeRun", notRequired(), mustNotBeNull(), { v -> new Tuple2("Closure taking HubitatAppScript", v as Closure) })
        objParameter("validationFlags", notRequired(), mustNotBeNull(), { v -> new Tuple2("List<Flags>", v as List<Flags>) })
    }

    private static void validateAndUpdateSandboxOptions(Map options) {
        optionsValidator.validate("Validating sandbox options", options, EnumSet.noneOf(Flags))
    }

    static private void addFlags(Map options, List<Flags> flags) {
        options.putIfAbsent('validationFlags', [])
        (options.validationFlags as List<Flags>).addAll(flags)
    }

    final private File file = null
    final private String text = null
}