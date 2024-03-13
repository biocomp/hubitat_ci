package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.util.device_fixtures.DimmerFixtureFactory

import spock.lang.Specification

class DimmerFixtureFactoryTests extends Specification {
    def log = Mock(Log)

    def appExecutor = Mock(AppExecutor) {
        _*getLog() >> log
    }

    void "Dimmer can turn on"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')
        dimmerFixture.initialize(appExecutor, [switch: "off", level: 50])

        when:
        dimmerFixture.on()

        then:
        1*appExecutor.sendEvent(dimmerFixture, [name: "switch.on", value: "on"])
        dimmerFixture.currentValue('switch') == "on"
        dimmerFixture.currentValue('doubleTapped') == null
    }

    void "Dimmer can turn off"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')
        dimmerFixture.initialize(appExecutor, [switch: "on", level: 50])

        when:
        dimmerFixture.off()

        then:
        1*appExecutor.sendEvent(dimmerFixture, [name: "switch.off", value: "off"])
        dimmerFixture.currentValue('switch') == "off"
        dimmerFixture.currentValue('doubleTapped') == null
    }

    void "Dimmer can set level"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')
        dimmerFixture.initialize(appExecutor, [switch: "on", level: 50])

        when:
        dimmerFixture.setLevel(100)

        then:
        1*appExecutor.sendEvent(dimmerFixture, [name: "level", value: 100])
        dimmerFixture.currentValue('level') == 100
        dimmerFixture.currentValue('doubleTapped') == null
    }

    void "Non-zero setLevel will turn a dimmer on"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')
        dimmerFixture.initialize(appExecutor, [switch: "off", level: 50])

        when:
        dimmerFixture.setLevel(100)

        then:
        1*appExecutor.sendEvent(dimmerFixture, [name: "level", value: 100])
        1*appExecutor.sendEvent(dimmerFixture, [name: "switch.on", value: "on"])
        dimmerFixture.currentValue('switch') == "on"
        dimmerFixture.currentValue('level') == 100
        dimmerFixture.currentValue('doubleTapped') == null
    }

    void "Dimmer can double-tap up"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')
        dimmerFixture.initialize(appExecutor, [switch: "off", level: 50])

        when:
        dimmerFixture.doubleTap(1)

        then:
        1*appExecutor.sendEvent(dimmerFixture, [name: "doubleTapped.1", value: 1])
        dimmerFixture.currentValue('doubleTapped') == 1
    }

    void "Dimmer can double-tap down"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')
        dimmerFixture.initialize(appExecutor, [switch: "off", level: 50])

        when:
        dimmerFixture.doubleTap(2)

        then:
        1*appExecutor.sendEvent(dimmerFixture, [name: "doubleTapped.2", value: 2])
        dimmerFixture.currentValue('doubleTapped') == 2
    }

}