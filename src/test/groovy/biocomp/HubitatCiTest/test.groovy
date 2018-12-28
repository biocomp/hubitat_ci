package biocomp.HubitatCiTest

import org.codehaus.groovy.control.CompilerConfiguration
import spock.lang.Specification

interface HubitatAppApi
{
    def definition(def definitionsMap)
    def preferences(def preferenceCallback)
    def sendSetup()
    def subscribe(def device, String propertyName, def handler)
    def subscribe(def device, def handler)

    def getPresenceDevices()
    void setPresenceDevices(def value)
    
    def getOmniDevices()
    void setOmniDevices(def value)

    def getMotionDevices()
    void setMotionDevices(def value)

    def getContactDevices()
    void setContactDevices(def value)

    def getAccelerationDevices()
    void setAccelerationDevices(def value)

    def getMultiSensors()
    void setMultiSensors(def value)

    def getOmniSensors()
    void setOmniSensors(def value)

    def getSwitchDevices()
    void setSwitchDevices(def value)

    def getDimmerDevices()
    void setDimmerDevices(def value)

    def getLocks()
    void setLocks(def value)

    def getModes()
    void setModes(def value)
    
    def getLocation()
    void setLocation(def value)

    def getIp()

    def getEnabled()
}

// Custom Script with methods that change the Car's state.
// The Car object is passed via the binding.
abstract class HubitatAppScript extends Script {
    //def printer = null

    def methodMissing(String name, Object args)
    {
        return this.binding.privateApi."${name}"(*args)
    }

    def propertyMissing(String name)
    {
         def methods = getMetaClass().getMethods()
         def foundMethod = methods.find { it.getName() == name}

         printer "property missing for ('${name}')"
         if (foundMethod){
             printer "returning method '${name}'"
             return foundMethod;
         }

        printer "calling privateApi.\"get${name.capitalize()}\"()"
        return this.binding.privateApi."get${name.capitalize()}"()
    }
}

class HubitatAppSandbox
{
    HubitatAppSandbox(String filePath)
    {
        this.fileOrScript = filePath
    }

    class MyClassLoader extends ClassLoader
    {
        MyClassLoader(ClassLoader parent) {
            super(parent)
        }

        @Override
        Class<?> loadClass(String name, boolean resolve)
        {
            super.loadClass(mapClassName(name), resolve)
        }

        private static String mapClassName(String name)
        {
            switch (name)
            {
                case "physicalgraph.device.HubAction":
                    return "biocomp.HubitatCiTest.MyHubAction"

                default:
                    return name
            }
        }
    }

    Script setupScript(def api)
    {
        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript.class.name

        // Setup script binding
        def binding = new Binding(privateApi: api)

        // Configure the GroovyShell.
        def shell = new GroovyShell(new MyClassLoader(this.class.classLoader), binding, compilerConfiguration)

        // Run DSL script.
        Script script = shell.parse(new File(fileOrScript))
        script.printer = { def str -> println str }
        script.run()
        return script
    }

    void mandatoryConfigIsSet()
    {
        LinkedHashMap<String, String> definitions = null
        def api = [
            definition: { def map -> definitions = map },
            preferences: {}] as HubitatAppApi

        setupScript(api)

        // Checking mandatory properties
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
}

class MyTestCase extends Specification {
    def sandbox = new HubitatAppSandbox("Send_Hub_Events.src/Send_Hub_Events.groovy")

    def "Script sets mandatory properties"() 
    {
        expect:
            sandbox.mandatoryConfigIsSet()
    }

    def "Installation succeeds"()
    {
        given:
            def api = Mock(HubitatAppApi)
            def script = sandbox.setupScript(api)

        when:
            script.installed()

        then:
            _*api.getPresenceDevices
            23*api.subscribe(*_)
    }

    def "Installation with modes requires one more subscription"()
    {
        given:
        def api = Mock(HubitatAppApi)
        def script = sandbox.setupScript(api)

        when:
        script.installed()

        then:
        _*api.getModes() >> "Modes?"
        _*api.getPresenceDevices
        23*api.subscribe(_,_,_)
        1*api.subscribe(_,_)
    }
}