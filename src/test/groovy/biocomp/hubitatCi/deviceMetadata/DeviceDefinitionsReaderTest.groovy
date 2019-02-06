package biocomp.hubitatCi.deviceMetadata

import biocomp.hubitatCi.HubitatDeviceSandbox
import biocomp.hubitatCi.capabilities.Capabilities
import spock.lang.Specification
import spock.lang.Unroll

class DeviceDefinitionsReaderTest extends Specification{
    private static Definition readDefinition(String definitionCall)
    {
        return new HubitatDeviceSandbox("""
metadata{
        ${definitionCall}
}
""").run().getProducedDefinition()
    }

    def "Reading all valid definition() options"()
    {
        setup:
            def definition = readDefinition("""
    definition(name: "test device", namespace: "yournamespace", author: "your name"){
    }""")

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
}
