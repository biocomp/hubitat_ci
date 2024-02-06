package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.Switch
import me.biocomp.hubitat_ci.capabilities.SwitchLevel
import me.biocomp.hubitat_ci.capabilities.FanControl

/**
 * Factory for creating fixtures of fan devices that behave like real fans in Hubitat.
 * These devices can be used in testing of app scripts, by passing them as inputs.
 * They also maintain state, and send events when their state changes.
 */
class FanFixtureFactory {
    /**
    * Constructs a new instance of a fan device fixture.
    */
    static def create(String name) {
        def deviceInputValueFactory = new DeviceInputValueFactory([Switch, SwitchLevel, FanControl])

        def fanDevice = deviceInputValueFactory.makeInputObject(name, 't',  DefaultAndUserValues.empty(), false)

        def fanMetaClass = fanDevice.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        fanMetaClass.initialize = { appExecutor, state ->
            fanMetaClass.state = state
            fanMetaClass.on = {
                state.switch = "on"
                appExecutor.sendEvent(fanDevice, [name: "switch.on", value: "on"])

                if (state.level == 0) {
                    setLevel(100)
                }
            }
            fanMetaClass.off = {
                state.switch = "off"
                state.speed = "off"
                state.level = 0
                appExecutor.sendEvent(fanDevice, [name: "switch.off", value: "off"])
                appExecutor.sendEvent(fanDevice, [name: "speed", value: "off"])
                appExecutor.sendEvent(fanDevice, [name: "level", value: 0])
            }
            fanMetaClass.setLevel = { int level ->
                // Most real fans will do this in their firmware:
                if (state.switch == "off" && level > 0) {
                    state.switch = "on"
                    appExecutor.sendEvent(fanDevice, [name: "switch.on", value: "on"])
                }
                else if (state.switch == "on" && level == 0) {
                    state.switch = "off"
                    appExecutor.sendEvent(fanDevice, [name: "switch.off", value: "off"])
                }

                state.level = level

                appExecutor.sendEvent(fanDevice, [name: "level", value: level])

                def speed = levelToSpeed(level)
                state.speed = speed
                appExecutor.sendEvent(fanDevice, [name: "speed", value: speed])
            }
            fanMetaClass.setSpeed = { String speed ->
                if (state.switch == "off" && speed != "off") {
                    state.switch = "on"
                    appExecutor.sendEvent(fanDevice, [name: "switch.on", value: "on"])
                }
                else if (state.switch == "on" && speed == "off") {
                    state.switch = "off"
                    appExecutor.sendEvent(fanDevice, [name: "switch.off", value: "off"])
                }

                state.speed = speed
                appExecutor.sendEvent(fanDevice, [name: "speed", value: speed])

                def level = speedToLevel(speed)
                state.level = level
                appExecutor.sendEvent(fanDevice, [name: "level", value: level])
            }
            fanMetaClass.cycleSpeed = {
                def newSpeed = cycleSpeed(state.speed)
                setSpeed(newSpeed)
            }

            // Three helper methods to be used internally.
            fanMetaClass.speedToLevel = { String speed ->
                switch (speed) {
                    case "low":
                        return 16
                    case "medium-low":
                        return 33
                    case "medium":
                        return 50
                    case "medium-high":
                        return 75
                    case "high":
                        return 100
                    case "on":
                        return 100
                    case "off":
                        return 0
                    case "auto":
                        return 100
                    default:
                        return 0
                }
            }

            fanMetaClass.levelToSpeed = { int level ->
                if (level == 0) {
                    return "off"
                }
                else if (level <= 16) {
                    return "low"
                }
                else if (level <= 33) {
                    return "medium-low"
                }
                else if (level <= 50) {
                    return "medium"
                }
                else if (level <= 75) {
                    return "medium-high"
                }
                else {
                    return "high"
                }
            }

            fanMetaClass.cycleSpeed = { String speed ->
                switch (speed) {
                    case "low":
                        return "medium-low"
                    case "medium-low":
                        return "medium"
                    case "medium":
                        return "medium-high"
                    case "medium-high":
                        return "high"
                    case "high":
                        return "off"
                    case "on":
                        return "low"
                    case "off":
                        return "low"
                    case "auto":
                        return "low"
                    default:
                        return "off"
                }
            }
        }

        return fanDevice
    }
}
