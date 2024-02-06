package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.util.device_fixtures.FanFixtureFactory

import spock.lang.Specification

class FanFixtureFactoryTests extends Specification {
    def log = Mock(Log)

    def appExecutor = Mock(AppExecutor) {
        _*getLog() >> log
    }

    void "Fan can turn on"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "off", level: 0, speed: "off"])

        when:
        fanFixture.on()

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.on", value: "on"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "high"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 100])

        and:
        fanFixture.state.switch == "on"
        fanFixture.state.speed == "high"
        fanFixture.state.level == 100
    }

    void "Fan can turn off"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "on", level: 100, speed: "high"])

        when:
        fanFixture.off()

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.off", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 0])

        and:
        fanFixture.state.switch == "off"
        fanFixture.state.speed == "off"
        fanFixture.state.level == 0
    }

    void "Fan can set speed from off"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "off", level: 0, speed: "off"])

        when:
        fanFixture.setSpeed("medium")

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.on", value: "on"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "medium"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 50])

        and:
        fanFixture.state.switch == "on"
        fanFixture.state.speed == "medium"
        fanFixture.state.level == 50
    }

    void "Fan can change speed when already on"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "on", level: 50, speed: "medium"])

        when:
        fanFixture.setSpeed("high")

        then:
        0*appExecutor.sendEvent(fanFixture, [name: "switch.on", value: "on"])   // Was already on.  No new event.
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "high"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 100])

        and:
        fanFixture.state.switch == "on"
        fanFixture.state.speed == "high"
        fanFixture.state.level == 100
    }

    void "Fan can set level from off"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "off", level: 0, speed: "off"])

        when:
        fanFixture.setLevel(50)

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.on", value: "on"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "medium"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 50])

        and:
        fanFixture.state.switch == "on"
        fanFixture.state.speed == "medium"
        fanFixture.state.level == 50
    }

    void "Fan can change level when already on"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "on", level: 50, speed: "medium"])

        when:
        fanFixture.setLevel(100)

        then:
        0*appExecutor.sendEvent(fanFixture, [name: "switch.on", value: "on"])   // Was already on.  No new event.
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "high"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 100])

        and:
        fanFixture.state.switch == "on"
        fanFixture.state.speed == "high"
        fanFixture.state.level == 100
    }

    void "Setting level to 0 will turn fan off"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "on", level: 100, speed: "high"])

        when:
        fanFixture.setLevel(0)

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.off", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 0])

        and:
        fanFixture.state.switch == "off"
        fanFixture.state.speed == "off"
        fanFixture.state.level == 0
    }

    void "Setting speed to off will turn fan off"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "on", level: 100, speed: "high"])

        when:
        fanFixture.setSpeed("off")

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.off", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 0])

        and:
        fanFixture.state.switch == "off"
        fanFixture.state.speed == "off"
        fanFixture.state.level == 0
    }

    void "Can cycle speed from off"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "off", level: 0, speed: "off"])

        when:
        fanFixture.cycleSpeed()

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.on", value: "on"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "low"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 16])

        and:
        fanFixture.state.switch == "on"
        fanFixture.state.speed == "low"
        fanFixture.state.level == 16
    }

    void "Can cycle speed from medium"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "on", level: 50, speed: "medium"])

        when:
        fanFixture.cycleSpeed()

        then:
        0*appExecutor.sendEvent(fanFixture, [name: "switch.on", value: "on"])   // Was already on.  No new event.
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "medium-high"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 75])

        and:
        fanFixture.state.switch == "on"
        fanFixture.state.speed == "medium-high"
        fanFixture.state.level == 75
    }

    void "Can cycle speed from high"() {
        given:
        def fanFixture = FanFixtureFactory.create('n')
        fanFixture.initialize(appExecutor, [switch: "on", level: 100, speed: "high"])

        when:
        fanFixture.cycleSpeed()

        then:
        1*appExecutor.sendEvent(fanFixture, [name: "switch.off", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "speed", value: "off"])
        1*appExecutor.sendEvent(fanFixture, [name: "level", value: 0])

        and:
        fanFixture.state.switch == "off"
        fanFixture.state.speed == "off"
        fanFixture.state.level == 0
    }
}