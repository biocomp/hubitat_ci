package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.TypeChecked
import spock.lang.Specification

class NamedParametersValidatorTest extends
        Specification
{
    @TypeChecked
    def makeStringValidator(String name)
    {
        return NamedParametersValidator.make {
            stringParameter(name, notRequired(), canBeEmpty())
        }
    }

    @TypeChecked
    def makeEnumStringValidator(String name, List<String> values)
    {
        return NamedParametersValidator.make {
            enumStringParameter(name, notRequired(), values)
        }
    }

    def "String validator works with both String and GString"()
    {
        setup:
            def answer = 42

        expect:
            makeStringValidator("val1").validate("ctx", [val1: "someTextVal"], EnumSet.noneOf(Flags))
            makeStringValidator("val1").validate("ctx", [val1: "answer is ${answer}"], EnumSet.noneOf(Flags))
    }

    def "String enum validator works with both String and GString"()
    {
        setup:
            def answer = 42

        expect:
            makeEnumStringValidator("val1", ["someTextVal"]).validate("ctx", [val1: "someTextVal"], EnumSet.noneOf(Flags))
            makeEnumStringValidator("val1", ["answer42"]).validate("ctx", [val1: "answer${answer}"], EnumSet.noneOf(Flags))
    }
}
