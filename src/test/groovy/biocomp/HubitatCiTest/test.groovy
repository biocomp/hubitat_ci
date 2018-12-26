package biocomp.HubitatCiTest

import org.codehaus.groovy.control.CompilerConfiguration
import spock.lang.Specification

// Simple Car class to save state and distance.
class Car {
    String state
    Long distance = 0
}

interface HubitatAppApi
{
    def definition(def definitionsMap)
    def preferences(def preferenceCallback)
}

// Custom Script with methods that change the Car's state.
// The Car object is passed via the binding.
abstract class HubitatAppScript extends Script {
    def invokeMethod(String name, Object args)
    {
        this.binding.privateApi."${name}" args;
    }
}

class HubitatAppSandbox
{
    HubitatAppSandbox(String filePath, def log = null)
    {
        this.fileOrScript = filePath
        this.log = log
    }

    def runScript(def api)
    {
        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript.class.name

        // Setup script binding
        def binding = new Binding(privateLog: log, privateApi: api)

        // Configure the GroovyShell.
        def shell = new GroovyShell(this.class.classLoader, binding, compilerConfiguration)

        // Run DSL script.
        shell.evaluate new File(fileOrScript)
    }

    void requiredConfigIsValid()
    {
        LinkedHashMap<String, String> definitions = null
        def api = [
            definition: { def map -> definitions = map },
            preferences: {}] as HubitatAppApi

        runScript(api)

        assert definitions
        assert definitions.name
        assert definitions.namespace
        assert definitions.author
        assert definitions.description
        assert definitions.category
        assert definitions.iconUrl
        assert definitions.iconX2Url
        assert definitions.iconX3Url
    }

    String fileOrScript = null
    def log = null
    //def app = null
    //def appApi = null
    //def driverApi = null
    //def commonMethods = null
}

class MyTestCase extends Specification {
    def sandbox = new HubitatAppSandbox("Send_Hub_Events.src/Send_Hub_Events.groovy")

    def "Script sets required properties"() 
    {
        expect:
            sandbox.requiredConfigIsValid()
    }
}