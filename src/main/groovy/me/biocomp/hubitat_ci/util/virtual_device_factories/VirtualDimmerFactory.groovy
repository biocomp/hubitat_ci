package me.biocomp.hubitat_ci.util.virtual_device_factories

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.Switch
import me.biocomp.hubitat_ci.capabilities.SwitchLevel

/**
 * Factory for creating virtual dimmer devices that behave like real dimmers in Hubitat.
 * These dimmer devices can be used in testing of app scripts.
 * Like real dimmers, they implement both the Switch and SwitchLevel capabilities.
 * They also maintain state, and send events when their state changes.
 */
class VirtualDimmerFactory {
    private DeviceInputValueFactory deviceInputValueFactory

    VirtualDimmerFactory() {
        this.deviceInputValueFactory = new DeviceInputValueFactory([Switch, SwitchLevel])
    }

    /**
    * Constructs a new instance of a virtual dimmer device.
    */
    def constructDevice(String name) {
        def dimmerDevice = this.deviceInputValueFactory.makeInputObject(name, 't',  DefaultAndUserValues.empty(), false)
        return dimmerDevice
    }

    /**
    * Attaches dimmer behavior involving commands, state maintenance, and sending events.
    */
    def attachBehavior(dimmerDevice, api, script, state) {
        def dimmerMetaClass = dimmerDevice.getMetaClass()
        dimmerMetaClass.state = state
        dimmerMetaClass.on = {
            state.switch = "on"
            api.sendEvent(dimmerDevice, [name: "switch.on", value: "on"])
        }
        dimmerMetaClass.off = {
            state.switch = "off"
            api.sendEvent(dimmerDevice, [name: "switch.off", value: "off"])
        }
        dimmerMetaClass.setLevel = { int level ->
            state.level = level

            api.sendEvent(dimmerDevice, [name: "level", value: level])

            if (level > 0) {
                on()
            }
        }

        return dimmerDevice
    }
}
