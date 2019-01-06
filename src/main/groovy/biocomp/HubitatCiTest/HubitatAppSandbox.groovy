package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.apppreferences.Preferences
import biocomp.hubitatCiTest.emulation.AppExecutorApi
import groovy.transform.CompileStatic
import org.codehaus.groovy.control.CompilerConfiguration

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

    @CompileStatic
    HubitatAppScript setupScript(boolean runScript = true, AppExecutorApi api = new AutoAppExecutor()) {
        println this.@text

        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass =  HubitatAppScript.class.name

        def shell = new GroovyShell(new SandboxClassLoader(this.class.classLoader), new DoNotCallMeBinding(), compilerConfiguration)

        HubitatAppScript script = null
        if (file)
        {
            script = shell.parse(file) as HubitatAppScript
        }
        else
        {
            script = shell.parse(text) as HubitatAppScript
        }

        script.api = new AppExecutorWithPreferencesAndDefinitions(script, api)
        script.run()
        return script
    }

    @CompileStatic
    Preferences readPreferences()
    {
        Cccc c = new Cccc()
        c.method()
        c.name = "blah"
        println "name = ${c.name}"
        c.setName("aaaa")
        println "name = ${c.name}"
        println "val = ${c.val}"

        HubitatAppScript script = setupScript(true)
        AppExecutorApi api = script.api
        AppExecutorWithPreferencesAndDefinitions apiCasted = api as AppExecutorWithPreferencesAndDefinitions
        AppPreferencesReader reader = apiCasted.preferencesReader
        Preferences pr = reader.getProducedPreferences()
        return pr
    }

    void mandatoryConfigIsSet() {
        setupScript()
    }

    File file = null
    String text = null
}