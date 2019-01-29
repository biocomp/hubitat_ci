package biocomp.hubitatCiTest


import biocomp.hubitatCiTest.apppreferences.Preferences
import biocomp.hubitatCiTest.validation.Flags
import biocomp.hubitatCiTest.validation.Validator
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.CompilerConfiguration
import sun.util.calendar.ZoneInfo

import java.text.DecimalFormat
import java.text.SimpleDateFormat

@TypeChecked
class HubitatAppSandbox {
    HubitatAppSandbox(File file) {
        this.file = file
        assert file
    }

    HubitatAppSandbox(String scriptText) {
        this.text = scriptText
    }

    HubitatAppScript compile(Map options = [:]) {
        addFlags(options, [Flags.DontRunScript])
        return setupImpl(options)
    }

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

    private HubitatAppScript setupImpl(Map options) {
        validateAndUpdateSandboxOptions(options)

        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript.class.name

        def validator = readValidator(options)

        HubitatAppScript script = file ? validator.parseScript(file) : validator.parseScript(text);

        script.initialize(options.api as AppExecutor, validator, readUserSettingValues(options), options.customizeScriptBeforeRun as Closure)

        if (!validator.hasFlag(Flags.DontRunScript)) {
            script.run()
        }

        return script
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    private static Validator readValidator(Map options) {
        if (options.validationFlags)
        {
            return new Validator(options.validationFlags as List<String>)
        }
        else if (options.validator)
        {
            assert options.validator
            return options.validator as Validator
        }

        return new Validator()
    }

    private static Map readUserSettingValues(Map options) {
        return options.userSettingValues ? options.userSettingValues as Map : [:]
    }

    private static void validateAndUpdateSandboxOptions(Map options) {
        def allKeys = new HashSet<String>(options.keySet());

        if (options.containsKey('api')) {
            allKeys.remove('api')

            assert options['api'] == null || options[
                    'api'] instanceof AppExecutor: "'app' value must be null or implement AppExecutor interface"
        }

        if (options.containsKey('userSettingValues')) {
            allKeys.remove('userSettingValues')

            assert options['userSettingValues'] != null
            assert (options[
                    'userSettingValues'] as Map<String, Object>) != null: "'userSettingValues' must be a map of String->Object options"
        }

        if (options.containsKey('customizeScriptBeforeRun')) {
            allKeys.remove('customizeScriptBeforeRun')

            assert options['customizeScriptBeforeRun'] != null
            assert options[
                    'customizeScriptBeforeRun'] instanceof Closure: "'customizeScriptBeforeRun' should be a closure that takes HubitatAppScript as a single parameter"
        }

        if (options.containsKey('validationFlags')) {
            allKeys.remove('validationFlags')

            assert options['validationFlags'] != null
            assert options[
                    'validationFlags'] as List<Flags>: "'validationFlags' should be a list of validation flags"
        }

        if (options.containsKey('validator')) {
            allKeys.remove('validator')

            assert options['validator'] != null
            assert options[
                    'validator'] as Validator: "'validator' should be an instance of 'Validator'"
        }

        if (options.containsKey('noValidation')) {
            allKeys.remove('noValidation')

            assert options['noValidation'] != null

            if (options.noValidation) {
                addFlags(options, [Flags.DontValidateDefinition, Flags.DontValidatePreferences])
            }
        }

        assert allKeys.isEmpty(): "These options are not supported: ${allKeys}"
    }

    static private void addFlags(Map options, List<Flags> flags) {
        options.putIfAbsent('validationFlags', [])
        (options.validationFlags as List<Flags>).addAll(flags)
    }


    final private File file = null
    final private String text = null
}