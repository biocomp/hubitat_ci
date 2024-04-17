package me.biocomp.hubitat_ci.integration

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.util.integration.IntegrationAppExecutor
import me.biocomp.hubitat_ci.util.integration.IntegrationScheduler
import me.biocomp.hubitat_ci.util.integration.TimeKeeper
import me.biocomp.hubitat_ci.util.integration.TimeChangedEvent
import me.biocomp.hubitat_ci.util.integration.TimeChangedListener
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

/**
 * Apps and device drivers sometimes schedule callbacks inside of other callbacks.
 * These tests verify that recursive callbacks are handled correctly by the IntegrationScheduler.
 */
class SchedulerRecursionIntegrationTest extends Specification {
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/AppWithRecursiveSchedules.groovy"))

    Log log = Mock(Log)
    def state = [callbackCount: 0]
    IntegrationScheduler scheduler = new IntegrationScheduler()
    IntegrationAppExecutor appExecutor = Spy(IntegrationAppExecutor, constructorArgs: [scheduler: scheduler]) {
        _*getLog() >> log
        _*getState() >> state
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
    }

    def "Handlers for scheduled jobs can go on to schedule their own jobs, without concurrency errors"() {
        given:
        appScript.initialize()

        and: "This will trigger a runInMillis callback to be scheduled for 50 milliseconds from now."
        switchFixture.on()

        when: "Wait long enough for the runInMillisHandler to be called the first time."
        TimeKeeper.advanceMillis(51)

        then:
        1 * log.debug("runInMillisHandler() called")

        when: "Wait long enough for the runInMillisHandler to be called the second time."
        TimeKeeper.advanceMillis(1001)

        then:
        1 * log.debug("runInMillisHandler() called")

        when: "Wait long enough for the runInMillisHandler to be called the third time."
        TimeKeeper.advanceMillis(1001)

        then:
        1 * log.debug("runInMillisHandler() called")
    }

    def "The recursive handler runs a maximum of 3 times, as per the app script logic"() {
        given:
        appScript.initialize()

        and: "This will trigger a runInMillis callback to be scheduled for 50 milliseconds from now."
        switchFixture.on()

        when: "Wait long enough for the runInMillisHandler to be called the first time."
        TimeKeeper.advanceMillis(51)

        then:
        1 * log.debug("runInMillisHandler() called")

        when: "Wait long enough for the runInMillisHandler to be called the second time."
        TimeKeeper.advanceMillis(1001)

        then:
        1 * log.debug("runInMillisHandler() called")

        when: "Wait long enough for the runInMillisHandler to be called the third time."
        TimeKeeper.advanceMillis(1001)

        then:
        1 * log.debug("runInMillisHandler() called")

        when: "Wait again, but it should not be called a fourth time."
        TimeKeeper.advanceMillis(1001)

        then:
        0 * log.debug("runInMillisHandler() called")
    }
}