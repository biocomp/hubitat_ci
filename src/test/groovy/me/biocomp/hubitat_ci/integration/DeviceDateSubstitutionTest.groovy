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
* A set of tests to verify that the SandBoxClassLoader replaces references to "new Date()"
* with TimeKeeper.now() when parsing the device script.
*/
class DeviceDateSubstitutionTest extends Specification {
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("Scripts/Devices/device_with_time_driver.groovy"))
    def log = Mock(Log)

    def deviceExecutor = Spy(DeviceExecutor) {
        _*getLog() >> log
    }

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))

        TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
    }

    void "Device script returns simulated time"() {
        given:
        // Run the app sandbox, passing the dimmer fixture in.
        def deviceScript = sandbox.run(api: deviceExecutor,
            userSettingValues: [:])

        and:
        def timekeeperNow = TimeKeeper.now()
        def now = new Date()

        when:
        def nowAccordingToTheDeviceScript = deviceScript.scriptNow()

        then:
        nowAccordingToTheDeviceScript.toString() != now.toString()
        nowAccordingToTheDeviceScript.toString() == timekeeperNow.toString()
    }

}