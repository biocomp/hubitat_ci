package me.biocomp.hubitat_ci.device.metadata

import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification
import spock.lang.Unroll

class DeviceDefinitionsReaderTest extends
        Specification
{
    private static Definition readDefinition(Map options = [:], String definitionCall) {
        options.validationFlags = options.validationFlags
                ? options.validationFlags + [Flags.DontRequireParseMethodInDevice]
                : [Flags.DontRequireParseMethodInDevice]

        return new HubitatDeviceSandbox("""
metadata{
        ${definitionCall}
}
""").run(options).getProducedDefinition()
    }

    private static Attribute readSingleAttribute(String attributeCall) {
        def attributes = readDefinition("""definition(name: "n", namespace: "nm", author: "a"){
    ${attributeCall}
}""", validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice]).attributes

        assert attributes.size() == 1
        return attributes[0]
    }

    def "Reading all valid definition() options"() {
        setup:
            def definition = readDefinition("""
    definition(name: "test device", namespace: "yournamespace", author: "your name", importUrl: "http://example.com/myscript.groovy"){
    }""", validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice])

        expect:
            definition.options.name == 'test device'
            definition.options.namespace == 'yournamespace'
            definition.options.author == 'your name'
            definition.options.importUrl == 'http://example.com/myscript.groovy'
    }

    @Unroll
    def "definition(#params)'s parameter #missingParam is required"(String params, String missingParam) {
        when:
            def definition = readDefinition("""
    definition(${params}){
    }""", validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice])

        then:
            AssertionError e = thrown()
            e.message.contains("[${missingParam}]' not set")

        where:
            params                        | missingParam
            "name: 'n', namespace: 'n'"   | "author"
            "namespace: 'n', author: 'a'" | "name"
            "name: 'n', author: 'a'"      | "namespace"
    }

    def "definition() with invalid option fails"() {
        when:
            readDefinition("""
    definition(name: "test device", badOption: "i'm so bad", namespace: "yournamespace", author: "your name"){
    }""")

        then:
            AssertionError e = thrown()
            e.message.contains('badOption')
            e.message.contains('not supported')
            e.message.contains('definition')
    }

    @Unroll
    def "Valid capability '#capability' in definition() can be read"(String capability) {
        when:
            def capabilitiesResult = readDefinition(
                    [validationFlags: [Flags.DontRequireCapabilityImplementationMethods, Flags.DontRequireParseMethodInDevice]], """
    definition(name: "nam", namespace: "n", author: "a"){
        capability '${capability}'
    }""").capabilities

        then:
            assert capabilitiesResult == [capability]

        where:
            capability << Capabilities.capabilitiesByPrettyName.collect {
                it.key
            } + Capabilities.capabilitiesByDriverDefinition.collect { it.key }
    }

    def "When capability used, and capability's methods are missing, fails"() {
        when:
            new HubitatDeviceSandbox("""
metadata{
    definition(name: "nam", namespace: "n", author: "a"){
        capability 'Bulb'
    }
}
 
def on()
{
}
""").run(validationFlags: [Flags.DontRequireParseMethodInDevice])

        then:
            AssertionError e = thrown()
            !e.message.contains('on()')
            e.message.contains('capability \'Bulb\' method [off()] not implemented')
            e.message.contains('off()')
    }

    def "Invalid capability in definition() fails"() {
        when:
            def capabilitiesResult = readDefinition("""
    definition(name: "nam"){
        capability 'Im Bad Capability'
    }""").capabilities

        then:
            AssertionError error = thrown()
            error.message.contains('Im Bad Capability')
            error.message.contains('not supported')
            error.message.contains(Capabilities.capabilitiesByPrettyName.keySet().asList()[0])
            error.message.contains(Capabilities.capabilitiesByPrettyName.keySet().asList()[10])
    }

    def "There must be at least one capability"() {
        when:
            readDefinition("""
    definition(name: "nam", author: 'a', namespace: 'n'){
    }""")

        then:
            AssertionError error = thrown()
            error.message.contains('at least one capability')
    }

    @Unroll
    def "attribute() with valid type = '#type' can be read"(String type) {
        when:
            def attribute = readSingleAttribute("attribute 'nam', '${type}'")

        then:
            attribute.name == 'nam'
            attribute.type == type
            attribute.possibleValues == null

        where:
            type << ['string', 'number']
    }

    def "attribute() with invalid type fails"() {
        when:
            readSingleAttribute("attribute 'nam', 'blah'")

        then:
            AssertionError error = thrown()
            error.message.contains('nam')
            error.message.contains('blah')
            error.message.contains('type')
            error.message.contains('not supported')
    }

    def "attribute() with empty type fails"() {
        when:
            readSingleAttribute("attribute 'nam', ''")

        then:
            AssertionError error = thrown()
            error.message.contains('nam')
            error.message.contains("doesn't have a type")
    }

    def "attribute() with empty name fails"() {
        when:
            readSingleAttribute("attribute '', 'number'")

        then:
            AssertionError error = thrown()
            error.message.contains("doesn't have a name")
    }

    def "attribute() with non-enum type can't have 'possibleValues'"() {
        when:
            readSingleAttribute("attribute 'nam', 'number', ['one', 'two']")

        then:
            AssertionError error = thrown()
            error.message.contains('nam')
            error.message.contains('number')
            error.message.contains('enum')
            error.message.contains("have possible values specified")
    }

    @Unroll
    def "attribute() with 'enum' type fails if doesn't have 'possibleValues' (possibleValues = '#possibleValues')"(
            String possibleValues)
    {
        when:
            readSingleAttribute("attribute 'nam', 'enum'${possibleValues}")

        then:
            AssertionError error = thrown()
            error.message.contains('nam')
            error.message.contains('enum')
            error.message.contains("must have possible values specified")

        where:
            possibleValues << [", []", "", ", null"]
    }

    def "Can't have duplicate capabilities"() {
        when:
            readDefinition([validationFlags: [Flags.DontRequireCapabilityImplementationMethods]],"""definition(name: "n", namespace: "nm", author: "a"){
                    capability 'Valve'
                    capability 'Valve'
                }""")

        then:
            AssertionError error = thrown()
            error.message.contains('Valve')
            error.message.contains('duplicate')
            error.message.contains("capabilit")
    }

    def "Can't have duplicate attributes"() {
        when:
            readDefinition("""definition(name: "n", namespace: "nm", author: "a"){
                attribute 'nam', 'number'
                attribute 'nam', 'number'
            }""", validationFlags: [Flags.DontValidateCapabilities])

        then:
            AssertionError error = thrown()
            error.message.contains('nam')
            error.message.contains('duplicate')
            error.message.matches(/.*(?i)attribute.*/)
    }

    @Unroll
    def "Can't have duplicate commands with same args. Testing: '#commands', expected errors: '#expectedErrorParts'."(
            String commands, List<String> expectedErrorParts)
    {
        when:
            readDefinition("""definition(name: "n", namespace: "nm", author: "a"){
                ${commands}
            }""", validationFlags: [Flags.DontValidateCapabilities])

        then:
            AssertionError e = thrown()
            expectedErrorParts.each { e.message.contains(it) }

        where:
            commands                                        | expectedErrorParts
            """command('cmd')
               command('cmd')"""             | ['cmd', 'duplicate']
            """command('cmd', ['number'])
               command('cmd', ['number'])""" | ['cmd', 'duplicate', 'number']
    }

    @Unroll
    def "duplicate commands(): '#commands' with different args are fine"(String commands, List<Object[]> invokeWith) {
        when:
            def script = new HubitatDeviceSandbox("""
metadata
{
    definition(name: "n", namespace: "nm", author: "a"){
        ${commands}
    }
}

def cmd() {}
def cmd(String s) {}
def cmd(String s, Integer i) {}
def cmd(int a) {}
""").run(validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice])
            def producedCommands = script.producedDefinition.commands

        then:
            producedCommands.size() == invokeWith.size()
            invokeWith.eachWithIndex { invokeWithArgs, i -> producedCommands[i].method.invoke(script, *invokeWithArgs)
            }

        where:
            commands                                              | invokeWith
            "command 'cmd';command 'cmd', ['string', 'number']"   | [[], ["aaa", 42]]
            "command 'cmd'"                                       | [[]]
            "command 'cmd', ['string'];command 'cmd', ['number']" | [["aaa"], [42]]
    }

    @Unroll
    def "command() data type (#type) is valid"(String type) {
        when:
            def producedCommands = readDefinition("""definition(name: "n", namespace: "nm", author: "a"){
                command 'name', ['${type}']
            }""", validationFlags: [Flags.DontValidateCapabilities, Flags.AllowMissingCommandMethod]).commands

        then:
            producedCommands.size() == 1
            producedCommands[0].parameterTypes.size() == 1
            producedCommands[0].parameterTypes[0] == type

        where:
            type << ['number', 'string']
    }

    def "command() fails with invalid data type"() {
        when:
            readDefinition("""definition(name: "n", namespace: "nm", author: "a"){
                command 'cmd', ["I'm bad type"]
            }""", validationFlags: [Flags.DontValidateCapabilities, Flags.AllowMissingCommandMethod])

        then:
            AssertionError e = thrown()
            e.message.contains("I'm bad type")
            e.message.contains("not supported")
            e.message.matches(/.*(?i)argument type.*/)
            e.message.contains("string")
            e.message.contains("number")
    }

    @Unroll
    def "If real method corresponding to #command does not exist, fails"(String command, String[] expectedErrorParts) {
        when:
            new HubitatDeviceSandbox("""
metadata
{
    definition(name: "n", namespace: "nm", author: "a"){
        ${command}
    }
}

def cmd1(String s) {}
def cmd2(int i) {}
def cmd3(int a, int b) {}
""").run(validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice])

        then:
            AssertionError e = thrown()
            e.message.contains('match')
            e.message.contains('signature')
            expectedErrorParts.each { e.message.contains(it) }

        where:
            command                                 | expectedErrorParts
            "command('cmd1')"                       | ['cmd1', 'cmd1(String']
            "command('cmd2')"                       | ['cmd2', 'cmd2(int']
            "command('cmd2', ['string'])"           | ['cmd2', 'cmd2(int']
            "command('cmd1', ['number'])"           | ['cmd2', 'cmd2(string']
            "command('cmd3', ['number', 'string'])" | ['cmd3', 'cmd3(int, int']
    }

    @Unroll
    def "Command() can find method corresponding to #command"(
            String command, String expectedMethodOutput, Object[] methodArgs)
    {
        setup:
            def script = new HubitatDeviceSandbox("""
metadata
{
    definition(name: "n", namespace: "nm", author: "a"){
        ${command}
    }
}

def cmd() { return 'just cmd' }
def cmd(String s) { return "cmd with String = \${s}" }
def cmd(int i) { return "cmd with int = \${i}" }
def cmd(int a, int b) { return "cmd with 2 ints = \${a}, \${b}" }
""").run(validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice])
            def foundCommand = script.producedDefinition.commands[0]

        expect:
            foundCommand.method.invoke(script, methodArgs) == expectedMethodOutput

        where:
            command                                | expectedMethodOutput        | methodArgs
            "command('cmd')"                       | 'just cmd'                  | []
            "command('cmd', ['string'])"           | 'cmd with String = str'     | ['str']
            "command('cmd', ['number'])"           | 'cmd with int = 10'         | [10]
            "command('cmd', ['number', 'number'])" | 'cmd with 2 ints = 42, 123' | [42, 123]
    }

    @Unroll
    def "Valid fingerprint(#fingerprintText) is accepted"(String fingerprintText) {
        setup:
            def fingerprints = new HubitatDeviceSandbox("""
metadata
{
    definition(name: "n", namespace: "nm", author: "a"){
        fingerprint(${fingerprintText})
    }
}
""").run(validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice]).producedDefinition.fingerprints

        expect:
            fingerprints.size() == 1

        where:
            fingerprintText << ['mfr: "0086", prod: "0102", model: "0064"',
                                'type: "10", cc: "25,32"',
                                'deviceId:"0x1104", inClusters:"0x26, 0x2B, 0x2C, 0x27, 0x73, 0x70, 0x86, 0x72", outClusters: "0x20"',
                                'type: "1104", cc: "26,2B,2C,27,73,70,86,72", ccOut: "20"',
                                'profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0702"',
                                'profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0702, 0B05", outClusters: "0019", manufacturer: "sengled", model: "Z01-CIA19NAE26", deviceJoinName: "Sengled Element touch"',
                                'profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0702, 0B05", outClusters: "000A, 0019", manufacturer: "Jasco Products", model: "45852", deviceJoinName: "GE Zigbee Plug-In Dimmer"',
                                'profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0702, 0B05", outClusters: "000A, 0019", manufacturer: "Jasco Products", model: "45857", deviceJoinName: "GE Zigbee In-Wall Dimmer"']
    }

    @Unroll
    def "Invalid fingerprint(#fingerprintText) is rejected"(String fingerprintText, String expectedFailedParam) {
        when:
            def fingerprints = new HubitatDeviceSandbox("""
metadata
{
    definition(name: "n", namespace: "nm", author: "a"){
        fingerprint(${fingerprintText})
    }
}
""").run(validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice]).producedDefinition.fingerprints

        then:
            AssertionError e = thrown()
            e.message.contains(expectedFailedParam)
            e.message.contains('type')
            e.message.contains('mfr')
            e.message.contains('prod')
            e.message.contains('model')
            e.message.contains('cc')
            e.message.contains('ccOut')

        where:
            fingerprintText                                                                                                                                                                                           | expectedFailedParam
            'mfgr: "0086", prod: "0102", model: "0064"'                                                                                                                                                               | 'mfgr'
            'TYpe: "10", cc: "25,32"'                                                                                                                                                                                 | 'TYpe'
            'profileId: "0104", inClusters: "0000, 0003, 0004, 0005, 0006, 0008, 0702, 0B05", outClusters: "000A, 0019", manufacturer: "Jasco Products", model: "45857", deviceJoinNamee: "GE Zigbee In-Wall Dimmer"' | 'deviceJoinNamee'
    }

    def "simulator() can be called. Does nothing."() {
        expect:
            new HubitatDeviceSandbox("""
metadata
{
    definition(name: "n", namespace: "nm", author: "a"){
        simulator{ }
    }
}
""").run(validationFlags: [Flags.DontValidateCapabilities, Flags.DontRequireParseMethodInDevice])

    }
}
