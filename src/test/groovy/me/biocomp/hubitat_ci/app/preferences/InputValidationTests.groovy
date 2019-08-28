package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.validation.Flags
import org.codehaus.groovy.runtime.metaclass.MethodSelectionException
import spock.lang.Specification
import spock.lang.Unroll

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

    def "input() with most (compatible) valid options reads them successfully"() {
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
                     //"enum", // Enum is tested separately - it needs 'options'
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
            def input = typeAndInputType[1] ? parseOneChild("""input("nam1", '${type}')""") as Input : parseOneChild(
                    """input(name: "nam1", type: '${type}')""") as Input

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

    def "Script can only read inputs that were defined, and fails for undefined"() {
        setup:
            def script = new HubitatAppSandbox("""
preferences{
    page("p", "t"){
        section(){
            input "existingInput", "bool", title: "fromPage0"
        }
    }
}

def methodThatUsesInputs()
{
    def good = existingInput
    def bad = missingInput
}
""").run(validationFlags: [Flags.DontValidateDefinition, Flags.AllowMissingInstall])

        when:
            script.methodThatUsesInputs()

        then:
            AssertionError e = thrown()
            e.message.contains("not registered inputs: [missingInput]")
            e.message.contains("registered inputs: [existingInput]")
    }

    def "Enum input type must have 'options'"() {
        when:
            parseOneChild("input 'nam', 'enum'")

        then:
            AssertionError e = thrown()
            e.message.contains("of type 'enum' must have 'options' parameter with enum values")
    }

    @Unroll
    def "Non-enum input(type: '#type') types must not have options"(String type) {
        when:
            parseOneChild("""input(name: "nam1", type: '${type}', options: ["A", "B"])""")

        then:
            AssertionError e = thrown()
            e.message.contains("only 'enum' input type needs 'options' parameter")
            e.message.contains("nam1")

        where:
            type << ["capability.thermostat",
                     "device.someDeviceName",
                     "bool",
                     //"boolean",
                     "decimal",
                     "email",
                     //"enum", // Enum is tested separately - it needs 'options'
                     "hub",
                     "icon",
                     "number",
                     "password",
                     "phone",
                     "time",
                     "text"]
    }

    def "Enum input type can take list of strings as options"() {
        when:
            def input = parseOneChild("input 'nam', 'enum', options: ['Val1', 'Val2']")

        then:
            input.readType() == 'enum'
            input.options.options == ['Val1', 'Val2']
    }

    def "Enum input type can't take string as 'options'"() {
        when:
            def input = parseOneChild("input 'nam', 'enum', options: 'Val1'")

        then:
            AssertionError e = thrown()
            e.message.contains("must be a list of values or map int->value")
            e.message.contains("Val1")
    }

    def "Enum input type can take list of ints as options"() {
        when:
            def input = parseOneChild("input 'nam', 'enum', options: [11, 22]")

        then:
            input.readType() == 'enum'
            input.options.options == [11, 22]
    }


    def "Enum input type can take map of int->string as options"() {
        when:
            def input = parseOneChild("input 'nam', 'enum', options: [42:'Val1', 33:'Val2']")

        then:
            input.readType() == 'enum'
            input.options.options == [42: 'Val1', 33: 'Val2']
    }

    @Unroll
    def "Enum default value must be one of options (#options)"(String options) {
        when:
            parseOneChild("input 'nam', 'enum', defaultValue: 'ValUnknown', options: ${options}")

        then:
            AssertionError e = thrown()
            e.message.contains("defaultValue 'ValUnknown' is not one of valid values: [Val1, Val2]")

        where:
            options << ["['Val1', 'Val2']",
                        "[42:'Val1', 33:'Val2']"]
    }

    @Unroll
    def "Enum options (#options) can't repeat each other"(String options, String whatWasDuplicated) {
        when:
            parseOneChild("input 'nam', 'enum', options: ${options}")

        then:
            AssertionError e = thrown()
            e.message.contains("enum ${whatWasDuplicated} was duplicated")

        where:
            options                             | whatWasDuplicated
            "['Val2', 'Val1', 'Val2']"          | "value 'Val2'"
            "[11:'Val2', 22:'Val1', 33:'Val2']" | "value 'Val2'"
    }
}