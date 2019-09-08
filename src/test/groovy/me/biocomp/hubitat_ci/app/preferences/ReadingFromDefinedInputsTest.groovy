package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.validation.DebuggerDetector
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class ReadingFromDefinedInputsTest extends Specification{
    def "Script can only read inputs that were defined, and fails for undefined"() {
        setup:
            def script = new HubitatAppSandbox("""
preferences{
    page("p", "t"){
        section(){
            input "existingInput", "bool", title: "fromPage0"
        }
    }
}

private def someInternalMethod()
{
    def good = existingInput
    def bad = missingInput
}

def methodThatUsesInputs()
{
    someInternalMethod()
}
""").run(validationFlags: [Flags.DontValidateDefinition, Flags.AllowMissingInstall])

        when:
            script.methodThatUsesInputs()

        then:
            AssertionError e = thrown()
            e.message.contains("not registered inputs: [missingInput]")
            e.message.contains("registered inputs: [existingInput]")
            e.message.contains("'someInternalMethod'")
            !e.message.contains("'methodThatUsesInputs'")
    }

}
