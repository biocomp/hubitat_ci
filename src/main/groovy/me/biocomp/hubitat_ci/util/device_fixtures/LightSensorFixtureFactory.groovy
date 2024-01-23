package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.IlluminanceMeasurement

/**
 * Factory for creating fixtures of light sensor devices that behave like real light sensors in Hubitat.
 * These dimmer devices can be used in testing of app scripts, by passing them as inputs.
 */
class LightSensorFixtureFactory {
    /**
    * Constructs a new instance of a light sensor device fixture.
    */
    static def create(String name) {
        def deviceInputValueFactory = new DeviceInputValueFactory([IlluminanceMeasurement])

        def lightSensorDevice = deviceInputValueFactory.makeInputObject(name, 't',  DefaultAndUserValues.empty(), false)

        def lightSensorMetaClass = lightSensorDevice.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        lightSensorMetaClass.initialize = { appExecutor, state ->
            // The IlluminanceMeasurement capability has no commands and only a single state attribute: illuminance.
            lightSensorMetaClass.state = state

            // However, for the purposes of testing, I'm adding a public method to the fixture.
            // It's not an actual command in real Hubitat, but it's useful for testing.
            // It will let you set the illuminance state value, and publish an event for the change.
            lightSensorMetaClass.setIlluminance = { int illuminance ->
                lightSensorMetaClass.state.illuminance = illuminance
                appExecutor.sendEvent(lightSensorDevice, [name: "illuminance", value: illuminance])
            }
        }

        return lightSensorDevice
    }
}
