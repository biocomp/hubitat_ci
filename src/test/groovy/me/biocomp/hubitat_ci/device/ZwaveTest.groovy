package me.biocomp.hubitat_ci.device

import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses.VersionV1
import me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionGet
import me.biocomp.hubitat_ci.api.device_api.zwave.Zwave
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class ZwaveTest extends Specification {
    def "Smoke test for a few zwave properties"()
    {
        setup:
            VersionGet versionGetMock = Mock{
                _*format() >> "Not sure what's supposed to be returned"
            }

            VersionV1 versionv1 = Mock{
                _*versionGet() >> versionGetMock
            }

            Zwave zwave = Mock{
                _*getVersionV1() >> versionv1
            }

            DeviceExecutor api = Mock{
                _*getZwave() >> zwave
            }

            def script = new HubitatDeviceSandbox("""
def installed() {}

def updated() {
    def cmds = []

    assert zwave != null
    assert zwave.versionV1 != null
    assert zwave.versionV1.versionGet() != null
    assert zwave.versionV1.versionGet().format() == "Not sure what's supposed to be returned"
    
    true
}
""").run(validationFlags: [Flags.DontValidateMetadata, Flags.DontRequireParseMethodInDevice], api: api)

        expect:
            script.updated()
    }
}
