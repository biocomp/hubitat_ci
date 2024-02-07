package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.util.device_fixtures.WindowShadeFixtureFactory

import spock.lang.Specification

class WindowShadeFixtureFactoryTests extends Specification {
    def log = Mock(Log)

    def appExecutor = Mock(AppExecutor) {
        _*getLog() >> log
    }

    void "Shade can close"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "partially open", position: 50])

        when:
        fixture.close()

        then:
        1*appExecutor.sendEvent(fixture, [name: "position", value: 0])
        1*appExecutor.sendEvent(fixture, [name: "windowShade", value: "closed"])
        fixture.currentValue('position') == 0
        fixture.currentValue('windowShade') == "closed"
    }

    void "Shade can open"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "partially open", position: 50])

        when:
        fixture.open()

        then:
        1*appExecutor.sendEvent(fixture, [name: "position", value: 100])
        1*appExecutor.sendEvent(fixture, [name: "windowShade", value: "open"])
        fixture.currentValue('position') == 100
        fixture.currentValue('windowShade') == "open"
    }

    void "Shade can set position to closed"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "partially open", position: 50])

        when:
        fixture.setPosition(0)

        then:
        1*appExecutor.sendEvent(fixture, [name: "position", value: 0])
        1*appExecutor.sendEvent(fixture, [name: "windowShade", value: "closed"])
        fixture.currentValue('position') == 0
        fixture.currentValue('windowShade') == "closed"
    }

    void "Shade can set position to open"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "partially open", position: 50])

        when:
        fixture.setPosition(100)

        then:
        1*appExecutor.sendEvent(fixture, [name: "position", value: 100])
        1*appExecutor.sendEvent(fixture, [name: "windowShade", value: "open"])
        fixture.currentValue('position') == 100
        fixture.currentValue('windowShade') == "open"
    }

    void "Shade can set position to partially open"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "open", position: 100])

        when:
        fixture.setPosition(50)

        then:
        1*appExecutor.sendEvent(fixture, [name: "position", value: 50])
        1*appExecutor.sendEvent(fixture, [name: "windowShade", value: "partially open"])
        fixture.currentValue('position') == 50
        fixture.currentValue('windowShade') == "partially open"
    }

    void "Shade can start changing to open"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "closed", position: 0])

        when:
        fixture.startPositionChange("open")

        then:
        1*appExecutor.sendEvent(fixture, [name: "position", value: 100])
        1*appExecutor.sendEvent(fixture, [name: "windowShade", value: "open"])
        fixture.currentValue('position') == 100
        fixture.currentValue('windowShade') == "open"
    }

    void "Shade can start changing to closed"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "open", position: 100])

        when:
        fixture.startPositionChange("close")

        then:
        1*appExecutor.sendEvent(fixture, [name: "position", value: 0])
        1*appExecutor.sendEvent(fixture, [name: "windowShade", value: "closed"])
        fixture.currentValue('position') == 0
        fixture.currentValue('windowShade') == "closed"
    }

    void "If given an invalid direction, shade does nothing"() {
        given:
        def fixture = WindowShadeFixtureFactory.create('n')
        fixture.initialize(appExecutor, [windowShade: "partially open", position: 50])

        when:
        fixture.startPositionChange("invalid")

        then:
        0*appExecutor.sendEvent(fixture, _)
        fixture.currentValue('position') == 50
        fixture.currentValue('windowShade') == "partially open"
    }
}