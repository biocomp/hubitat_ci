package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.apppreferences.Preferences
import biocomp.hubitatCiTest.emulation.AppDefinitionReaderApi
import biocomp.hubitatCiTest.emulation.AppExecutorApi
import biocomp.hubitatCiTest.emulation.AppPreferencesSourceApi
import groovy.transform.TypeChecked
import org.codehaus.groovy.ast.expr.AttributeExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer


class HubitatAppSandbox {
    HubitatAppSandbox(File file) {
        this.file = file
        assert file
    }

    HubitatAppSandbox(String scriptText) {
        this.text = scriptText
        assert scriptText
        assert !scriptText.empty
    }

    HubitatAppScript setupScript(
            boolean runScript = true,
            AppExecutorApi api = new NoopAppExecutor(),
            Closure<AppExecutorApi> addPreferencesReader = {
                delegate, script -> new AppPreferencesReader(script, delegate) as AppExecutorApi
            },
            Closure<AppExecutorApi> addDefinitionsReader = {
                delegate -> new AppDefinitionValidator(delegate) as AppExecutorApi
            })
    {
        return setupScriptWithPrefsAndSources(runScript, api, addPreferencesReader, addDefinitionsReader).get(
                0) as HubitatAppScript
    }

    Tuple3<HubitatAppScript, AppPreferencesSourceApi, AppDefinitionReaderApi> setupScriptWithPrefsAndSources(
            boolean runScript = true,
            AppExecutorApi api = new NoopAppExecutor(),
            Closure<AppExecutorApi> addPreferencesReader = {
                delegate, script -> new AppPreferencesReader(script, delegate) as AppExecutorApi
            })
    {
        return setupScriptWithPrefsAndSources(
                runScript,
                api,
                addPreferencesReader,
                { delegate -> new AppDefinitionValidator(delegate) as AppExecutorApi
        })
    }

    @TypeChecked
    Tuple3<HubitatAppScript, AppPreferencesSourceApi, AppDefinitionReaderApi> setupScriptWithPrefsAndSources(
            boolean runScript,
            AppExecutorApi api,
            Closure<AppExecutorApi> addPreferencesReader,
            Closure<AppExecutorApi> addDefinitionsReader)
    {

        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript.class.name
        compilerConfiguration.compilationCustomizers << makeRestrictingCustomizer()

        def shell = new GroovyShell(new SandboxClassLoader(this.class.classLoader),
                new DoNotCallMeBinding(),
                compilerConfiguration)

        HubitatAppScript script = null
        if (file) {
            script = shell.parse(file) as HubitatAppScript
        } else {
            script = shell.parse(text) as HubitatAppScript
        }

        AppExecutorApi preferencesReader = addPreferencesReader(api, script)
        AppExecutorApi definitionsReader = addDefinitionsReader(preferencesReader)

        script.api = definitionsReader
        script.run()
        return new Tuple3(script, preferencesReader, definitionsReader)
    }

    Preferences readPreferences() {
        AppPreferencesReader preferencesReader = null
        def wrapPreferencesReader = { AppExecutorApi delegate, HubitatAppScript script ->
            preferencesReader = new AppPreferencesReader(script, delegate)
            return preferencesReader
        }

        setupScript(true, new NoopAppExecutor(), wrapPreferencesReader, { it })
        return preferencesReader.getProducedPreferences()
    }

    void mandatoryConfigIsSet() {
        setupScript(true,
                new NoopAppExecutor(),
                { def script, def api -> api },
                { def api -> new AppDefinitionValidator(api) })
    }

    void runBasicValidations() {
        setupScript(true)
    }

    private static CompilationCustomizer makeRestrictingCustomizer()
    {
        def scz = new SecureASTCustomizer()
        def checker = { expr ->
            !(expr instanceof MethodCallExpression && expr.method == "println")
        } as SecureASTCustomizer.ExpressionChecker
        scz.addExpressionCheckers(checker)
        return scz
    }

    final private File file = null
    final private String text = null
}