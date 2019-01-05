package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.Preferences
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

    Script setupScript(Class scriptClass, boolean runScript = true) {
        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = scriptClass.name

        def shell = new GroovyShell(new SandboxClassLoader(this.class.classLoader), new DoNotCallMeBinding(), compilerConfiguration)

        Script script = null
        if (file)
        {
            script = shell.parse(file)
        }
        else
        {
            script = shell.parse(text)
        }

        script.run()
        return script
    }

    @CompileStatic
    Preferences readPreferences()
    {
        HubitatAppScript script = setupScript(HubitatAppScript, false) as HubitatAppScript
        script.@readPreferences = true
        script.run()
        return script.getProducedPreferences()
    }

    void mandatoryConfigIsSet() {
        setupScript(AppDefinitionValidator, true)
    }

    File file = null
    String text = null
}