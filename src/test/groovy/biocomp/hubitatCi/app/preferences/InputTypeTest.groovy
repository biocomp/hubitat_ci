package biocomp.hubitatCi.app.preferences

import biocomp.hubitatCi.validation.Flags
import spock.lang.Specification

class InputTypeTest extends Specification{
    def "Number input is 42 by default"()
    {
        expect:
            new Input([name: 'n', type: 'number'], [:], EnumSet.noneOf(Flags)).makeInputObject() == 42
    }
}
