package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.util.SubscribingAppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.AppValidator
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.util.virtual_device_factories.VirtualDimmerFactory
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class EventSubscriptionsTest extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/ScriptWithSubscriptions.groovy"))

    def log = Mock(Log)

    def api = Spy(SubscribingAppExecutor) {
        _*getLog() >> log
    }

    def dimmerFactory = new VirtualDimmerFactory()

    def "Basic validation"() {
        expect:
        sandbox.run()
    }

    void "initialize() subscribes to events"() {
        given:
        def dimmerDevice = dimmerFactory.constructDevice('n')

        // Run the app sandbox, passing the virtual dimmer device in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice], minimumLevel: 5, enableLogging: true])
        api.setSubscribingScript(script)

        when:
        // Run initialize() method on app script.
        script.initialize()

        then:
        // Expect that events are subscribe to
        1 * api.subscribe([dimmerDevice], 'level', 'levelHandler')
        1 * api.subscribe([dimmerDevice], 'switch.on', 'switchOnHandler')
    }

    void "levelHandler() ensures minimum level"() {
        given:
        // Define a virtual dimmer device
        def dimmerDevice = dimmerFactory.constructDevice('n')

        // Run the app sandbox, passing the virtual dimmer device in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice, api, script, [switch: "on", level: 99])

        when:
        script.installed()
        dimmerDevice.setLevel(2)

        then:
        dimmerDevice.state.switch == "on"
        dimmerDevice.state.level == 5
    }

    void "setLevel() can turn on the dimmer"() {
        given:
        // Define a virtual dimmer device
        def dimmerDevice = dimmerFactory.constructDevice('n')

        // Run the app sandbox, passing the virtual dimmer device in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice, api, script, [switch: "off", level: 99])

        when:
        script.installed()
        dimmerDevice.setLevel(2)

        then:
        dimmerDevice.state.switch == "on"
        dimmerDevice.state.level == 5
    }

    void "setLevel() does not turn on dimmer if zero"() {
        given:
        // Define a virtual dimmer device
        def dimmerDevice = dimmerFactory.constructDevice('n')

        // Run the app sandbox, passing the virtual dimmer device in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice, api, script, [switch: "off", level: 99])

        when:
        script.installed()
        dimmerDevice.setLevel(0)

        then:
        dimmerDevice.state.switch == "off"
        dimmerDevice.state.level == 0
    }

    void "levelHandler() does not change level if above the minimum"() {
        given:
        // Define a virtual dimmer device
        def dimmerDevice = dimmerFactory.constructDevice('n')

        // Run the app sandbox, passing the virtual dimmer device in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice, api, script, [switch: "on", level: 99])

        when:
        script.installed()
        dimmerDevice.setLevel(80)

        then:
        dimmerDevice.state.switch == "on"
        dimmerDevice.state.level == 80
    }

    void "levelHandler() adjusts correct dimmer from among multiple devices"() {
        given:
        // Define two virtual dimmer devices
        def dimmerDevice1 = dimmerFactory.constructDevice('n1')
        def dimmerDevice2 = dimmerFactory.constructDevice('n2')

        // Run the app sandbox, passing the virtual dimmer devices in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice1, dimmerDevice2], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice1, api, script, [switch: "on", level: 99])
        dimmerFactory.attachBehavior(dimmerDevice2, api, script, [switch: "on", level: 99])

        when:
        script.installed()
        dimmerDevice2.setLevel(2)

        then:
        dimmerDevice2.state.switch == "on"
        dimmerDevice2.state.level == 5
        dimmerDevice1.state.switch == "on"
        dimmerDevice1.state.level == 99
    }

        void "switchOnHandler() ensures minimum level"() {
        given:
        // Define a virtual dimmer device
        def dimmerDevice = dimmerFactory.constructDevice('n')

        // Run the app sandbox, passing the virtual dimmer device in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice, api, script, [switch: "off", level: 0])

        when:
        script.installed()
        dimmerDevice.on()

        then:
        dimmerDevice.state.switch == "on"
        dimmerDevice.state.level == 5
    }

    void "switchOnHandler() does not change level if above the minimum"() {
        given:
        // Define a virtual dimmer device
        def dimmerDevice = dimmerFactory.constructDevice('n')

        // Run the app sandbox, passing the virtual dimmer device in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice, api, script, [switch: "off", level: 99])

        when:
        script.installed()
        dimmerDevice.on()

        then:
        dimmerDevice.state.switch == "on"
        dimmerDevice.state.level == 99
    }

    void "switchOnHandler() adjusts correct dimmer from among multiple devices"() {
        given:
        // Define two virtual dimmer devices
        def dimmerDevice1 = dimmerFactory.constructDevice('n1')
        def dimmerDevice2 = dimmerFactory.constructDevice('n2')

        // Run the app sandbox, passing the virtual dimmer devices in.
        def script = sandbox.run(api: api,
            userSettingValues: [dimmers: [dimmerDevice1, dimmerDevice2], minimumLevel: 5, enableLogging: true],
            )
        api.setSubscribingScript(script)

        dimmerFactory.attachBehavior(dimmerDevice1, api, script, [switch: "off", level: 0])
        dimmerFactory.attachBehavior(dimmerDevice2, api, script, [switch: "off", level: 0])

        when:
        script.installed()
        dimmerDevice2.on()

        then:
        dimmerDevice2.state.switch == "on"
        dimmerDevice2.state.level == 5
        dimmerDevice1.state.switch == "off"
        dimmerDevice1.state.level == 0
    }

}