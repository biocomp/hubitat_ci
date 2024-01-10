package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.app.HubitatAppScript

/**
* An implementation of the subscribe() and sendEvent() portions of AppExecutor,
* for use in testing of apps that subscribe to device events.
* It is not a full implementation of the AppExecutor abstract class, so it
* is still expected to be used by a Spock Spy.
*/
abstract class SubscribingAppExecutor implements AppExecutor {
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
            if (subInfo.toWhat == device) {
                if (subInfo.attributeNameOrNameAndValueOrEventName == properties.name) {
                    def generatedEvent = new EventArgs(subInfo.toWhat.deviceId, device, properties.value)
                    script."$subInfo.handler"(generatedEvent)
                }
            }
        }
    }
}

class SubInfo {
    Object toWhat
    String attributeNameOrNameAndValueOrEventName
    Object handler

    SubInfo(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler) {
        this.toWhat = toWhat
        this.attributeNameOrNameAndValueOrEventName = attributeNameOrNameAndValueOrEventName
        this.handler = handler
    }
}

class EventArgs {
    Object deviceId
    DeviceWrapper device
    Object value

    EventArgs(Object deviceId, DeviceWrapper device, Object value) {
        this.deviceId = deviceId
        this.device = device
        this.value = value
    }
}
