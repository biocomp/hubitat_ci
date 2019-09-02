package me.biocomp.hubitat_ci.device

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification
import spock.lang.Unroll

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
            method                                     || expectedError
            ""                                         || "Script does not have parse() method required for devices"
            "parse() {}"                               || "Script does not have parse() method required for devices"
            "def parse() {}"                           || errorForSignature("Object parse()")
            "Map parse() { null }"                     || errorForSignature("Map parse()")
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
}

