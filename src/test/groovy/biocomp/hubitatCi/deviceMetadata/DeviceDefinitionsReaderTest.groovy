package biocomp.hubitatCi.deviceMetadata

import biocomp.hubitatCi.HubitatDeviceSandbox
import biocomp.hubitatCi.capabilities.Capabilities
import biocomp.hubitatCi.validation.Flags
import spock.lang.Specification
import spock.lang.Unroll

class DeviceDefinitionsReaderTest extends Specification{
    private static Definition readDefinition(Map options = [:], String definitionCall)
    {
        return new HubitatDeviceSandbox("""
metadata{
        ${definitionCall}
}
""").run(options).getProducedDefinition()
    }

    private static Attribute readSingleAttribute(String attributeCall)
    {
        def attributes = readDefinition("""definition(name: "n", namespace: "nm", author: "a"){
    ${attributeCall}
}""", validationFlags: [Flags.DontValidateCapabilities]).attributes

        assert attributes.size() == 1
        return attributes[0]
    }

    def "Reading all valid definition() options"()
    {
        setup:
            def definition = readDefinition("""
    definition(name: "test device", namespace: "yournamespace", author: "your name"){
    }""", validationFlags: [Flags.DontValidateCapabilities])

        expect:
            definition.options.name == 'test device'
            definition.options.namespace == 'yournamespace'
            definition.options.author == 'your name'
    }

    def "definition() with invalid option fails"()
    {
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
    def "Valid capability '#capability' in definition() can be read"(String capability)
    {
        when:
            def capabilitiesResult = readDefinition("""
    definition(name: "nam"){
        capability '${capability}'
    }""").capabilities

        then:
            assert capabilitiesResult == [capability]

        where:
            capability <<
                    Capabilities.capabilitiesByPrettyName.collect{it.key} +
                    Capabilities.capabilitiesByDriverDefinition.collect{it.key}
    }

    def "Invalid capability in definition() fails"()
    {
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

    def "There must be at least one capability"()
    {
        when:
            readDefinition("""
    definition(name: "nam"){
    }""")

        then:
            AssertionError error = thrown()
            error.message.contains('at least one capability')
    }

    @Unroll
    def "attribute() with valid type = '#type' can be read"(String type)
    {
        when:
            def attribute = readSingleAttribute("attribute 'nam', '${type}'")

        then:
            attribute.name == 'nam'
            attribute.type == type
            attribute.possibleValues == null

        where:
            type << ['string', 'number']
    }

    def "attribute() with invalid type fails"()
    {
        when:
            readSingleAttribute("attribute 'nam', 'blah'")

        then:
            AssertionError error = thrown()
            error.message.contains('nam')
            error.message.contains('blah')
            error.message.contains('type')
            error.message.contains('not supported')
    }

    def "attribute() with empty type fails"()
    {
        when:
            readSingleAttribute("attribute 'nam', ''")

        then:
            AssertionError error = thrown()
            error.message.contains('nam')
            error.message.contains("doesn't have a type")
    }

    def "attribute() with empty name fails"()
    {
        when:
            readSingleAttribute("attribute '', 'number'")

        then:
            AssertionError error = thrown()
            error.message.contains("doesn't have a name")
    }

    def "attribute() with non-enum type can't have 'possibleValues'"()
    {
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
    def "attribute() with 'enum' type fails if doesn't have 'possibleValues' (possibleValues = '#possibleValues')"(String possibleValues)
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

    def "Can't have duplicate capabilities"()
    {
        when:
            readDefinition("""definition(name: "n", namespace: "nm", author: "a"){
                    capability 'Valve'
                    capability 'Valve'
                }""")

        then:
            AssertionError error = thrown()
            error.message.contains('Valve')
            error.message.contains('duplicate')
            error.message.contains("capability")
    }

    def "Can't have duplicate attributes"()
    {
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
}
