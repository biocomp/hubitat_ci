package biocomp.HubitatCiTest

import org.codehaus.groovy.control.CompilerConfiguration
import spock.lang.Specification

interface HubitatAppApi {
    def definition(def definitionsMap)
    def preferences(def preferenceCallback)
    void sendHubCommand(HubAction action)

    def subscribe(def device, String propertyName, def handler)
    def subscribe(def device, def handler)

    def getPresenceDevices()
    def getOmniDevices()
    def getMotionDevices()
    def getContactDevices()
    def getAccelerationDevices()
    def getMultiSensors()
    def getOmniSensors()
    def getSwitchDevices()
    def getDimmerDevices()
    def getLocks()
    def getModes()
    Location getLocation()
    String getIp()
    boolean getEnabled()
    boolean getLogEnable()
}

// Custom Script that redirects most unknown calls to app_.
abstract class HubitatAppScript extends Script {
    @Delegate
    private HubitatAppApi app_

    void setApp(HubitatAppApi app)
    {
        this.app_ = app
    }

    /*
    Don't let Script base class to redirect properties to binding.
    Also redirect to local methods (if present) first.
     */
    @Override
    Object getProperty(String property) {
        // TODO: search here is linear, need to fix.
        def foundMethod = getMetaClass().methods.find({it.name == property})
        if (foundMethod)
        {
            return foundMethod
        }

        return getMetaClass().getProperty(this as GroovyObjectSupport, property)
    }

    /*
    Don't let Script base class to redirect properties to binding.
     */
    @Override
    void setProperty(String property, Object newValue) {
        getMetaClass().setProperty(this as GroovyObjectSupport, property, newValue)
    }
}

class DoNotCallMeBinding extends Binding
{
    @Override
    def getProperty(String property)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    def getVariable(String name)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    java.util.Map getVariables()
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    boolean hasVariable(String name)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    void setProperty(String property, Object newValue)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    void setVariable(String name, Object value)
    {
        ThisBindingShouldNotBeUsed()
    }

    private static ThisBindingShouldNotBeUsed()
    {
        throw new SecurityException("This Binding's methods should not be called")
    }
}


class HubitatAppSandbox {
    HubitatAppSandbox(String filePath) {
        this.fileOrScript = filePath
    }

    /* Will replace framework classes with test implementations,
    * and validate for allowed classes and methods*/
    class MyClassLoader extends ClassLoader {
        MyClassLoader(ClassLoader parent) {
            super(parent)
        }

        @Override
        Class<?> loadClass(String name, boolean resolve) {
            super.loadClass(mapClassName(name), resolve)
        }

        private static String mapClassName(String name) {
            return name.replaceAll('''physicalgraph[\\.$]device[\\.$]''', "biocomp.HubitatCiTest.")
        }
    }

    Script setupScript(def api) {
        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript.class.name

        def shell = new GroovyShell(new MyClassLoader(this.class.classLoader), new DoNotCallMeBinding(), compilerConfiguration)

        Script script = shell.parse(new File(fileOrScript))
        script.setApp(api)
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

class MyTestCase extends Specification {
    def sandbox = new HubitatAppSandbox("Send_Hub_Events.src/Send_Hub_Events.groovy")

    def "Script sets mandatory properties"() {
       expect:
       sandbox.mandatoryConfigIsSet()
   }

    def "Installation succeeds"() {
        given:
        def api = Mock(HubitatAppApi)
        def script = sandbox.setupScript(api)

        when:
        script.installed()

        then:
        _ * api.getPresenceDevices
        23 * api.subscribe(*_)
        0 * api.sendHubCommand
    }

    def "Installation with modes requires one more subscription"() {
        given:
        def api = Mock(HubitatAppApi)
        def script = sandbox.setupScript(api)

        when:
        script.installed()

        then:
        _ * api.getModes() >> "Modes?"
        _ * api.getPresenceDevices
        23 * api.subscribe(_, _, _)
        1 * api.subscribe(_, _)
    }

    def "When enabled, sends setup command during installation"() {
        given:
        def api = Mock(HubitatAppApi)
        def script = sandbox.setupScript(api)
        def myIp = "123.456.789.123"

        when:
        script.installed()

        then:
        (1.._)*api.enabled >> true
        (1.._)*api.ip >> myIp
        1 * api.sendHubCommand({
            HubAction action ->
                action.action == "test" && action.dni == "${myIp}:39501"
        })
    }
}