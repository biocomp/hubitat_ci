package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class WeatherDisplayScriptTest extends
        Specification
{
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("Scripts/Devices/Weather-Display With External Forecast.groovy"))

    def "Basic validation"() {
        setup:
            def log = Mock(Log)
            DeviceExecutor api = Mock{ _ * getLog() >> log }

        expect:
            sandbox.run(api: api, validationFlags: [Flags.AllowSectionsInDevicePreferences, Flags.AllowWritingToSettings])
    }
}

class Fibaro223SciptTest extends Specification
{
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("SubmodulesWithScripts/Hubitat/Drivers/fibaro-double-switch-2-fgs-223.src/fibaro-double-switch-2-fgs-223.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run(validationFlags: [Flags.AllowMissingDeviceInputNameOrType, Flags.AllowCommandDefinitionWithNoArgsMatchAnyCommandWithSameName])
    }
}