package me.biocomp.hubitat_ci.capabilities

import spock.lang.Specification

import java.lang.reflect.Method

class GeneratedPropertiesTest extends Specification {
    def "GeneratedAttribute is correctly constructed from #attributeInfo"() {
        setup:
            final def generated = new GeneratedAttribute(attributeInfo)

        expect:
            generated.name == expectedName
            generated.dataType == expectedDataType
            generated.values == expectedValues

        where:
            attributeInfo                                              || expectedName     | expectedDataType | expectedValues
            Capabilities.readAttributes(Button)["button"]              || "button"         | "NUMBER"         | null
            Capabilities.readAttributes(Button)["holdableButton"]      || "holdableButton" | "ENUM"           | ["true", "false"]
            Capabilities.readAttributes(Chime)["soundEffects"]         || "soundEffects"   | "JSON_OBJECT"    | null
            Capabilities.readAttributes(Chime)["soundName"]            || "soundName"      | "STRING"         | null
            Capabilities.readAttributes(EstimatedTimeOfArrival)["eta"] || "eta"            | "DATE"           | null
    }

    private static Method findMethod(Class capability, String method) {
        Capabilities.readMethods(capability).find { it.name == method }
    }

    def "GeneratedCommand is correctly constructed from #command"() {
        setup:
            final def c = new GeneratedCommand(command)

        expect:
            c.name == name
            c.arguments == arguments

        where:
            command                                               || name                   | arguments
            findMethod(TV, "channelDown")                         || "channelDown"          | []
            findMethod(SwitchLevel, "setLevel")                   || "setLevel"             | ["NUMBER", "NUMBER"]
            findMethod(ThermostatFanMode, "setThermostatFanMode") || "setThermostatFanMode" | ["STRING"]
            findMethod(Thermostat, "setSchedule")                 || "setSchedule"          | ["JSON_OBJECT"]
    }

    def "GeneratedCapability is correctly constructed from #capability"() {
        setup:
            final def c = new GeneratedCapability(capability)
            final def expectedAttributes = Capabilities.readAttributes(capability)
            final def expectedCommands = Capabilities.readMethods(capability)

        expect:
            c.attributes.collect{it.name}.sort() == expectedAttributes.collect{it.key}.sort()
            c.commands.collect{it.name}.sort() == expectedCommands.collect{it.name}.sort()

        where:
            capability | _
            Thermostat | _
    }
}
