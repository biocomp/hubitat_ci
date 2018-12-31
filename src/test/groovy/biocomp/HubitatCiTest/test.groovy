package biocomp.HubitatCiTest

import org.codehaus.groovy.control.CompilerConfiguration
import spock.lang.Specification

interface HubitatAppApi {
    def definition(def definitionsMap)
    def preferences(def preferenceCallback)
    void sendHubCommand(MyHubAction action)

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
    boolean getEnabled()
    boolean getLogEnable()
}

// Custom Script with methods that change the Car's state.
// The Car object is passed via the binding.
abstract class HubitatAppScript extends Script {
    def printer = null

    @Delegate
    App app
    /*
    def invokeMethod(String name, Object args) {
        def methods = this.binding.privateApi.metaClass.methods
        def foundApiMethod = methods.find { it.getName() == name }
        if (foundApiMethod) {
            return this.binding.privateApi."${name}"(*args)
        }

        return super.invokeMethod(name, args)
    }

    @Override
    def getProperty(String name)
    {
        if (name != "binding") {
            def getName = "get${name.capitalize()}"
            def methods = this.binding.privateApi.metaClass.methods
            def foundApiMethod = methods.find { it.getName() == getName }
            if (foundApiMethod) {
                return this.binding.privateApi."${getName}"()
            }
        }

        return super.getProperty(name)
    }

    @Override
    void setProperty(String name, Object newValue)
    {
        def setName = "set${name.capitalize()}"
        def methods = this.binding.privateApi.metaClass.methods
        def foundApiMethod = methods.find { it.getName() == setName }
        if (foundApiMethod) {
            this.binding.privateApi."${setName}"(newValue)
        }

        super.setProperty(name, newValue)
    }*/
}

class HubitatAppSandbox {
    HubitatAppSandbox(String filePath) {
        this.fileOrScript = filePath
    }

    class MyClassLoader extends ClassLoader {
        MyClassLoader(ClassLoader parent) {
            super(parent)
        }

        @Override
        Class<?> loadClass(String name, boolean resolve) {
            super.loadClass(mapClassName(name), resolve)
        }

        private static String mapClassName(String name) {
            switch (name) {
                case "physicalgraph.device.HubAction":
                    println "Overridden!"
                    return "biocomp.HubitatCiTest.MyHubAction"

                default:
                    return name
            }
        }
    }

    Script setupScript(def api) {
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

    void mandatoryConfigIsSet() {
        LinkedHashMap<String, String> definitions = null
        def api = [
                definition : { def map -> definitions = map },
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

interface App
{
    def foo()

    def zap(def o, String s)
    def zap(def o)
    def zap()

    def getBar()
    void setBar(def val)
}

class Api implements App
{
    @Override
    def foo() { println 'foo() called!' }

    @Override
    def zap(def o, String s) { println 'zap(2) called!' }

    @Override
    def zap(def o) { println 'zap(1) called!' }

    @Override
    def zap() { println 'zap(0) called!' }

    @Override
    def getBar() { println "getBar() called!, returns ${bar_}" }

    @Override
    void setBar(def val) { println "setBar(${val}) called!" }

    private def bar_ = 42
}



abstract class HubitatAppScript2 extends Script
{
    @Delegate
    private App app_

    void setApp(App app)
    {
        this.app_ = app
    }
}


class MyTestCase extends Specification {
    def sandbox = new HubitatAppSandbox("Send_Hub_Events.src/Send_Hub_Events.groovy")
//
//    def "Script sets mandatory properties"() {
//        expect:
//        sandbox.mandatoryConfigIsSet()
//    }
//
//    def "Installation succeeds"() {
//        given:
//        def api = Mock(HubitatAppApi)
//        def script = sandbox.setupScript(api)
//
//        when:
//        script.installed()
//
//        then:
//        _ * api.getPresenceDevices
//        23 * api.subscribe(*_)
//        0 * api.sendHubCommand
//    }
//
//    def "Installation with modes requires one more subscription"() {
//        given:
//        def api = Mock(HubitatAppApi)
//        def script = sandbox.setupScript(api)
//
//        when:
//        script.installed()
//
//        then:
//        _ * api.getModes() >> "Modes?"
//        _ * api.getPresenceDevices
//        23 * api.subscribe(_, _, _)
//        1 * api.subscribe(_, _)
//    }
//
//    def "When enabled, sends setup command during installation"() {
//        given:
//        def api = Mock(HubitatAppApi)
//        def script = sandbox.setupScript(api)
//
//        when:
//        script.installed()
//
//        then:
//        _*api.enabled >> true
//        1 * api.sendHubCommand({MyHubAction action -> action.msg == "test"})
//    }

    def "Test another thing"()
    {
        def api = new Api()

/*App.class.declaredMethods.each{
    if (!${it}.name.startsWith "get" && !${it}.name.startsWith "set")
    {
        binding."${it.name}" = api.&"${it.name}"
    }
}*/

        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript2.class.name

// Setup script binding
        def binding = new Binding(privateApi: api)

// Configure the GroovyShell.
        def shell = new GroovyShell(this.class.classLoader, binding, compilerConfiguration)


// Run DSL script.
        Script script = shell.parse('''
println "calling foo"
foo()

println "calling zap(2)"
zap(1, "test")

println "calling zap(0)"
zap()

println "bar = ${bar}" 
println "Setting bar to 123"
bar = 123
assert bar == 123
println "Print from script!"
''')
        assert api.bar == 42
        script.run()

        assert api.bar == 123
    }

}