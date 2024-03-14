package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.Switch
import me.biocomp.hubitat_ci.capabilities.DoubleTapableButton

/**
 * Factory for creating fixtures of switch devices that behave like real switches in Hubitat.
 * These devices can be used in testing of app scripts, by passing them as inputs.
 * They also maintain state, and send events when their state changes.
 */
class SwitchFixtureFactory {
    /**
    * Constructs a new instance of a switch device fixture.
    */
    static def create(String name) {
        def deviceInputValueFactory = new DeviceInputValueFactory([Switch, DoubleTapableButton])

        def switchDevice = deviceInputValueFactory.makeInputObject(name)

        def switchMetaClass = switchDevice.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        switchMetaClass.initialize = { appExecutor, initialAttributeValues ->
            attributeValues = initialAttributeValues

            switchMetaClass.on = {
                attributeValues.switch = "on"
                attributeValues.doubleTapped = null
                appExecutor.sendEvent(switchDevice, [name: "switch.on", value: "on"])
            }
            switchMetaClass.off = {
                attributeValues.switch = "off"
                attributeValues.doubleTapped = null
                appExecutor.sendEvent(switchDevice, [name: "switch.off", value: "off"])
            }
            switchMetaClass.doubleTap = { buttonNumber ->
                attributeValues.doubleTapped = buttonNumber
                appExecutor.sendEvent(switchDevice, [name: "doubleTapped.${buttonNumber}", value: buttonNumber])
            }
        }

        return switchDevice
    }
}
