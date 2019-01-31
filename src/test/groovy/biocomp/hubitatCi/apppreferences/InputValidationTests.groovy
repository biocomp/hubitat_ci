package biocomp.hubitatCi.apppreferences


import spock.lang.Unroll

import org.codehaus.groovy.runtime.metaclass.MethodSelectionException
import spock.lang.Specification

import static PreferencesValidationCommon.parseOneChild
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.combinations


/**
 * Validation tests for preferences' inputs
 * (separated from the rest of preferences test).*/
class InputValidationTests extends
        Specification
{

    def "input() can only consist of name and type"() {
        given:
            def input = parseOneChild("input 'nam', 'bool'")

        expect:
            input.unnamedOptions.name == "nam"
            input.unnamedOptions.type == "bool"
    }

    def "input(name:'some name', type:text) should fail because text should be quoted"() {
        when:
            parseOneChild("input name:'some nam', type:text")

        then:
            AssertionError e = thrown()
            e.message.contains("'type' value can't be null")
    }

    def "input() can't be empty"() {
        when:
            def input = parseOneChild("input()")

        then:
            MethodSelectionException e = thrown()
            e.message.contains("input()")
    }

    def "input() with all valid options reads them successfully"() {
        when:
            def input = parseOneChild("""input(
capitalization: true,
defaultValue: 123,
name: "inputName",
title: "input title",
description: "input description",
multiple: true,
range: "3..200",
required: true,
submitOnChange: true,
options: ["choice 1", "choice 2", "choice 3"],
type: "number",
hideWhenEmpty: true)
""") as Input

        then:
            input.options.capitalization == true
            input.options.defaultValue == 123
            input.options.name == "inputName"
            input.options.title == "input title"
            input.options.description == "input description"
            input.options.multiple == true
            input.options.range == "3..200"
            input.options.required == true
            input.options.submitOnChange == true
            input.options.options == ["choice 1", "choice 2", "choice 3"]
            input.options.type == "number"
            input.options.hideWhenEmpty == true
    }

    def "input() fails if receives invalid option"() {
        when:
            def input = parseOneChild("""input(someInvalidOption: "blah")""")


        then:
            AssertionError e = thrown()
            e.message.contains("not supported")
            e.message.contains("someInvalidOption")
    }

    def "input()'s name is required"() {
        when:
            def input = parseOneChild("input(type: 'bool')")

        then:
            AssertionError e = thrown()
            e.message.contains("name")
            e.message.contains("required")
    }

    def "input()'s type is required"() {
        when:
            def input = parseOneChild("input(name: 'nam')")

        then:
            AssertionError e = thrown()
            e.message.contains("type")
            e.message.contains("required")
    }

    def "input()'s name cant'be be null or empty"(String inputFormat, String expectedProblem) {
        when:
            def input = parseOneChild(inputFormat)

        then:
            AssertionError e = thrown()
            e.message.contains("'name'")
            e.message.contains(expectedProblem)

        where:
            inputFormat                       | expectedProblem
            "input(null, 'bool')"             | 'null'
            "input(name: null, type: 'bool')" | 'null'
            "input('', 'bool')"               | 'empty'
            "input(name: '', type: 'bool')"   | 'empty'
    }

    def "input()'s type cant'be be null or empty"(String inputFormat, String expectedProblem) {
        when:
            def input = parseOneChild(inputFormat)

        then:
            AssertionError e = thrown()
            e.message.contains("'type'")
            e.message.contains(expectedProblem)

        where:
            inputFormat                      | expectedProblem
            "input('nam', null)"             | 'null'
            "input(name: 'nam', type: null)" | 'null'
            "input('nam', '')"               | 'empty'
            "input(name: 'nam', type: '')"   | 'empty'

    }

    def "input() can't have name declared in two places"() {
        when:
            def input = parseOneChild("""input("nam1", "boolean", name: 'nam2')""")

        then:
            AssertionError e = thrown()
            e.message.contains("[name] were passed both as named (via Map) and implicit")
            e.message.contains("nam1")
            e.message.contains("nam2")
    }

    def "input() can't have type declared in two places"() {
        when:
            def input = parseOneChild("""input("nam1", "boolean", type: 'text')""")

        then:
            AssertionError e = thrown()
            e.message.contains("[type] were passed both as named (via Map) and implicit")
            e.message.contains("type")
            e.message.contains("boolean")
            e.message.contains("text")
    }

    def "Checking that valid input() types succeed"(String type) {
        when:
            def input = [parseOneChild("""input("nam1", '${type}')""") as Input,
                         parseOneChild("""input(name: "nam1", type: '${type}')""") as Input]


        then:
            input[0].readType() == type
            input[1].readType() == type

        where:
            type << ["capability.thermostat",
                     "device.someDeviceName",
                     "bool",
                     //"boolean",
                     "decimal",
                     "email",
                     "enum",
                     "hub",
                     "icon",
                     "number",
                     "password",
                     "phone",
                     "time",
                     "text"]
    }

    @Unroll
    def "Invalid input(name, type) types fail #typeAndInputType"() {
        when:
            def type = typeAndInputType[0]
            def input = typeAndInputType[1] ?
                    parseOneChild("""input("nam1", '${type}')""") as Input :
                    parseOneChild("""input(name: "nam1", type: '${type}')""") as Input

        then:
            AssertionError e = thrown()
            e.message.contains("not supported")
            e.message.contains("nam1")
            e.message.contains(type)

        where:
            typeAndInputType << combinations([["capability.badCapability",
                                               "devic.someDeviceName",
                                               "devicee.someDeviceName",
                                               "blah",
                                               "booll"], [true, false]])
    }
}