package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.validation.Flags
import groovy.transform.TypeChecked
import spock.lang.Specification
import spock.lang.Unroll

class AppDefinitionReaderTest extends
        Specification
{
    @Unroll
    def "definition() needs to provide mandatory parameters"(
            Map parameters, List<String> expectedErrorParts, boolean verifyAllParametersPrinted)
    {
        when:
            new HubitatAppSandbox("definition(${parameters.collect { "${it.key}: '${it.value}'" }.join(', ')})").run(
                    validationFlags: [Flags.DontValidatePreferences])

        then:
            AssertionError e = thrown()
            expectedErrorParts.each { e.message.contains(it) }

            // Order here is important, these are sorted by name
            if (verifyAllParametersPrinted) {
                e.message.contains("[author, description, iconUrl, iconX2Url, iconX3Url, name, namespace]")
            }

        where:
            parameters                                                                                                    | expectedErrorParts              | verifyAllParametersPrinted
            [/*name: "v",*/ namespace: "v", author: "v", description: "v", iconUrl: "v", iconX2Url: "v", iconX3Url: "v"]  | ["name", "not provided"]        | true
            [name: "", namespace: "v", author: "v", description: "v", iconUrl: "v", iconX2Url: "v", iconX3Url: "v"]       | ["name", "empty"]               | false
            [name: "v", /*namespace: "v", */ author: "v", description: "v", iconUrl: "v", iconX2Url: "v", iconX3Url: "v"] | ["namespace", "not provided"]   | true
            [name: "v", namespace: "v", /*author: "v", */ description: "v", iconUrl: "v", iconX2Url: "v", iconX3Url: "v"] | ["author", "not provided"]      | true
            [name: "v", namespace: "v", author: "v", /*description: "v",*/ iconUrl: "v", iconX2Url: "v", iconX3Url: "v"]  | ["description", "not provided"] | true
            [name: "v", namespace: "v", author: "v", description: "v", /*iconUrl: "v", */ iconX2Url: "v", iconX3Url: "v"] | ["iconUrl", "not provided"]     | true
            [name: "v", namespace: "v", author: "v", description: "v", iconUrl: "v", /*iconX2Url: "v",*/ iconX3Url: "v"]  | ["iconX2Url", "not provided"]   | true
            [name: "v", namespace: "v", author: "v", description: "v", iconUrl: "v", iconX2Url: "v", /*iconX3Url: "v"*/]  | ["iconX3Url", "not provided"]   | true
    }

    def "Script with no definition() call will fail"()
    {
        when:
            new HubitatAppSandbox("int x = 0").run(validationFlags: [Flags.DontValidatePreferences])

        then:
            AssertionError e = thrown()
            e.message.contains("definition()")
            e.message.contains("empty")
    }

    def "Script with no definition() call will succeed, if definition validation disabled"()
    {
        expect:
            new HubitatAppSandbox("int x = 0").run(validationFlags: [Flags.DontValidateDefinition, Flags.DontValidatePreferences])
    }

    @TypeChecked
    private static def generateValidDefinitionsWith(String entry)
    {
        return new HubitatAppSandbox($/
                definition(
                    name: "v",
                    namespace: "v",
                    author: "v",
                    description: "v",
                    iconUrl: "v",
                    iconX2Url: "v",
                    iconX3Url: "v",
                    parent: "biocomp:My Parent",
                    ${entry})/$).run(validationFlags: [Flags.DontValidatePreferences]).getProducedDefinition()
    }

    def "Other supported parameters are accepted"()
    {
        expect:
            generateValidDefinitionsWith("category: 'cat'").category == 'cat'
            generateValidDefinitionsWith("singleInstance: true").singleInstance == true
            generateValidDefinitionsWith("oauth: true").oauth == true
    }

    def "Unsupported parameters cause error"()
    {
        when:
            generateValidDefinitionsWith("unsupportedStuff: 'blah'")

        then:
            AssertionError e = thrown()
            e.message.contains("unsupportedStuff")
    }
}
