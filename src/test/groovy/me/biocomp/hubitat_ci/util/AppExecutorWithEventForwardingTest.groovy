package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.util.AppExecutorWithEventForwarding
import me.biocomp.hubitat_ci.util.DeviceEventArgs
import me.biocomp.hubitat_ci.app.HubitatAppScript
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper

import spock.lang.Specification

/**
* We have to extend HubitatAppScript so that Spock Mock will know
* about the levelHandler method that is called on it.
*/
abstract class LevelHandlingAppScript extends HubitatAppScript {
    abstract void levelHandler(DeviceEventArgs args)
}

class AppExecutorWithEventForwardingTest extends Specification {
    def appExecutor = Spy(AppExecutorWithEventForwarding)

    def appScript = Mock(LevelHandlingAppScript)

    def setup() {
        appExecutor.setSubscribingScript(appScript)
    }

    def "AppExecutorWithEventForwarding will forward a message"() {
        given:
        def device = Mock(DeviceWrapper) {
            _*getIdAsLong() >> 1
        }
        appExecutor.subscribe(device, 'level', 'levelHandler')

        when:
        appExecutor.sendEvent(device, [name: 'level', value: 50])

        then:
        1 * appScript.levelHandler(_) >> { DeviceEventArgs event ->
            assert event.deviceId == 1
            assert event.device == device
            assert event.name == 'level'
            assert event.value == 50
        }
    }

    def "DeviceEventArgs are for the correct device"() {
        given:
        def device1 = Mock(DeviceWrapper) {
            _*getIdAsLong() >> 1
        }
        def device2 = Mock(DeviceWrapper) {
            _*getIdAsLong() >> 2
        }
        appExecutor.subscribe(device1, 'level', 'levelHandler')
        appExecutor.subscribe(device2, 'level', 'levelHandler')

        when:
        appExecutor.sendEvent(device2, [name: 'level', value: 55])

        then:
        1 * appScript.levelHandler(_) >> { DeviceEventArgs event ->
            assert event.deviceId == 2
            assert event.device == device2
            assert event.name == 'level'
            assert event.value == 55
        }
    }

    def "DeviceEventArgs are for the correct device, when subscribed in a list"() {
        given:
        def device1 = Mock(DeviceWrapper) {
            _*getIdAsLong() >> 1
        }
        def device2 = Mock(DeviceWrapper) {
            _*getIdAsLong() >> 2
        }
        appExecutor.subscribe([device1, device2], 'level', 'levelHandler')

        when:
        appExecutor.sendEvent(device2, [name: 'level', value: 55])

        then:
        1 * appScript.levelHandler(_) >> { DeviceEventArgs event ->
            assert event.deviceId == 2
            assert event.device == device2
            assert event.name == 'level'
            assert event.value == 55
        }
    }

    def "AppExecutorWithEventForwarding ignores unsubscribed events"() {
        given:
        def device = Mock(DeviceWrapper) {
            _*getIdAsLong() >> 1
        }
        appExecutor.subscribe(device, 'level', 'levelHandler')

        when:
        appExecutor.sendEvent(device, [name: 'switch', value: 'on'])

        then:
        0 * appScript.levelHandler(_)
    }
}