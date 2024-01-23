package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.util.device_fixtures.LightSensorFixtureFactory

import spock.lang.Specification

class LightSensorFixtureFactoryTests extends Specification {
    def log = Mock(Log)

    def appExecutor = Mock(AppExecutor) {
        _*getLog() >> log
    }

    void "Light sensor can report illuminance"() {
        given:
        def lightSensorFixture = LightSensorFixtureFactory.create('n')
        lightSensorFixture.initialize(appExecutor, [illuminance: 200])

        expect:
        lightSensorFixture.state.illuminance == 200
    }

    void "Light sensor reports changes in the measurement"() {
        given:
        def lightSensorFixture = LightSensorFixtureFactory.create('n')
        lightSensorFixture.initialize(appExecutor, [illuminance: 200])

        when:
        lightSensorFixture.setIlluminance(300)

        then:
        1*appExecutor.sendEvent(lightSensorFixture, [name: "illuminance", value: 300])
    }
}