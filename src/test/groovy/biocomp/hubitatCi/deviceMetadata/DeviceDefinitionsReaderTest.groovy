package biocomp.hubitatCi.deviceMetadata

import biocomp.hubitatCi.HubitatDeviceSandbox
import spock.lang.Specification

class DeviceDefinitionsReaderTest extends Specification{
    def "Reading all valid definition() options"()
    {
        setup:
            def definition = new HubitatDeviceSandbox("""
metadata{
    definition(name: "test device", namespace: "yournamespace", author: "your name"){
    }
}
""").run().getProducedDefinition()

        expect:
            definition.options.name == 'test device'
            definition.options.namespace == 'yournamespace'
            definition.options.author == 'your name'
    }

    def "definition() with invalid option fails"()
    {
        when:
            new HubitatDeviceSandbox("""
metadata{
    definition(name: "test device", badOption: "i'm so bad", namespace: "yournamespace", author: "your name"){
    }
}
""").run()

        then:
            AssertionError e = thrown()
            e.message.contains('badOption')
            e.message.contains('not supported')
            e.message.contains('definition')
    }
}
