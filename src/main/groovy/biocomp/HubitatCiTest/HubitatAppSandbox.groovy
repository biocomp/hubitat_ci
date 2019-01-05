package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.apppreferences.Preferences
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

    Preferences readPreferences()
    {
        (setupScript(AppPreferencesReader, true) as AppPreferencesReader).producePreferences()
    }

    void mandatoryConfigIsSet() {
        setupScript(AppDefinitionValidator, true)
    }

    File file = null
    String text = null
}