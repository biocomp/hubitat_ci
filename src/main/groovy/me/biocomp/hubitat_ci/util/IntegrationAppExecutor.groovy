package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.BaseScheduler
import me.biocomp.hubitat_ci.app.HubitatAppScript

/**
* An implementation of portions of AppExecutor that are useful for integration tests.
* It is not a full implementation of the AppExecutor abstract class, so it
* is still expected to be wrapped in a Spock Spy.
* The parts that are implemented are:
* - Time methods from BaseExecutor trait
* - Methods from Subscription interface
* - Methods from BaseScheduler trait (passed through to a BaseScheduler dependency)
*/
abstract class IntegrationAppExecutor implements AppExecutor {
    IntegrationAppExecutor() {
    }

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
            if (subInfo.toWhat == device && subInfo.attributeNameOrNameAndValueOrEventName == properties.name) {
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

    /*******************************************************
     * BEGIN SECTION: Methods from BaseScheduler trait
     *******************************************************/

    // This is the scheduler that will be used to schedule events.
    // This app executor outsources its scheduling logic to the scheduler, if it exists.
    // In tests, it will generally be an instance of IntegrationScheduler.
    BaseScheduler scheduler

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Minute(MetaMethod handlerMethod) { scheduler?.runEvery1Minute(handlerMethod) }
    void runEvery1Minute(String handlerMethod) { scheduler?.runEvery1Minute(handlerMethod) }
    void runEvery1Minute(MetaMethod handlerMethod, Map options) { scheduler?.runEvery1Minute(handlerMethod, options) }
    void runEvery1Minute(String handlerMethod, Map options) { scheduler?.runEvery1Minute(handlerMethod, options) }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery5Minutes(MetaMethod handlerMethod) { scheduler?.runEvery5Minutes(handlerMethod) }
    void runEvery5Minutes(String handlerMethod) { scheduler?.runEvery5Minutes(handlerMethod) }
    void runEvery5Minutes(MetaMethod handlerMethod, Map options) { scheduler?.runEvery5Minutes(handlerMethod, options) }
    void runEvery5Minutes(String handlerMethod, Map options) { scheduler?.runEvery5Minutes(handlerMethod, options) }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery10Minutes(MetaMethod handlerMethod) { scheduler?.runEvery10Minutes(handlerMethod) }
    void runEvery10Minutes(String handlerMethod) { scheduler?.runEvery10Minutes(handlerMethod) }
    void runEvery10Minutes(MetaMethod handlerMethod, Map options) { scheduler?.runEvery10Minutes(handlerMethod, options) }
    void runEvery10Minutes(String handlerMethod, Map options) { scheduler?.runEvery10Minutes(handlerMethod, options) }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery15Minutes(MetaMethod handlerMethod) { scheduler?.runEvery15Minutes(handlerMethod) }
    void runEvery15Minutes(String handlerMethod) { scheduler?.runEvery15Minutes(handlerMethod) }
    void runEvery15Minutes(MetaMethod handlerMethod, Map options) { scheduler?.runEvery15Minutes(handlerMethod, options) }
    void runEvery15Minutes(String handlerMethod, Map options) { scheduler?.runEvery15Minutes(handlerMethod, options) }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery30Minutes(MetaMethod handlerMethod) { scheduler?.runEvery30Minutes(handlerMethod) }
    void runEvery30Minutes(String handlerMethod) { scheduler?.runEvery30Minutes(handlerMethod) }
    void runEvery30Minutes(MetaMethod handlerMethod, Map options) { scheduler?.runEvery30Minutes(handlerMethod, options) }
    void runEvery30Minutes(String handlerMethod, Map options) { scheduler?.runEvery30Minutes(handlerMethod, options) }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Hour(MetaMethod handlerMethod) { scheduler?.runEvery1Hour(handlerMethod) }
    void runEvery1Hour(String handlerMethod) { scheduler?.runEvery1Hour(handlerMethod) }
    void runEvery1Hour(MetaMethod handlerMethod, Map options) { scheduler?.runEvery1Hour(handlerMethod, options) }
    void runEvery1Hour(String handlerMethod, Map options) { scheduler?.runEvery1Hour(handlerMethod, options) }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery3Hours(MetaMethod handlerMethod) { scheduler?.runEvery3Hours(handlerMethod) }
    void runEvery3Hours(String handlerMethod) { scheduler?.runEvery3Hours(handlerMethod) }
    void runEvery3Hours(MetaMethod handlerMethod, Map options) { scheduler?.runEvery3Hours(handlerMethod, options) }
    void runEvery3Hours(String handlerMethod, Map options) { scheduler?.runEvery3Hours(handlerMethod, options) }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runIn(Long delayInSeconds, MetaMethod handlerMethod) { scheduler?.runIn(delayInSeconds, handlerMethod) }
    void runIn(Long delayInSeconds, String handlerMethod) { scheduler?.runIn(delayInSeconds, handlerMethod) }
    void runIn(Long delayInSeconds, MetaMethod handlerMethod, Map options) { scheduler?.runIn(delayInSeconds, handlerMethod, options) }
    void runIn(Long delayInSeconds, String handlerMethod, Map options) { scheduler?.runIn(delayInSeconds, handlerMethod, options) }

    void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod) { scheduler?.runInMillis(delayInMilliSeconds, handlerMethod) }
    void runInMillis(Long delayInMilliSeconds, String handlerMethod) { scheduler?.runInMillis(delayInMilliSeconds, handlerMethod) }
    void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod, Map options) { scheduler?.runInMillis(delayInMilliSeconds, handlerMethod, options) }
    void runInMillis(Long delayInMilliSeconds, String handlerMethod, Map options) { scheduler?.runInMillis(delayInMilliSeconds, handlerMethod, options) }

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(Date dateTime, MetaMethod handlerMethod) { scheduler?.runOnce(dateTime, handlerMethod) }
    void runOnce(Date dateTime, String handlerMethod) { scheduler?.runOnce(dateTime, handlerMethod) }
    void runOnce(Date dateTime, MetaMethod handlerMethod, Map options) { scheduler?.runOnce(dateTime, handlerMethod, options) }
    void runOnce(Date dateTime, String handlerMethod, Map options) { scheduler?.runOnce(dateTime, handlerMethod, options) }

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - ISO-8601 date string - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options map. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(String dateTime, MetaMethod handlerMethod) { scheduler?.runOnce(dateTime, handlerMethod) }
    void runOnce(String dateTime, String handlerMethod) { scheduler?.runOnce(dateTime, handlerMethod) }
    void runOnce(String dateTime, MetaMethod handlerMethod, Map options) { scheduler?.runOnce(dateTime, handlerMethod, options) }
    void runOnce(String dateTime, String handlerMethod, Map options) { scheduler?.runOnce(dateTime, handlerMethod, options) }

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(Date dateTime, MetaMethod handlerMethod) { scheduler?.schedule(dateTime, handlerMethod) }
    void schedule(Date dateTime, String handlerMethod) { scheduler?.schedule(dateTime, handlerMethod) }
    void schedule(Date dateTime, MetaMethod handlerMethod, Map options) { scheduler?.schedule(dateTime, handlerMethod, options) }
    void schedule(Date dateTime, String handlerMethod, Map options) { scheduler?.schedule(dateTime, handlerMethod, options) }
    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod) { scheduler?.schedule(cronExpressionOrIsoDate, handlerMethod) }
    void schedule(String cronExpressionOrIsoDate, String handlerMethod) { scheduler?.schedule(cronExpressionOrIsoDate, handlerMethod) }
    void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod, Map options) { scheduler?.schedule(cronExpressionOrIsoDate, handlerMethod, options) }
    void schedule(String cronExpressionOrIsoDate, String handlerMethod, Map options) { scheduler?.schedule(cronExpressionOrIsoDate, handlerMethod, options) }

    /**
     * Deletes all scheduled jobs for the App.
     */
    void unschedule() { scheduler?.unschedule() }

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    void unschedule(MetaMethod method) { scheduler?.unschedule(method) }

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    void unschedule(String method) { scheduler?.unschedule(method) }

    /*******************************************************
     * END SECTION: Methods from BaseScheduler trait
     *******************************************************/
}

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