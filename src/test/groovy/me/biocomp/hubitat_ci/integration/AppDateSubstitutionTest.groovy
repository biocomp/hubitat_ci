package me.biocomp.hubitat_ci.integration

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.util.AppExecutorWithEventForwarding
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.AppValidator
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.util.device_fixtures.DimmerFixtureFactory
import me.biocomp.hubitat_ci.util.TimeKeeper
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

/**
* A set of tests to verify that the SandBoxClassLoader replaces references to Date()
* with TimeKeeperDate() when parsing the app script.
*/
class AppDateSubstitutionTest extends Specification {
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/DimmerMinimums.groovy"))
    def log = Mock(Log)

    def appExecutor = Spy(AppExecutorWithEventForwarding) {
        _*getLog() >> log
    }

    void "If TimeKeeper is not installed, then the app script returns current time"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')

        // Run the app sandbox, passing the dimmer fixture in.
        def appScript = sandbox.run(api: appExecutor,
            userSettingValues: [dimmers: [dimmerFixture], minimumLevel: 5, enableLogging: true])
        appExecutor.setSubscribingScript(appScript)

        when:
        def now = new Date()
        def nowAccordingToTheAppScript = appScript.scriptNow()
        def differenceInMillis = nowAccordingToTheAppScript.getTime() - now.getTime()

        then: "Since we're generating Dates twice in succession, there can be a slight time difference, but we don't want to allow much."
        differenceInMillis < 1000
    }

    void "If TimeKeeper is installed, then the app script returns simulated time"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')

        // Run the app sandbox, passing the dimmer fixture in.
        def appScript = sandbox.run(api: appExecutor,
            userSettingValues: [dimmers: [dimmerFixture], minimumLevel: 5, enableLogging: true])
        appExecutor.setSubscribingScript(appScript)

        when:
        def now = new Date()
        def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
        timekeeper.install()

        and:
        def nowAccordingToTheAppScript = appScript.scriptNow()

        then:
        nowAccordingToTheAppScript.toString() != now.toString()

        cleanup:
        timekeeper.uninstall()
    }

}