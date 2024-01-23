package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.api.common_api.BaseScheduler
import me.biocomp.hubitat_ci.util.TimeKeeper
import me.biocomp.hubitat_ci.util.TimeChangedEvent
import me.biocomp.hubitat_ci.util.TimeChangedListener

/**
* An implementation of BaseScheduler that is intended for use by integration tests.
* It does not actually schedule anything, but instead records the schedule requests.
* It then receives TimeChangedEvents from the observable TimeKeeper class, and
* executes any scheduled methods that would be between the old time and the new time.
*/
class IntegrationScheduler implements BaseScheduler, TimeChangedListener {
    IntegrationScheduler(TimeKeeper timekeeper) {
        timekeeper.addListener(this)
    }

    boolean _is_hubitat_ci_private() { true }

    @Override
    void timeChangedEventReceived(TimeChangedEvent event) {
        // TODO - Implement
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Minute(MetaMethod handlerMethod) {}
    void runEvery1Minute(String handlerMethod) {}
    void runEvery1Minute(MetaMethod handlerMethod, Map options) {}
    void runEvery1Minute(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery5Minutes(MetaMethod handlerMethod) {}
    void runEvery5Minutes(String handlerMethod) {}
    void runEvery5Minutes(MetaMethod handlerMethod, Map options) {}
    void runEvery5Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery10Minutes(MetaMethod handlerMethod) {}
    void runEvery10Minutes(String handlerMethod) {}
    void runEvery10Minutes(MetaMethod handlerMethod, Map options) {}
    void runEvery10Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery15Minutes(MetaMethod handlerMethod) {}
    void runEvery15Minutes(String handlerMethod) {}
    void runEvery15Minutes(MetaMethod handlerMethod, Map options) {}
    void runEvery15Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery30Minutes(MetaMethod handlerMethod) {}
    void runEvery30Minutes(String handlerMethod) {}
    void runEvery30Minutes(MetaMethod handlerMethod, Map options) {}
    void runEvery30Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Hour(MetaMethod handlerMethod) {}
    void runEvery1Hour(String handlerMethod) {}
    void runEvery1Hour(MetaMethod handlerMethod, Map options) {}
    void runEvery1Hour(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery3Hours(MetaMethod handlerMethod) {}
    void runEvery3Hours(String handlerMethod) {}
    void runEvery3Hours(MetaMethod handlerMethod, Map options) {}
    void runEvery3Hours(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runIn(Long delayInSeconds, MetaMethod handlerMethod) {}
    void runIn(Long delayInSeconds, String handlerMethod) {}
    void runIn(Long delayInSeconds, MetaMethod handlerMethod, Map options) {}
    void runIn(Long delayInSeconds, String handlerMethod, Map options) {}

    void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod) {}
    void runInMillis(Long delayInMilliSeconds, String handlerMethod) {}
    void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod, Map options) {}
    void runInMillis(Long delayInMilliSeconds, String handlerMethod, Map options) {}

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(Date dateTime, MetaMethod handlerMethod) {}
    void runOnce(Date dateTime, String handlerMethod) {}
    void runOnce(Date dateTime, MetaMethod handlerMethod, Map options) {}
    void runOnce(Date dateTime, String handlerMethod, Map options) {}

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - ISO-8601 date string - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options map. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(String dateTime, MetaMethod handlerMethod) {}
    void runOnce(String dateTime, String handlerMethod) {}
    void runOnce(String dateTime, MetaMethod handlerMethod, Map options) {}
    void runOnce(String dateTime, String handlerMethod, Map options) {}

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(Date dateTime, MetaMethod handlerMethod) {}
    void schedule(Date dateTime, String handlerMethod) {}
    void schedule(Date dateTime, MetaMethod handlerMethod, Map options) {}
    void schedule(Date dateTime, String handlerMethod, Map options) {}
    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod) {}
    void schedule(String cronExpressionOrIsoDate, String handlerMethod) {}
    void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod, Map options) {}
    void schedule(String cronExpressionOrIsoDate, String handlerMethod, Map options) {}

    /**
     * Deletes all scheduled jobs for the App.
     */
    void unschedule() {}

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    void unschedule(MetaMethod method) {}

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    void unschedule(String method) {}

}