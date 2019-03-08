package biocomp.hubitatCi


import biocomp.hubitatCi.emulation.appApi.AppExecutor
import biocomp.hubitatCi.emulation.deviceApi.DeviceExecutor
import biocomp.hubitatCi.validation.DeviceValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.AppValidator
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import org.codehaus.groovy.control.CompilerConfiguration

@TypeChecked
class HubitatDeviceSandbox {
    HubitatDeviceSandbox(File file) {
        this.file = file
        assert file
    }

    HubitatDeviceSandbox(String scriptText) {
        this.text = scriptText
    }

//    HubitatDeviceScript compile(Map options = [:]) {
//        addFlags(options, [Flags.DontRunScript])
//        return setupImpl(options)
//    }

    HubitatDeviceScript run(Map options = [:]) {
        return setupImpl(options)
    }

    /**
     * Calls run() with Flags.DontValidateDefinition and returns juts preferences, not script.
     * @param options
     * @return
     */
//    Preferences readPreferences(Map options = [:]) {
//        addFlags(options, [Flags.DontValidateDefinition])
//        setupImpl(options).getProducedPreferences()
//    }

    private HubitatDeviceScript setupImpl(Map options) {
        validateAndUpdateSandboxOptions(options)

        // Use custom HubitatDeviceScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatDeviceScript.class.name

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
//        else if (options.validator)
//        {
//            assert options.validator
//            return options.validator as AppValidator
//        }

        return new DeviceValidator()
    }
//
//    private static Map readUserSettingValues(Map options) {
//        return options.userSettingValues ? options.userSettingValues as Map : [:]
//    }
//
    private static void validateAndUpdateSandboxOptions(Map options) {
        def allKeys = new HashSet<String>(options.keySet());
//
        if (options.containsKey('api')) {
            allKeys.remove('api')

            assert options['api'] == null || options[
                    'api'] instanceof DeviceExecutor: "'api' value must be null or implement DeviceExecutor interface"
        }
//
//        if (options.containsKey('userSettingValues')) {
//            allKeys.remove('userSettingValues')
//
//            assert options['userSettingValues'] != null
//            assert (options[
//                    'userSettingValues'] as Map<String, Object>) != null: "'userSettingValues' must be a map of String->Object options"
//        }
//
//        if (options.containsKey('customizeScriptBeforeRun')) {
//            allKeys.remove('customizeScriptBeforeRun')
//
//            assert options['customizeScriptBeforeRun'] != null
//            assert options[
//                    'customizeScriptBeforeRun'] instanceof Closure: "'customizeScriptBeforeRun' should be a closure that takes HubitatDeviceScript as a single parameter"
//        }
//
        if (options.containsKey('validationFlags')) {
            allKeys.remove('validationFlags')

            assert options['validationFlags'] != null
            assert options[
                    'validationFlags'] as List<Flags>: "'validationFlags' should be a list of validation flags"
        }
//
//        if (options.containsKey('validator')) {
//            allKeys.remove('validator')
//
//            assert options['validator'] != null
//            assert options[
//                    'validator'] as AppValidator: "'validator' should be an instance of 'AppValidator'"
//        }
//
//        if (options.containsKey('noValidation')) {
//            allKeys.remove('noValidation')
//
//            assert options['noValidation'] != null
//
//            if (options.noValidation) {
//                addFlags(options, [Flags.DontValidateDefinition, Flags.DontValidatePreferences])
//            }
//        }
//
        assert allKeys.isEmpty(): "These options are not supported: ${allKeys}"
    }
//
//    static private void addFlags(Map options, List<Flags> flags) {
//        options.putIfAbsent('validationFlags', [])
//        (options.validationFlags as List<Flags>).addAll(flags)
//    }


    final private File file = null
    final private String text = null
}