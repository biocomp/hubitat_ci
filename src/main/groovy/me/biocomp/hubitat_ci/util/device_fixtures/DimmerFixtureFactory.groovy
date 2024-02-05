package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.Switch
import me.biocomp.hubitat_ci.capabilities.SwitchLevel
import me.biocomp.hubitat_ci.capabilities.DoubleTapableButton

/**
 * Factory for creating fixtures of dimmer devices that behave like real dimmers in Hubitat.
 * These dimmer devices can be used in testing of app scripts, by passing them as inputs.
 * Like real dimmers, they implement both the Switch and SwitchLevel capabilities.
 * They also maintain state, and send events when their state changes.
 */
class DimmerFixtureFactory {
    /**
    * Constructs a new instance of a dimmer device fixture.
    */
    static def create(String name) {
        def deviceInputValueFactory = new DeviceInputValueFactory([Switch, SwitchLevel, DoubleTapableButton])

        def dimmerDevice = deviceInputValueFactory.makeInputObject(name, 't',  DefaultAndUserValues.empty(), false)

        def dimmerMetaClass = dimmerDevice.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        dimmerMetaClass.initialize = { appExecutor, state ->
            dimmerMetaClass.state = state
            dimmerMetaClass.on = {
                state.switch = "on"
                state.doubleTapped = null
                appExecutor.sendEvent(dimmerDevice, [name: "switch.on", value: "on"])

                if (state.level == 0) {
                    setLevel(100)
                }
            }
            dimmerMetaClass.off = {
                state.switch = "off"
                state.doubleTapped = null
                appExecutor.sendEvent(dimmerDevice, [name: "switch.off", value: "off"])
            }
            dimmerMetaClass.setLevel = { int level ->
                // Most real dimmers will do this in their firmware:
                if (state.switch == "off" && level > 0) {
                    state.switch = "on"
                    appExecutor.sendEvent(dimmerDevice, [name: "switch.on", value: "on"])
                }

                state.level = level
                state.doubleTapped = null

                appExecutor.sendEvent(dimmerDevice, [name: "level", value: level])
            }
            dimmerMetaClass.doubleTap = { buttonNumber ->
                state.doubleTapped = buttonNumber
                appExecutor.sendEvent(dimmerDevice, [name: "doubleTapped.${buttonNumber}", value: buttonNumber])
            }
        }

        return dimmerDevice
    }
}
