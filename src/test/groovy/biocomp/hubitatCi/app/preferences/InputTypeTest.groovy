package biocomp.hubitatCi.app.preferences

import biocomp.hubitatCi.validation.Flags
import spock.lang.Specification

class InputTypeTest extends Specification{
    def "Number input is 0 by default"()
    {
        expect:
            new Input([name: 'n', type: 'number'], [:], EnumSet.noneOf(Flags)).makeInputObject() == 0
    }

    def "Number input is 'defaultValue' by default, when provided"()
    {
        expect:
            new Input([name: 'n', type: 'number', defaultValue: '42'], [:], EnumSet.noneOf(Flags)).makeInputObject() == 42
    }

    def "Enum input is a string with first value by default"()
    {
        expect:
            new Input([name: 'n', type: 'enum', options: ["Val1", "Val2"]], [:], EnumSet.noneOf(Flags)).makeInputObject() == "Val1"
            new Input([name: 'n', type: 'enum', options: [42:"Val1", 33:"Val2"]], [:], EnumSet.noneOf(Flags)).makeInputObject() == "42"
    }

    def "Enum input is 'defaultValue' when provided"()
    {
        expect:
            new Input([name: 'n', type: 'enum', defaultValue: 'Val2', options: ["Val1", "Val2"]], [:], EnumSet.noneOf(Flags)).makeInputObject() == "Val2"
            new Input([name: 'n', type: 'enum', defaultValue: 'Val2', options: [42:"Val1", 33:"Val2"]], [:], EnumSet.noneOf(Flags)).makeInputObject() == "33"
    }
}
