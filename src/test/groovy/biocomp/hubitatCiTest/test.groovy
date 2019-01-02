package biocomp.hubitatCiTest

import spock.lang.Specification

class MyTestCase extends Specification {
    def sandbox = new HubitatAppSandbox("Send_Hub_Events.src/Send_Hub_Events.groovy")

    def "Script sets mandatory properties"() {
       expect:
       sandbox.mandatoryConfigIsSet()
   }

    def "Installation succeeds"() {
        given:
        def api = Mock(emulation.SmartAppApi)
        def script = sandbox.setupScript(api)

        when:
        script.installed()

        then:
        _ * api.getPresenceDevices
        23 * api.subscribe(*_)
        0 * api.sendHubCommand
    }

    def "Uninstallation succeeds"() {
        given:
        def
        def api = Mock(emulation.SmartAppApi)
        def script = sandbox.setupScript(api)

        when:
        script.uninstalled()

        then:
        _ * api.getChildDevices
        23 * api.subscribe(*_)
        0 * api.sendHubCommand
    }

    def "Installation with modes requires one more subscription"() {
        given:
        def api = Mock(emulation.SmartAppApi)
        def script = sandbox.setupScript(api)

        when:
        script.installed()

        then:
        _ * api.getModes() >> "Modes?"
        _ * api.getPresenceDevices
        23 * api.subscribe(_, _, _)
        1 * api.subscribe(_, _)
    }

    def "When enabled, sends setup command during installation with all devices"() {
        given:
        def api = Mock(emulation.SmartAppApi)
        def script = sandbox.setupScript(api)
        def myIp = "123.456.789.123"

        when:
        script.installed()

        then:
        (1.._)*api.enabled >> true
        (1.._)*api.ip >> myIp
        1 * api.sendHubCommand({
            emulation.HubAction action ->
                action.action.startsWith("""POST / HTTP/1.1
HOST: ${myIp}:39501
CONTENT-TYPE: text/plain
DEVICE-NETWORK-ID: systemHubLink
CONTENT-LENGTH: 0""") && action.dni == "${myIp}:39501"
        })
    }
}