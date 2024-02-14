package me.biocomp.hubitat_ci.integration

import me.biocomp.hubitat_ci.util.integration.IntegrationAppSpecification
import me.biocomp.hubitat_ci.util.device_fixtures.DimmerFixtureFactory

import spock.lang.Specification

/**
* These are a set of integration tests of a real app script, testing it for behavior a user
* would experience from their real devices.
* Inside the IntegrationAppSpecification, it uses IntegrationAppExecutor, DimmerFixtureFactory,
* and the app script together, to ensure that the full behavior of the system is correct.
* These are the type of tests we would want to write when developing an app.
*/
class DimmerMinimumsIntegrationTest extends IntegrationAppSpecification {
    Object dimmerFixture1
    Object dimmerFixture2

    @Override
    def setup() {
        dimmerFixture1 = DimmerFixtureFactory.create('d1')
        dimmerFixture2 = DimmerFixtureFactory.create('d2')

        super.initializeEnvironment(appScriptFilename: "Scripts/DimmerMinimums.groovy",
                                    userSettingValues: [dimmers: [dimmerFixture1, dimmerFixture2], minimumLevel: 5, enableLogging: true])
    }

    void "App initialize subscribes to events"() {
        when:
        appScript.initialize()

        then:
        // Expect that events are subscribed to
        1 * appExecutor.subscribe([dimmerFixture1, dimmerFixture2], 'level', 'levelHandler')
        1 * appExecutor.subscribe([dimmerFixture1, dimmerFixture2], 'switch.on', 'switchOnHandler')
    }

    void "levelHandler() ensures minimum level"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "on", level: 99])
        appScript.installed()

        when:
        dimmerFixture1.setLevel(2)

        then:
        dimmerFixture1.currentValue('switch') == "on"
        dimmerFixture1.currentValue('level') == 5
    }

    void "setLevel() can turn on the dimmer"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "off", level: 99])
        appScript.installed()

        when:
        dimmerFixture1.setLevel(2)

        then:
        dimmerFixture1.currentValue('switch') == "on"
        dimmerFixture1.currentValue('level') == 5
    }

    void "setLevel() does not turn on dimmer if zero"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "off", level: 99])
        appScript.installed()

        when:
        dimmerFixture1.setLevel(0)

        then:
        dimmerFixture1.currentValue('switch') == "off"
        dimmerFixture1.currentValue('level') == 0
    }

    void "levelHandler() does not change level if above the minimum"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "on", level: 99])
        appScript.installed()

        when:
        dimmerFixture1.setLevel(80)

        then:
        dimmerFixture1.currentValue('switch') == "on"
        dimmerFixture1.currentValue('level') == 80
    }

    void "levelHandler() adjusts correct dimmer from among multiple devices"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "on", level: 99])
        dimmerFixture2.initialize(appExecutor, [switch: "on", level: 99])
        appScript.installed()

        when:
        dimmerFixture2.setLevel(2)

        then:
        dimmerFixture2.currentValue('switch') == "on"
        dimmerFixture2.currentValue('level') == 5
        dimmerFixture1.currentValue('switch') == "on"
        dimmerFixture1.currentValue('level') == 99
    }

    void "switchOnHandler() ensures minimum level"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "off", level: 0])
        appScript.installed()

        when:
        dimmerFixture1.on()

        then:
        dimmerFixture1.currentValue('switch') == "on"
        dimmerFixture1.currentValue('level') == 5
    }

    void "switchOnHandler() does not change level if above the minimum"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "off", level: 99])
        appScript.installed()

        when:
        dimmerFixture1.on()

        then:
        dimmerFixture1.currentValue('switch') == "on"
        dimmerFixture1.currentValue('level') == 99
    }

    void "switchOnHandler() adjusts correct dimmer from among multiple devices"() {
        given:
        dimmerFixture1.initialize(appExecutor, [switch: "off", level: 0])
        dimmerFixture2.initialize(appExecutor, [switch: "off", level: 0])
        appScript.installed()

        when:
        dimmerFixture2.on()

        then:
        dimmerFixture2.currentValue('switch') == "on"
        dimmerFixture2.currentValue('level') == 5
        dimmerFixture1.currentValue('switch') == "off"
        dimmerFixture1.currentValue('level') == 0
    }

}