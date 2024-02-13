package me.biocomp.hubitat_ci.integration

import me.biocomp.hubitat_ci.util.integration.IntegrationDeviceSpecification

import spock.lang.Specification

/**
* These are a set of integration tests of a real device script.
* Inside the IntegrationDeviceSpecification, it uses IntegrationDeviceExecutor, IntegrationDeviceWrapper,
* and the device script together, to ensure that the full behavior of the system is correct.
* This is an example of the type of tests we would want to write when developing a device driver.
*/
class EnhancedVirtualPresenceSensorTests extends IntegrationDeviceSpecification {
    @Override
    def setup() {
        super.initializeEnvironment("Scripts/Devices/EnhancedVirtualPresenceSensor.groovy", [], [switch: "off", presence: "not present"])
    }

    def "Can arrive"() {
        when:
            deviceScript.arrived()

        then:
            device.currentValue("switch") == "on"
            device.currentValue("presence") == "present"
    }

    def "Can depart"() {
        when:
            deviceScript.arrived()

        then:
            device.currentValue("switch") == "on"
            device.currentValue("presence") == "present"

        when:
            deviceScript.departed()

        then:
            device.currentValue("switch") == "off"
            device.currentValue("presence") == "not present"
    }

}
