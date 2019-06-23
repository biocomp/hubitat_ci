package biocomp.hubitatCi.app

import biocomp.hubitatCi.api.preferences.Page
import biocomp.hubitatCi.app.preferences.Input
import biocomp.hubitatCi.app.preferences.Page
import biocomp.hubitatCi.app.preferences.Section
import biocomp.hubitatCi.validation.Flags
import spock.lang.Specification
import spock.lang.Unroll

class AppDataTest extends Specification{
    def "generateInputObject() returns null if both args null"()
    {
        expect:
            new AppData().generateInputWrapper(null, null) == null
    }

    def "generateInputObject() returns userProvidedObject if input is null"()
    {
        expect:
            new AppData().generateInputWrapper(null, "whatever") == "whatever"
    }

    @Unroll
    def "generateInputObject() returns userProvidedObject if input doesn't have capability (type == #inputType)"(String inputType)
    {
        setup:
            def data = new AppData()
            def input = new Input([name: 'myInputName', type: inputType], [:], EnumSet.of(Flags.DontValidatePreferences))

            def section = new Section(0, 't', [:])
            section.children.add(input)

            def page = new Page(0, 'p', 't', [:])
            page.sections.add(section)

            data.preferences.pages.add(page)

        expect:
            data.generateInputWrapper('myInputName', "whatever") == "whatever"

        where:
            inputType << [ "number", "capability.badcapability" ]
    }
}
