package me.biocomp.hubitat_ci.util.integration

import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper

/**
* Class for sending events to handlers in app scripts.  It replicates
* the key parts of the events that the Hubitat framework passes to app scripts.
*/
class DeviceEventArgs {
    Object deviceId
    DeviceWrapper device
    String name
    Object value

    DeviceEventArgs(Object deviceId, DeviceWrapper device, String name, Object value) {
        this.deviceId = deviceId
        this.device = device
        this.name = name
        this.value = value
    }
}