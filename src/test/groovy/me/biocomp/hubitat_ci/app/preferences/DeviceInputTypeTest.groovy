package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.capabilities.Thermostat
import me.biocomp.hubitat_ci.capabilities.ThermostatCoolingSetpoint
import me.biocomp.hubitat_ci.capabilities.ThermostatMode
import me.biocomp.hubitat_ci.util.NullableOptional
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues
import spock.lang.Specification

class DeviceInputTypeTest extends Specification{
    def "Simple capability 'ThermostatCoolingSetpoint' input generates object with all proper fields and methods"()
    {
        setup:
            def device = new DeviceInputValueFactory(ThermostatCoolingSetpoint, "ThermostatCoolingSetpoint").makeInputObject('n', 't',  DefaultAndUserValues.empty())
            def attributes = device.getSupportedAttributes()

        expect:
            assert device.capability == ThermostatCoolingSetpoint
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
    }

    def "Complex capability 'Thermostat' input generates object with all proper fields and methods"()
    {
        setup:
            def device = new DeviceInputValueFactory(Thermostat, "Thermostat").makeInputObject('n', 't',  DefaultAndUserValues.empty())
            def attributes = device.getSupportedAttributes()

        expect:
            !device.hasUserProvidedValue()

            assert device.capability == Thermostat.class
            assert attributes.collect{it.name} as Set == ["supportedThermostatFanModes", "supportedThermostatModes", "temperature", "coolingSetpoint", "thermostatFanMode", "heatingSetpoint", "thermostatMode", "thermostatOperatingState", "schedule", "thermostatSetpoint"] as Set

            assert((Double)device.currentCoolingSetpoint == 0)
            assert((Double)device.currentHeatingSetpoint == 0)
            assert(device.currentSupportedThermostatFanModes == 0)
            assert(device.currentSupportedThermostatModes == 0)
            assert(device.currentTemperature == 0)
            assert(device.currentThermostatSetpoint == 0)
            assert(device.currentSchedule == 0)

            // Just make sure this is callable
            device.setCoolingSetpoint(42.42)
            device.setHeatingSetpoint(42.42)
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

    def "When user object provided, calls to methods and attributes are redirected to it"()
    {
        when:
            def userThermostat = new MockThermostat()
            def device = new DeviceInputValueFactory(Thermostat, "Thermostat").makeInputObject('n', 't',  DefaultAndUserValues.bothValues(
                    NullableOptional.empty(), NullableOptional.withValue(userThermostat)))

            device.setCoolingSetpoint(42.42)
            device.setHeatingSetpoint(12.12)

        then:
            device.userProvidedValue == userThermostat
            userThermostat.coolingSetpoints == [42.42]
            userThermostat.heatingSetpoints == [12.12]

            assert(device.currentCoolingSetpoint == 123)
            assert(device.currentHeatingSetpoint == 42)
            assert(device.currentTemperature == 22)
    }

    def "Device without capability has null capability and no attributes"()
    {
        when:
            def device = new DeviceInputValueFactory(null, "SomeDevice").makeInputObject('n', 't',  DefaultAndUserValues.empty())

        then:
            device.supportedAttributes == []
            device.capability == null
    }

    def "Enum attribute values are properly listed"() {
        setup:
            final def device = new DeviceInputValueFactory(ThermostatMode, "ThermostatMode").makeInputObject('n', 't', DefaultAndUserValues.empty())
            final def attributes = device.getSupportedAttributes()

        expect:
            attributes.find{it.name == "thermostatMode"}.values as Set == ["auto", "off", "heat", "emergency heat", "cool"] as Set
    }
}
