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
* A set of tests to verify that the SandBoxClassLoader replaces references to new Date()
* with TimeKeeper.now() when parsing the app script.
*/
class AppDateSubstitutionTest extends Specification {
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("Scripts/DimmerMinimums.groovy"))
    def log = Mock(Log)

    def appExecutor = Spy(AppExecutorWithEventForwarding) {
        _*getLog() >> log
    }

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))

        TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
        TimeKeeper.removeAllListeners()
    }

    void "App script returns simulated time"() {
        given:
        def dimmerFixture = DimmerFixtureFactory.create('n')

        // Run the app sandbox, passing the dimmer fixture in.
        def appScript = sandbox.run(api: appExecutor,
            userSettingValues: [dimmers: [dimmerFixture], minimumLevel: 5, enableLogging: true])
        appExecutor.setSubscribingScript(appScript)

        and:
        def timekeeperNow = TimeKeeper.now()
        def now = new Date()

        when:
        def nowAccordingToTheAppScript = appScript.scriptNow()

        then:
        nowAccordingToTheAppScript.toString() != now.toString()
        nowAccordingToTheAppScript.toString() == timekeeperNow.toString()
    }

}