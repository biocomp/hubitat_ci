package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.api.common_api.BaseScheduler
import me.biocomp.hubitat_ci.util.TimeKeeper
import me.biocomp.hubitat_ci.util.TimeChangedEvent
import me.biocomp.hubitat_ci.util.TimeChangedListener

import org.quartz.CronExpression

/**
* An implementation of BaseScheduler that is intended for use by integration tests.
* It does not actually schedule anything, but instead records the schedule requests.
* It then receives TimeChangedEvents from the observable TimeKeeper class, and
* executes any scheduled methods that would be between the old time and the new time.
*/
class IntegrationScheduler implements BaseScheduler, TimeChangedListener {
    private TimeKeeper timekeeper

    IntegrationScheduler(TimeKeeper timekeeper) {
        timekeeper?.addListener(this)
        this.timekeeper = timekeeper
    }

    boolean _is_hubitat_ci_private() { true }

    String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    @Override
    void timeChangedEventReceived(TimeChangedEvent event) {
        // TODO - Implement
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Minute(MetaMethod handlerMethod) {
        schedule("0 * * * * ?", handlerMethod)
    }
    void runEvery1Minute(String handlerMethod) {
        schedule("0 * * * * ?", handlerMethod)
    }
    void runEvery1Minute(MetaMethod handlerMethod, Map options) {
        schedule("0 * * * * ?", handlerMethod, options)
    }
    void runEvery1Minute(String handlerMethod, Map options) {
        schedule("0 * * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery5Minutes(MetaMethod handlerMethod) {
        schedule("0 */5 * * * ?", handlerMethod)
    }
    void runEvery5Minutes(String handlerMethod) {
        schedule("0 */5 * * * ?", handlerMethod)
    }
    void runEvery5Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */5 * * * ?", handlerMethod, options)
    }
    void runEvery5Minutes(String handlerMethod, Map options) {
        schedule("0 */5 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery10Minutes(MetaMethod handlerMethod) {
        schedule("0 */10 * * * ?", handlerMethod)
    }
    void runEvery10Minutes(String handlerMethod) {
        schedule("0 */10 * * * ?", handlerMethod)
    }
    void runEvery10Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */10 * * * ?", handlerMethod, options)
    }
    void runEvery10Minutes(String handlerMethod, Map options) {
        schedule("0 */10 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery15Minutes(MetaMethod handlerMethod) {
        schedule("0 */15 * * * ?", handlerMethod)
    }
    void runEvery15Minutes(String handlerMethod) {
        schedule("0 */15 * * * ?", handlerMethod)
    }
    void runEvery15Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */15 * * * ?", handlerMethod, options)
    }
    void runEvery15Minutes(String handlerMethod, Map options) {
        schedule("0 */15 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery30Minutes(MetaMethod handlerMethod) {
        schedule("0 */30 * * * ?", handlerMethod)
    }
    void runEvery30Minutes(String handlerMethod) {
        schedule("0 */30 * * * ?", handlerMethod)
    }
    void runEvery30Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */30 * * * ?", handlerMethod, options)
    }
    void runEvery30Minutes(String handlerMethod, Map options) {
        schedule("0 */30 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Hour(MetaMethod handlerMethod) {
        schedule("0 0 * * * ?", handlerMethod)
    }
    void runEvery1Hour(String handlerMethod) {
        schedule("0 0 * * * ?", handlerMethod)
    }
    void runEvery1Hour(MetaMethod handlerMethod, Map options) {
        schedule("0 0 * * * ?", handlerMethod, options)
    }
    void runEvery1Hour(String handlerMethod, Map options) {
        schedule("0 0 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery3Hours(MetaMethod handlerMethod) {
        schedule("0 0 */3 * * ?", handlerMethod)
    }
    void runEvery3Hours(String handlerMethod) {
        schedule("0 0 */3 * * ?", handlerMethod)
    }
    void runEvery3Hours(MetaMethod handlerMethod, Map options) {
        schedule("0 0 */3 * * ?", handlerMethod, options)
    }
    void runEvery3Hours(String handlerMethod, Map options) {
        schedule("0 0 */3 * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runIn(Long delayInSeconds, MetaMethod handlerMethod) {
        runInMillis(delayInSeconds * 1000, handlerMethod)
    }
    void runIn(Long delayInSeconds, String handlerMethod) {
        runInMillis(delayInSeconds * 1000, handlerMethod)
    }
    void runIn(Long delayInSeconds, MetaMethod handlerMethod, Map options) {
        runInMillis(delayInSeconds * 1000, handlerMethod, options)
    }
    void runIn(Long delayInSeconds, String handlerMethod, Map options) {
        runInMillis(delayInSeconds * 1000, handlerMethod, options)
    }

    void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod) {
        runInMillis(delayInMilliSeconds, handlerMethod.name, null)
    }
    void runInMillis(Long delayInMilliSeconds, String handlerMethod) {
        runInMillis(delayInMilliSeconds, handlerMethod, null)
    }
    void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod, Map options) {
        runInMillis(delayInMilliSeconds, handlerMethod.name, options)
    }
    void runInMillis(Long delayInMilliSeconds, String handlerMethod, Map options) {
        runOnce(new Date((timekeeper != null ? timekeeper.now() : new Date()).getTime() + delayInMilliSeconds), handlerMethod, options)
    }

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(Date dateTime, MetaMethod handlerMethod) {
        runOnce(dateTime, handlerMethod.name, null)
    }
    void runOnce(Date dateTime, String handlerMethod) {
        runOnce(dateTime, handlerMethod, null)
    }
    void runOnce(Date dateTime, MetaMethod handlerMethod, Map options) {
        runOnce(dateTime, handlerMethod.name, options)
    }
    void runOnce(Date dateTime, String handlerMethod, Map options) {
        runOnce(dateTime.format(ISO_8601_FORMAT), handlerMethod, options)
    }

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - ISO-8601 date string - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options map. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(String dateTime, MetaMethod handlerMethod) {
        runOnce(dateTime, handlerMethod.name, null)
    }
    void runOnce(String dateTime, String handlerMethod) {
        runOnce(dateTime, handlerMethod, null)
    }
    void runOnce(String dateTime, MetaMethod handlerMethod, Map options) {
        runOnce(dateTime, handlerMethod.name, options)
    }
    void runOnce(String dateTime, String handlerMethod, Map options) {
        def scheduleRequest = new ScheduleRequest(
            cronExpressionOrIsoDate: dateTime,
            handlerMethod: handlerMethod,
            options: options,
            deleteAfterSingleRun: true
        )
        _scheduleRequests.add(scheduleRequest)
    }

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(Date dateTime, MetaMethod handlerMethod) {
        schedule(dateTime, handlerMethod.name, null)
    }
    void schedule(Date dateTime, String handlerMethod) {
        schedule(dateTime, handlerMethod, null)
    }
    void schedule(Date dateTime, MetaMethod handlerMethod, Map options) {
        schedule(dateTime, handlerMethod.name, options)
    }
    void schedule(Date dateTime, String handlerMethod, Map options) {
        schedule(dateTime.format(ISO_8601_FORMAT), handlerMethod, options)
    }
    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod) {
        schedule(cronExpressionOrIsoDate, handlerMethod.name, null)
    }
    void schedule(String cronExpressionOrIsoDate, String handlerMethod) {
        schedule(cronExpressionOrIsoDate, handlerMethod, null)
    }
    void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod, Map options) {
        schedule(cronExpressionOrIsoDate, handlerMethod.name, options)
    }
    void schedule(String cronExpressionOrIsoDate, String handlerMethod, Map options) {
        def scheduleRequest = new ScheduleRequest(
            cronExpressionOrIsoDate: cronExpressionOrIsoDate,
            handlerMethod: handlerMethod,
            options: options,
            deleteAfterSingleRun: false
        )
        _scheduleRequests.add(scheduleRequest)
    }

    /**
     * Deletes all scheduled jobs for the App.
     */
    void unschedule() {
        _scheduleRequests.clear()
    }

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    void unschedule(MetaMethod method) {
        _scheduleRequests.removeIf { it.handlerMethod == method.name }
    }

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    void unschedule(String method) {
        _scheduleRequests.removeIf { it.handlerMethod == method }
    }

    // All the cron jobs that have been scheduled
    ArrayList<ScheduleRequest> _scheduleRequests = new ArrayList<ScheduleRequest>()

    private class ScheduleRequest {
        String cronExpressionOrIsoDate
        String handlerMethod
        Map options
        boolean deleteAfterSingleRun
    }
}