package me.biocomp.hubitat_ci.device


import biocomp.hubitatCi.api.device_api.DeviceExecutor
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.NamedParametersValidator
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

@TypeChecked
class HubitatDeviceSandbox {
    HubitatDeviceSandbox(File file) {
        this.file = file
        assert file
    }

    HubitatDeviceSandbox(String scriptText) {
        this.text = scriptText
    }

    HubitatDeviceScript run(Map options = [:]) {
        return setupImpl(options)
    }

    private HubitatDeviceScript setupImpl(Map options) {
        validateAndUpdateSandboxOptions(options)

        def validator = readValidator(options)

        HubitatDeviceScript script = file ? validator.parseScript(file) : validator.parseScript(text);

        script.initialize(options.api as DeviceExecutor, validator/*, readUserSettingValues(options), options.customizeScriptBeforeRun as Closure*/)

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

    private static final NamedParametersValidator optionsValidator = NamedParametersValidator.make {
        objParameter("api", notRequired(), canBeNull(), { v -> new Tuple2("DeviceExecutor", v instanceof DeviceExecutor)} )
        objParameter("validationFlags", notRequired(), mustNotBeNull(), { v -> new Tuple2("List<Flags>", v as List<Flags>) })
    }

    private static void validateAndUpdateSandboxOptions(Map options) {
        optionsValidator.validate("Validating sandbox options", options, EnumSet.noneOf(Flags))
    }

    final private File file = null
    final private String text = null
}