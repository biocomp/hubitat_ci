package me.biocomp.hubitat_ci.device

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.api.Device
import me.biocomp.hubitat_ci.api.common_api.InterfaceHelper
import me.biocomp.hubitat_ci.api.common_api.Mqtt
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification
import spock.lang.Unroll

import java.lang.reflect.Method

class RestOfDeviceScriptTests extends
        Specification
{
    @CompileStatic
    private static String errorForSignature(String signatures) {
        return "None of parse() method signatures ([${signatures}]) matches any of expected ones: [Map parse(String), List<Map> parse(String), HubAction parse(String), List<HubAction> parse(String)"
    }

    @Unroll
    def "Bad parse() method: '#method' in device: #expectedError"(
            String method, String expectedError)
    {
        when:
            new HubitatDeviceSandbox(method).run(validationFlags: [Flags.DontValidateMetadata])

        then:
            AssertionError e = thrown()
            e.message.contains(expectedError)

            /*
                Map parse(String description)
                List<Map> parse(String description)
                HubAction parse(String description)
                List<HubAction> parse(String description)
             */

        where:
            method                 || expectedError
            ""                     || "Script does not have parse() method required for devices"
            "parse() {}"           || "Script does not have parse() method required for devices"
            "def parse() {}"       || errorForSignature("Object parse()")
            "Map parse() { null }" || errorForSignature("Map parse()")
            // TODO: maybe validate return type too.
            // "String parse(String s) { null }"          || errorForSignature("String parse(String s)")
            // "Map<String> parse(String s) { null }"     || errorForSignature("Map<String> parse(String s)")
            // "HashMap<String> parse(String s) { null }" || errorForSignature("HashMap<String> parse(String s)")
    }

    @Unroll
    def "Good parse() method: '#method' in device, will be accepted"(String method) {
        expect:
            new HubitatDeviceSandbox(method).run(validationFlags: [Flags.DontValidateMetadata])

        where:
            method << ["Map parse(String s) { null }",
                       "Map parse(def s) { null }",
                       "Map parse(def s) { return [:] }",
                       "def parse(def s) { return [:] }",
                       "List<Map> parse(def s) { return [] }",
                       "def parse(def s) { new ArrayList<Map>() }",
                       "hubitat.device.HubAction parse(def s) { null }",
                       "def parse(def s) { new hubitat.device.HubAction() }",
                       "List<hubitat.device.HubAction> parse(def s) { return [] }",
                       "def parse(def s) { return new ArrayList<hubitat.device.HubAction>() }"]
    }


    def "missing metadata() has clear error message"() {
        when:
            new HubitatDeviceSandbox("""
""").run(validationFlags: [Flags.DontRequireParseMethodInDevice])

        then:
            AssertionError e = thrown()
            e.message.contains("Device does not have 'metadata' call")
    }

    def "Use device helper to get to Mqtt"() {
        given:
            final def mqtt = Mock(Mqtt)
            final def interfaces = Mock(InterfaceHelper) {
                _ * getMqtt() >> mqtt
            }

            final def api = Mock(DeviceExecutor) {
                _ * getInterfaces() >> interfaces
            }

            final def script = new HubitatDeviceSandbox("""
def useMqtt()
{
    interfaces.mqtt.publish("a", "b")
}
""").run(api: api, validationFlags: [Flags.DontRequireParseMethodInDevice, Flags.DontValidateMetadata])

        when:
            script.useMqtt()

        then:
            1 * mqtt.publish("a", "b")
    }

    def "helper_driver.groovy dumps class correctly"() {
        when:
            final def script = new HubitatDeviceSandbox(new File("Scripts/Devices/helper_driver.groovy")).run()

        then:
            script.dumpClassImpl(TestToDump.class) == """class ${TestToDump.class.name}:
Methods:[
  public java.lang.String me.biocomp.hubitat_ci.device.TestToDump.bar(java.lang.String,java.lang.String)
  public int me.biocomp.hubitat_ci.device.TestToDump.getFoo()
  public void me.biocomp.hubitat_ci.device.TestToDump.setFoo(int)
]"""

            script.dumpClassImpl(EnumToDump.class) == """class ${EnumToDump.class.name}:
Enum values:[
  One,
  Two
]"""
            script.dumpClassImpl(DeviceExecutor.class) != ""
    }
}

class TestToDump
{
    int foo
    String bar(String a, String b) {}
}

enum EnumToDump
{
    One,
    Two
}