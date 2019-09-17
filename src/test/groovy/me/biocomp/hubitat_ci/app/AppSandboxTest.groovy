package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

/**
 * @note Tests that are applicable to both Apps and Devices should be done in AppAndDeviceSandboxTest
 */
class AppSandboxTest extends Specification {
    def "subscribe() will fail validation for non-existing attribute"() {
        setup:
            final def script = """
preferences {
    page(name:"mainPage", title:"Settings", install: true, uninstall: true) {
        section() {
            input (name:"thermostat", type: "capability.thermostat", title: "Thermostat", required: true, multiple: false)
        }
    }
}

def installed() {
    initialize()
}

def initialize() {
    subscribe(thermostat, "badAttributeName", realCoolingSetpointHandler)
}

def realCoolingSetpointHandler(evt) {}
"""
        when:
            new HubitatAppSandbox(script).run(validationFlags: [Flags.DontValidateDefinition]).installed()

        then:
            AssertionError e = thrown()
            e.message.contains("'Thermostat' does not contain attribute 'badAttributeName'. Valid attributes are: [")
    }
}