package me.biocomp.hubitat_ci.integration

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.util.IntegrationAppExecutor
import me.biocomp.hubitat_ci.util.IntegrationScheduler
import me.biocomp.hubitat_ci.util.TimeKeeper
import me.biocomp.hubitat_ci.util.TimeChangedEvent
import me.biocomp.hubitat_ci.util.TimeChangedListener
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.AppValidator
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.app.HubitatAppScript
import me.biocomp.hubitat_ci.util.device_fixtures.DimmerFixtureFactory
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class SchedulerIntegrationTest extends Specification {
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/AppWithSchedules.groovy"))

    Log log = Mock(Log)
    IntegrationScheduler scheduler = new IntegrationScheduler()
    IntegrationAppExecutor appExecutor = Spy(IntegrationAppExecutor, constructorArgs: [scheduler: scheduler]) {
        _*getLog() >> log
    }
    def switchFixture = DimmerFixtureFactory.create('n')
    HubitatAppScript appScript

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
        TimeKeeper.removeAllListeners()
        TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:20:01"))    // This time is chosen to not be on a minute or 5 minute boundary

        switchFixture.initialize(appExecutor, [switch:"off"])

        appScript = sandbox.run(api: appExecutor,
            userSettingValues: [switches: [switchFixture], enableLogging: true])

        appExecutor.setSubscribingScript(appScript)
    }

    def cleanup() {
        TimeKeeper.removeAllListeners()
    }


    def "Basic validation of app script"() {
        expect:
        sandbox.run()
    }

    void "App initialize subscribes to events"() {
        when:
        appScript.initialize()

        then:
        // Expect that events are subscribed to
        1 * appExecutor.subscribe([switchFixture], 'switch.on', 'switchOnHandler')
        1 * appExecutor.subscribe([switchFixture], 'switch.off', 'switchOffHandler')
    }

    void "Scheduling a runInMillis causes the handler to trigger after the correct amount of time."() {
        given:
        appScript.initialize()

        and: "This will trigger a runInMillis callback to be scheduled for 50 milliseconds from now."
        switchFixture.on()

        when:
        TimeKeeper.advanceMillis(51)

        then:
        1 * log.debug("runInMillisHandler() called")
    }

    void "Scheduling a runInMillis does not cause the handler to trigger before the correct amount of time."() {
        given:
        appScript.initialize()

        and: "This will trigger a runInMillis callback to be scheduled for 50 milliseconds from now."
        switchFixture.on()

        when:
        TimeKeeper.advanceMillis(49)

        then:
        0 * log.debug("runInMillisHandler() called")
    }

    void "Scheduling a runInMillis only fires once"() {
        given:
        appScript.initialize()

        and: "This will trigger a runInMillis callback to be scheduled for 50 milliseconds from now."
        switchFixture.on()

        when:
        TimeKeeper.advanceMillis(51)

        then:
        1 * log.debug("runInMillisHandler() called")

        when: "We advance time again."
        TimeKeeper.advanceMillis(51)

        then:
        0 * log.debug("runInMillisHandler() called")
    }

    void "Recurring scheduled handlers will be called after the correct amount of time."() {
        given:
        appScript.initialize()

        and: "This will trigger a recurring callback to be scheduled for every 5 minutes."
        switchFixture.off()

        when:
        TimeKeeper.advanceMinutes(5)

        then:
        1 * log.debug("runEvery5MinutesHandler() called")
    }

    void "Recurring scheduled handlers will not be called before the correct amount of time."() {
        given:
        appScript.initialize()

        and: "This will trigger a recurring callback to be scheduled for every 5 minutes."
        switchFixture.off()

        when:
        TimeKeeper.advanceMinutes(4)

        then:
        0 * log.debug("runEvery5MinutesHandler() called")
    }

    void "Recurring scheduled handlers will be called multiple times."() {
        given:
        appScript.initialize()

        and: "This will trigger a recurring callback to be scheduled for every 5 minutes."
        switchFixture.off()

        when:
        TimeKeeper.advanceMinutes(5)

        then:
        1 * log.debug("runEvery5MinutesHandler() called")

        when: "We advance time again."
        TimeKeeper.advanceMinutes(5)

        then:
        1 * log.debug("runEvery5MinutesHandler() called")
    }

    void "Recurring scheduled handlers will be called multiple times. 2nd way to count."() {
        given:
        appScript.initialize()

        and: "This will trigger a recurring callback to be scheduled for every 5 minutes."
        switchFixture.off()

        when:
        TimeKeeper.advanceMinutes(10)

        then:
        2 * log.debug("runEvery5MinutesHandler() called")
    }

}