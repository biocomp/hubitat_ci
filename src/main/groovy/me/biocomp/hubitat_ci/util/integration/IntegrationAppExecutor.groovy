package me.biocomp.hubitat_ci.util.integration

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.BaseScheduler
import me.biocomp.hubitat_ci.app.HubitatAppScript
import me.biocomp.hubitat_ci.util.integration.DeviceEventArgs
import me.biocomp.hubitat_ci.util.integration.PassthroughScheduler

/**
* An implementation of portions of AppExecutor that are useful for integration tests.
* It is not a full implementation of the AppExecutor abstract class, so it
* is still expected to be wrapped in a Spock Spy.
* The parts that are implemented are:
* - Time methods from BaseExecutor trait
* - Methods from Subscription interface
* - Methods from BaseScheduler trait (passed through to a BaseScheduler dependency)
*/
abstract class IntegrationAppExecutor implements AppExecutor, PassthroughScheduler {
    IntegrationAppExecutor(BaseScheduler scheduler) {
        this.scheduler = scheduler
    }

    /**************************************************************
     * BEGIN SECTION: Time methods from BaseExecutor trait
     **************************************************************/

    @Override
    long now() {
        return TimeKeeper.now().getTime()
    }

    @Override
    Date toDateTime(String dateTimeString) {
        // Hubitat hub converts dates to strings when you store them in state.  It uses the
        // format: 2020-11-02T14:32:17+0000
        // Hubitat provides the toDateTime(String) method to convert back to a Date object.
        return Date.parse("yyyy-MM-dd'T'HH:mm:ssZ", dateTimeString)
    }

    /**************************************************************
     * END SECTION: Time methods from BaseExecutor trait
     **************************************************************/


    /*****************************************************************
     * BEGIN SECTION: Methods from Subscription interface
     *****************************************************************/

    private List<DeviceEventSubInfo> deviceEventSubscriptions = []
    private HubitatAppScript script

    void setSubscribingScript(HubitatAppScript script) {
        this.script = script
        this.scheduler?.setHandlingObject(script)
    }

    @Override
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler) {
        if (toWhat in Collection) {
            toWhat.each { Object it ->
                deviceEventSubscriptions << new DeviceEventSubInfo(it, attributeNameOrNameAndValueOrEventName, handler)
            }
        }
        else {
            deviceEventSubscriptions << new DeviceEventSubInfo(toWhat, attributeNameOrNameAndValueOrEventName, handler)
        }
    }

    @Override
    void unsubscribe() {
        deviceEventSubscriptions.clear()
    }

    @Override
    void sendEvent(DeviceWrapper device, Map properties) {
        deviceEventSubscriptions.each { DeviceEventSubInfo subInfo ->
            if (subInfo.toWhat != device) {
                return
            }

            def matchOfAttributeName = subInfo.attributeNameOrNameAndValueOrEventName == properties.name
            def matchOfNameAndValue = subInfo.attributeNameOrNameAndValueOrEventName == "${properties.name}.${properties.value}"

            if (matchOfAttributeName || matchOfNameAndValue) {
                def generatedEvent = new DeviceEventArgs(device.getIdAsLong(), device, properties.name, properties.value)
                script."$subInfo.handler"(generatedEvent)
            }
        }
    }

    /**
    * Private class for tracking subscriptions to device events.
    */
    private class DeviceEventSubInfo {
        Object toWhat
        String attributeNameOrNameAndValueOrEventName
        Object handler

        DeviceEventSubInfo(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler) {
            this.toWhat = toWhat
            this.attributeNameOrNameAndValueOrEventName = attributeNameOrNameAndValueOrEventName
            this.handler = handler
        }
    }

    /*****************************************************************
     * END SECTION: Methods from Subscription interface
     *****************************************************************/
}