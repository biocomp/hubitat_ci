package me.biocomp.hubitat_ci.app.preferences

import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.capabilities.GeneratedCapability
import me.biocomp.hubitat_ci.capabilities.Thermostat
import me.biocomp.hubitat_ci.capabilities.ThermostatCoolingSetpoint
import me.biocomp.hubitat_ci.capabilities.ThermostatMode
import me.biocomp.hubitat_ci.capabilities.Switch
import me.biocomp.hubitat_ci.capabilities.SwitchLevel
import me.biocomp.hubitat_ci.util.NullableOptional
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.GeneratedDeviceInputBase
import spock.lang.Specification
import spock.lang.Unroll

class DeviceInputTypeTest extends Specification{
    private static def getDevice(def from) {
        if (DeviceWrapper.isInstance(from)) {
            from
        } else {
            from[0]
        }
    }

    @Unroll
    def "Simple capability 'ThermostatCoolingSetpoint' input generates object with all proper fields and methods, multiple = #multiple" ()
    {
        setup:
            def device = getDevice(new DeviceInputValueFactory([ThermostatCoolingSetpoint])
                    .makeInputObject('n', 't',  DefaultAndUserValues.empty(), multiple))
            def attributes = device.getSupportedAttributes()

        expect:
            assert device.capabilities.size() == 1
            assert device.capabilities[0].name == "ThermostatCoolingSetpoint"

            assert device.capabilities[0].getAttributes() == attributes

            assert attributes.size() == 1
            assert attributes[0].getDataType() == "NUMBER"
            assert attributes[0].dataType == "NUMBER"

            assert attributes[0].getName() == 'coolingSetpoint'
            assert attributes[0].name == 'coolingSetpoint'

            assert attributes[0].getValues() == null
            assert attributes[0].values == null

            assert((Double)device.currentCoolingSetpoint == 0)

            // Just make sure this is callable
            device.setCoolingSetpoint(42.42)

        where:
            multiple << [true, false]
    }

    def "Complex capability 'Thermostat' input generates object with all proper fields and methods"()
    {
        setup:
            def device = new DeviceInputValueFactory([Thermostat])
                    .makeInputObject('n', 't',  DefaultAndUserValues.empty(), false)
            def attributes = device.getSupportedAttributes()

        expect:
            GeneratedDeviceInputBase.isInstance(device)

            device.capabilities.size() == 1
            device.capabilities[0].name == "Thermostat"

            attributes.collect{it.name} as Set == ["supportedThermostatFanModes", "supportedThermostatModes", "temperature", "coolingSetpoint", "thermostatFanMode", "heatingSetpoint", "thermostatMode", "thermostatOperatingState", "schedule", "thermostatSetpoint"] as Set

            (Double)device.currentCoolingSetpoint == 0
            (Double)device.currentHeatingSetpoint == 0
            device.currentSupportedThermostatFanModes == 0
            device.currentSupportedThermostatModes == 0
            device.currentTemperature == 0
            device.currentThermostatSetpoint == 0
            device.currentSchedule == 0

            // Just make sure this is callable
            device.setCoolingSetpoint(42.42)
            device.setHeatingSetpoint(42.42)
    }

    @Unroll
    def "Multiple capability input generates object with all proper fields and methods, multiple = #multiple" ()
    {
        setup:
            def device = getDevice(new DeviceInputValueFactory([Switch, SwitchLevel])
                    .makeInputObject('n', 't',  DefaultAndUserValues.empty(), multiple))
            def attributes = device.getSupportedAttributes()
            def commands = device.getSupportedCommands()

        expect:
            assert device.capabilities.size() == 2
            assert device.capabilities[0].name == "Switch"
            assert device.capabilities[1].name == "SwitchLevel"

            assert device.capabilities[0].getAttributes()[0] == attributes[0]
            assert device.capabilities[1].getAttributes()[0] == attributes[1]

            assert attributes.size() == 2

            assert attributes[0].getDataType() == "ENUM"
            assert attributes[0].dataType == "ENUM"
            assert attributes[0].getName() == 'switch'
            assert attributes[0].name == 'switch'
            assert attributes[0].getValues() == ["on", "off"]
            assert attributes[0].values == ["on", "off"]

            assert attributes[1].getDataType() == "NUMBER"
            assert attributes[1].dataType == "NUMBER"
            assert attributes[1].getName() == 'level'
            assert attributes[1].name == 'level'
            assert attributes[1].getValues() == null
            assert attributes[1].values == null

            assert commands.size() == 3
            assert commands[0].name == "off"
            assert commands[1].name == "on"
            assert commands[2].name == "setLevel"

        where:
            multiple << [true, false]
    }

    class MockThermostat
    {
        final double currentCoolingSetpoint = 123
        final double currentHeatingSetpoint = 42
        final double currentTemperature = 22

        void setCoolingSetpoint(Number val)
        {
            coolingSetpoints.add(val)
        }

        void setHeatingSetpoint(Number val)
        {
            heatingSetpoints.add(val)
        }

        final List<Number> coolingSetpoints = []
        final List<Number> heatingSetpoints = []
    }

    @Unroll
    def "When user object provided, it is returned instead, multiple = #multiple is ignored"()
    {
        when:
            def userThermostat = new MockThermostat()
            def device = new DeviceInputValueFactory([Thermostat])
                    .makeInputObject('n', 't',  DefaultAndUserValues.bothValues(
                        NullableOptional.empty(), NullableOptional.withValue(userThermostat)), multiple)

            device.setCoolingSetpoint(42.42)
            device.setHeatingSetpoint(12.12)

        then:
            device == userThermostat
            userThermostat.coolingSetpoints == [42.42]
            userThermostat.heatingSetpoints == [12.12]

            assert(device.currentCoolingSetpoint == 123)
            assert(device.currentHeatingSetpoint == 42)
            assert(device.currentTemperature == 22)

        where:
            multiple << [true, false]
    }

    @Unroll
    def "Enum attribute values are properly listed, multiple = #multiple"() {
        setup:
            final def device = getDevice(new DeviceInputValueFactory([ThermostatMode])
                    .makeInputObject('n', 't', DefaultAndUserValues.empty(), multiple))
            final def attributes = device.getSupportedAttributes()

        expect:
            attributes.find{it.name == "thermostatMode"}.values as Set == ["auto", "off", "heat", "emergency heat", "cool"] as Set

        where:
            multiple << [true, false]
    }

    def "End to end test - device input type is properly generated, user value is used when provided, and multiple produces an empty"() {
        setup:
            final def script = new HubitatAppSandbox(PreferencesValidationCommon.pageWith("""
                input "multipleInputNoUserValue", "capability.alarm", title: "Tit1", multiple: true
                input "singleInputNoUserValue", "capability.alarm", title: "Tit2"
                input "multipleInputOverriden", "capability.alarm", title: "Tit3", multiple: true
                input "singleInputOverriden", "capability.alarm", title: "Tit4"
""")).run(userSettingValues: [
                multipleInputOverriden: ["MyVal1", "MyVal2"],
                singleInputOverriden: "MyVal3"],
            validationFlags: [Flags.DontValidateDefinition])

        expect:
            script.multipleInputNoUserValue.size() == 1
            script.multipleInputNoUserValue[0].name == "name_generated_from_multipleInputNoUserValue"

            script.singleInputNoUserValue.name == "name_generated_from_singleInputNoUserValue"

            script.multipleInputOverriden == ["MyVal1", "MyVal2"]

            script.singleInputOverriden == "MyVal3"
    }
}
