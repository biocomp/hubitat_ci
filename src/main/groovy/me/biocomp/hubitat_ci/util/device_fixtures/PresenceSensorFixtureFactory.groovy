package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.PresenceSensor

/**
 * Factory for creating fixtures of presence sensor devices that behave like real presence sensors in Hubitat.
 * These devices can be used in testing of app scripts, by passing them as inputs.
 */
class PresenceSensorFixtureFactory {
    /**
    * Constructs a new instance of a presence sensor device fixture.
    */
    static def create(String name) {
        def deviceInputValueFactory = new DeviceInputValueFactory([PresenceSensor])

        def device = deviceInputValueFactory.makeInputObject(name)

        def deviceMetaClass = device.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        deviceMetaClass.initialize = { appExecutor, initialAttributeValues ->
            // The PresenceSensor capability has no commands and only a single state attribute: presence.
            attributeValues = initialAttributeValues

            // However, for the purposes of testing, I'm adding public methods to the fixture.
            // They're not actual commands in real Hubitat, but are useful for testing.
            // They will let you set the presence attribute value, and publish an event for the change.
            deviceMetaClass.detectMotion = { ->
                attributeValues.presence = 'present'
                appExecutor.sendEvent(device, [name: "presence", value: 'present'])
            }
            deviceMetaClass.noMotion = { ->
                attributeValues.presence = 'not present'
                appExecutor.sendEvent(device, [name: "presence", value: 'not present'])
            }
        }

        return device
    }
}
