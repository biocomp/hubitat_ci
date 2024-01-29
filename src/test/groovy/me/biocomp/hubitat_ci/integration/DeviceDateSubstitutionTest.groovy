package me.biocomp.hubitat_ci.integration

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.util.TimeKeeper
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

/**
* A set of tests to verify that the SandBoxClassLoader replaces references to Date()
* with TimeKeeperDate() when parsing the device script.
*/
class DeviceDateSubstitutionTest extends Specification {
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("Scripts/Devices/device_with_time_driver.groovy"))
    def log = Mock(Log)

    def deviceExecutor = Spy(DeviceExecutor) {
        _*getLog() >> log
    }

    void "If TimeKeeper is not installed, then the device script returns current time"() {
        given:
        def deviceScript = sandbox.run(api: deviceExecutor,
            userSettingValues: [:])

        when:
        def now = new Date()
        def nowAccordingToTheDeviceScript = deviceScript.scriptNow()
        def differenceInMillis = nowAccordingToTheDeviceScript.getTime() - now.getTime()

        then: "Since we're generating Dates twice in succession, there can be a slight time difference, but we don't want to allow much."
        differenceInMillis < 10
    }

    void "If TimeKeeper is installed, then the device script returns simulated time"() {
        given:
        // Run the app sandbox, passing the dimmer fixture in.
        def deviceScript = sandbox.run(api: deviceExecutor,
            userSettingValues: [:])

        when:
        def now = new Date()
        def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
        timekeeper.install()

        and:
        def nowAccordingToTheDeviceScript = deviceScript.scriptNow()

        then:
        nowAccordingToTheDeviceScript.toString() != now.toString()

        cleanup:
        timekeeper.uninstall()
    }

}