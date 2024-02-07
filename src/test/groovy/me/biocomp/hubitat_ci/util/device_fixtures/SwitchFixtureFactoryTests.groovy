package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.util.device_fixtures.SwitchFixtureFactory

import spock.lang.Specification

class SwitchFixtureFactoryTests extends Specification {
    def log = Mock(Log)

    def appExecutor = Mock(AppExecutor) {
        _*getLog() >> log
    }

    void "Switch can turn on"() {
        given:
        def switchFixture = SwitchFixtureFactory.create('n')
        switchFixture.initialize(appExecutor, [switch: "off", level: 50])

        when:
        switchFixture.on()

        then:
        1*appExecutor.sendEvent(switchFixture, [name: "switch.on", value: "on"])
        switchFixture.currentValue('switch') == "on"
        switchFixture.currentValue('doubleTapped') == null
    }

    void "Switch can turn off"() {
        given:
        def switchFixture = SwitchFixtureFactory.create('n')
        switchFixture.initialize(appExecutor, [switch: "on", level: 50])

        when:
        switchFixture.off()

        then:
        1*appExecutor.sendEvent(switchFixture, [name: "switch.off", value: "off"])
        switchFixture.currentValue('switch') == "off"
        switchFixture.currentValue('doubleTapped') == null
    }

    void "Switch can double-tap up"() {
        given:
        def switchFixture = SwitchFixtureFactory.create('n')
        switchFixture.initialize(appExecutor, [switch: "off", level: 50])

        when:
        switchFixture.doubleTap(1)

        then:
        1*appExecutor.sendEvent(switchFixture, [name: "doubleTapped.1", value: 1])
        switchFixture.currentValue('doubleTapped') == 1
    }

    void "Switch can double-tap down"() {
        given:
        def switchFixture = SwitchFixtureFactory.create('n')
        switchFixture.initialize(appExecutor, [switch: "off", level: 50])

        when:
        switchFixture.doubleTap(2)

        then:
        1*appExecutor.sendEvent(switchFixture, [name: "doubleTapped.2", value: 2])
        switchFixture.currentValue('doubleTapped') == 2
    }

}