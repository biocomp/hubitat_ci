package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.app.HubitatAppScript

/**
* An implementation of the subscribe() and sendEvent() portions of AppExecutor,
* for use in testing of apps that subscribe to device events.
* It is not a full implementation of the AppExecutor abstract class, so it
* is still expected to be wrapped in a Spock Spy.
*/
abstract class AppExecutorWithEventForwarding implements AppExecutor {
    private List<SubInfo> subscriptions = []

    private HubitatAppScript script

    void setSubscribingScript(HubitatAppScript script) {
        this.script = script
    }

    @Override
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler) {
        if (toWhat in Collection) {
            toWhat.each { Object it ->
                subscriptions << new SubInfo(it, attributeNameOrNameAndValueOrEventName, handler)
            }
        }
        else {
            subscriptions << new SubInfo(toWhat, attributeNameOrNameAndValueOrEventName, handler)
        }
    }

    @Override
    void sendEvent(DeviceWrapper device, Map properties) {
        subscriptions.each { SubInfo subInfo ->
            if (subInfo.toWhat == device && subInfo.attributeNameOrNameAndValueOrEventName == properties.name) {
                def generatedEvent = new DeviceEventArgs(device.getIdAsLong(), device, properties.name, properties.value)
                script."$subInfo.handler"(generatedEvent)
            }
        }
    }

    /**
    * Private class for tracking subscriptions.
    */
    private class SubInfo {
        Object toWhat
        String attributeNameOrNameAndValueOrEventName
        Object handler

        SubInfo(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler) {
            this.toWhat = toWhat
            this.attributeNameOrNameAndValueOrEventName = attributeNameOrNameAndValueOrEventName
            this.handler = handler
        }
    }
}

/**
* Private class for sending events to handlers in app scripts.  It replicates
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