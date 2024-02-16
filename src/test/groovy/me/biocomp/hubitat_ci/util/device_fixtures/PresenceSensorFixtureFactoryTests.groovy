package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.util.device_fixtures.PresenceSensorFixtureFactory

import spock.lang.Specification

class PresenceSensorFixtureFactoryTests extends Specification {
    def log = Mock(Log)

    def appExecutor = Mock(AppExecutor) {
        _*getLog() >> log
    }

    void "Presence sensor can report presence"() {
        given:
        def presenceSensorFixture = PresenceSensorFixtureFactory.create('n')
        presenceSensorFixture.initialize(appExecutor, [presence: 'present'])

        expect:
        presenceSensorFixture.currentValue('presence') == 'present'
    }

    void "Presence sensor reports changes in the measurement"() {
        given:
        def presenceSensorFixture = PresenceSensorFixtureFactory.create('n')
        presenceSensorFixture.initialize(appExecutor, [presence: 'not present'])

        when:
        presenceSensorFixture.detectMotion()

        then:
        1*appExecutor.sendEvent(presenceSensorFixture, [name: "presence", value: 'present'])
        presenceSensorFixture.currentValue('presence') == 'present'

        when:
        presenceSensorFixture.noMotion()

        then:
        1*appExecutor.sendEvent(presenceSensorFixture, [name: "presence", value: 'not present'])
        presenceSensorFixture.currentValue('presence') == 'not present'
    }
}