package me.biocomp.hubitat_ci.device

import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class RestOfDeviceScriptTests extends Specification
{
    def "parse() method is required in device"()
    {
        when:
            new HubitatDeviceSandbox("""
metadata
{
    definition(name: "n", namespace: "nm", author: "a"){}
}
""").run(validationFlags: [Flags.DontValidateCapabilities])

        then:
            AssertionError e = thrown()
            e.message.contains("Script does not have parse() method required for devices")
    }

    def "missing metadata() has clear error message"()
    {
        when:
            new HubitatDeviceSandbox("""
""").run(validationFlags: [Flags.DontRequireParseMethodInDevice])

        then:
            AssertionError e = thrown()
            e.message.contains("Device does not have 'metadata' call")
    }
}

