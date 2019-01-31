package biocomp.hubitatCi.util


import biocomp.hubitatCi.validation.AppValidator
import biocomp.hubitatCi.validation.NamedParametersValidator
import spock.lang.Specification

class NamedParametersValidatorTest extends
        Specification
{
    def makeStringValidator(Map options)
    {
        return NamedParametersValidator.make {
            stringParameter(options)
        }
    }

    def makeEnumStringValidator(Map options)
    {
        return NamedParametersValidator.make {
            enumStringParameter(options)
        }
    }

    def "String validator works with both String and GString"()
    {
        setup:
            def answer = 42

        expect:
            makeStringValidator(name: "val1").validate("ctx", [val1: "someTextVal"], new AppValidator())
            makeStringValidator(name: "val1").validate("ctx", [val1: "answer is ${answer}"], new AppValidator())
    }

    def "String enum validator works with both String and GString"()
    {
        setup:
            def answer = 42

        expect:
            makeEnumStringValidator(name: "val1", values: ["someTextVal"]).validate("ctx", [val1: "someTextVal"], new AppValidator())
            makeEnumStringValidator(name: "val1", values: ["answer42"]).validate("ctx", [val1: "answer${answer}"], new AppValidator())
    }
}
