package me.biocomp.hubitat_ci.util.integration

import me.biocomp.hubitat_ci.api.common_api.BaseScheduler
import me.biocomp.hubitat_ci.util.integration.TimeKeeper
import me.biocomp.hubitat_ci.util.integration.TimeChangedEvent
import me.biocomp.hubitat_ci.util.integration.TimeChangedListener

import java.util.concurrent.CopyOnWriteArrayList
import groovy.transform.Synchronized
import org.quartz.CronExpression

/**
* An implementation of BaseScheduler that is intended for use by integration tests.
* It does not actually schedule anything, but instead records the schedule requests.
* It then receives TimeChangedEvents from the observable TimeKeeper class, and
* executes any scheduled methods that would be between the old time and the new time.
*/
class IntegrationScheduler implements BaseScheduler, TimeChangedListener {
    private Object handlingObject

    private final integrationSchedulerLock = new Object()

    IntegrationScheduler() {
    }

    def setHandlingObject(Object handlingObject) {
        TimeKeeper.addListener(this)
        this.handlingObject = handlingObject
    }

    boolean _is_hubitat_ci_private() { true }

    static final String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    @Synchronized("integrationSchedulerLock")
    void timeChangedEventReceived(TimeChangedEvent event) {
        if (handlingObject == null) {
            return
        }

        ArrayList<ScheduleRequest> jobsToRemove = new ArrayList<ScheduleRequest>()

        // for each schedule request, see if it should be triggered
        _scheduleRequests.each { scheduleRequest ->
            def shouldRemoveJob = evaluateSingleScheduleRequest(scheduleRequest, event)
            if (shouldRemoveJob) {
                jobsToRemove.add(scheduleRequest)
            }
        }

        // Now, filter out any that are flagged for removal
        _scheduleRequests.removeAll(jobsToRemove)
    }

    private boolean evaluateSingleScheduleRequest(ScheduleRequest scheduleRequest, TimeChangedEvent event) {
        boolean shouldRemoveJob = false

        if (scheduleRequest.cronExpression == null && scheduleRequest.nextFireAt != null) {
            // There's a special case where there's no cronString, but there is a nextFireAt date.
            // This is because if something needs to be scheduled to the second/millisecond, CRON can't do that.
            if (scheduleRequest.nextFireAt.after(event.oldTime) && scheduleRequest.nextFireAt.before(event.newTime)) {
                // fire it
                fireOffScheduledEvent(scheduleRequest)
                shouldRemoveJob = scheduleRequest.deleteAfterSingleRun
            }
        }
        else {
            // But otherwise, we need to evaluate the cron expression, relative to the time that
            // has elapsed.  And we have to account for if the cron expression would have fired
            // more than once during the elapsed time.
            def cronString = scheduleRequest.cronExpression
            def exp = new CronExpression(cronString)

            def evalStartTime = event.oldTime
            def evalEndTime = event.newTime

            while (evalStartTime < evalEndTime) {
                def nextFireAt = exp.getNextValidTimeAfter(evalStartTime)

                if (nextFireAt == null || nextFireAt.after(evalEndTime)) {
                    break
                }

                // fire it
                fireOffScheduledEvent(scheduleRequest)
                shouldRemoveJob = scheduleRequest.deleteAfterSingleRun

                // Advance the evalStartTime to see if we need to fire more than once during this interval
                evalStartTime = nextFireAt
            }
        }

        return shouldRemoveJob
    }

    private void fireOffScheduledEvent(ScheduleRequest scheduleRequest) {
        if (scheduleRequest.options?.data != null) {
            handlingObject."${scheduleRequest.handlerMethod}"(scheduleRequest.options.data)
        } else {
            handlingObject."${scheduleRequest.handlerMethod}"([:])
        }
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runEvery1Minute(MetaMethod handlerMethod) {
        schedule("0 * * * * ?", handlerMethod)
    }
    @Override void runEvery1Minute(String handlerMethod) {
        schedule("0 * * * * ?", handlerMethod)
    }
    @Override void runEvery1Minute(MetaMethod handlerMethod, Map options) {
        schedule("0 * * * * ?", handlerMethod, options)
    }
    @Override void runEvery1Minute(String handlerMethod, Map options) {
        schedule("0 * * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runEvery5Minutes(MetaMethod handlerMethod) {
        schedule("0 */5 * * * ?", handlerMethod)
    }
    @Override void runEvery5Minutes(String handlerMethod) {
        schedule("0 */5 * * * ?", handlerMethod)
    }
    @Override void runEvery5Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */5 * * * ?", handlerMethod, options)
    }
    @Override void runEvery5Minutes(String handlerMethod, Map options) {
        schedule("0 */5 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runEvery10Minutes(MetaMethod handlerMethod) {
        schedule("0 */10 * * * ?", handlerMethod)
    }
    @Override void runEvery10Minutes(String handlerMethod) {
        schedule("0 */10 * * * ?", handlerMethod)
    }
    @Override void runEvery10Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */10 * * * ?", handlerMethod, options)
    }
    @Override void runEvery10Minutes(String handlerMethod, Map options) {
        schedule("0 */10 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runEvery15Minutes(MetaMethod handlerMethod) {
        schedule("0 */15 * * * ?", handlerMethod)
    }
    @Override void runEvery15Minutes(String handlerMethod) {
        schedule("0 */15 * * * ?", handlerMethod)
    }
    @Override void runEvery15Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */15 * * * ?", handlerMethod, options)
    }
    @Override void runEvery15Minutes(String handlerMethod, Map options) {
        schedule("0 */15 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runEvery30Minutes(MetaMethod handlerMethod) {
        schedule("0 */30 * * * ?", handlerMethod)
    }
    @Override void runEvery30Minutes(String handlerMethod) {
        schedule("0 */30 * * * ?", handlerMethod)
    }
    @Override void runEvery30Minutes(MetaMethod handlerMethod, Map options) {
        schedule("0 */30 * * * ?", handlerMethod, options)
    }
    @Override void runEvery30Minutes(String handlerMethod, Map options) {
        schedule("0 */30 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runEvery1Hour(MetaMethod handlerMethod) {
        schedule("0 0 * * * ?", handlerMethod)
    }
    @Override void runEvery1Hour(String handlerMethod) {
        schedule("0 0 * * * ?", handlerMethod)
    }
    @Override void runEvery1Hour(MetaMethod handlerMethod, Map options) {
        schedule("0 0 * * * ?", handlerMethod, options)
    }
    @Override void runEvery1Hour(String handlerMethod, Map options) {
        schedule("0 0 * * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runEvery3Hours(MetaMethod handlerMethod) {
        schedule("0 0 */3 * * ?", handlerMethod)
    }
    @Override void runEvery3Hours(String handlerMethod) {
        schedule("0 0 */3 * * ?", handlerMethod)
    }
    @Override void runEvery3Hours(MetaMethod handlerMethod, Map options) {
        schedule("0 0 */3 * * ?", handlerMethod, options)
    }
    @Override void runEvery3Hours(String handlerMethod, Map options) {
        schedule("0 0 */3 * * ?", handlerMethod, options)
    }

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    @Override void runIn(Long delayInSeconds, MetaMethod handlerMethod) {
        runInMillis(delayInSeconds * 1000, handlerMethod)
    }
    @Override void runIn(Long delayInSeconds, String handlerMethod) {
        runInMillis(delayInSeconds * 1000, handlerMethod)
    }
    @Override void runIn(Long delayInSeconds, MetaMethod handlerMethod, Map options) {
        runInMillis(delayInSeconds * 1000, handlerMethod, options)
    }
    @Override void runIn(Long delayInSeconds, String handlerMethod, Map options) {
        runInMillis(delayInSeconds * 1000, handlerMethod, options)
    }

    @Override void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod) {
        runInMillis(delayInMilliSeconds, handlerMethod.name, null)
    }
    @Override void runInMillis(Long delayInMilliSeconds, String handlerMethod) {
        runInMillis(delayInMilliSeconds, handlerMethod, null)
    }
    @Override void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod, Map options) {
        runInMillis(delayInMilliSeconds, handlerMethod.name, options)
    }
    @Override void runInMillis(Long delayInMilliSeconds, String handlerMethod, Map options) {
        runOnce(new Date(TimeKeeper.now().getTime() + delayInMilliSeconds), handlerMethod, options)
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
    @Override void runOnce(Date dateTime, MetaMethod handlerMethod) {
        runOnce(dateTime, handlerMethod.name, null)
    }
    @Override void runOnce(Date dateTime, String handlerMethod) {
        runOnce(dateTime, handlerMethod, null)
    }
    @Override void runOnce(Date dateTime, MetaMethod handlerMethod, Map options) {
        runOnce(dateTime, handlerMethod.name, options)
    }
    @Override void runOnce(Date dateTime, String handlerMethod, Map options) {
        def scheduleRequest = new ScheduleRequest(
            cronExpression: null,
            nextFireAt: dateTime,
            handlerMethod: handlerMethod,
            options: options,
            deleteAfterSingleRun: true
        )
        addToSchedule(scheduleRequest)
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
    @Override void runOnce(String dateTime, MetaMethod handlerMethod) {
        runOnce(dateTime, handlerMethod.name, null)
    }
    @Override void runOnce(String dateTime, String handlerMethod) {
        runOnce(dateTime, handlerMethod, null)
    }
    @Override void runOnce(String dateTime, MetaMethod handlerMethod, Map options) {
        runOnce(dateTime, handlerMethod.name, options)
    }
    @Override void runOnce(String dateTime, String handlerMethod, Map options) {
        parsedDate = Date.parse(ISO_8601_FORMAT, cronExpressionOrIsoDate)
        runOnce(parsedDate, handlerMethod, options)
    }

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    @Override void schedule(Date dateTime, MetaMethod handlerMethod) {
        schedule(dateTime, handlerMethod.name, null)
    }
    @Override void schedule(Date dateTime, String handlerMethod) {
        schedule(dateTime, handlerMethod, null)
    }
    @Override void schedule(Date dateTime, MetaMethod handlerMethod, Map options) {
        schedule(dateTime, handlerMethod.name, options)
    }
    @Override void schedule(Date dateTime, String handlerMethod, Map options) {
        // CRON expression for once a day at the time portion of dateTime
        def cronExpression = dateTime.format("0 m H * * ?")

        def scheduleRequest = new ScheduleRequest(
            cronExpression: cronExpression,
            handlerMethod: handlerMethod,
            options: options,
            deleteAfterSingleRun: false
        )
        addToSchedule(scheduleRequest)
    }
    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    @Override void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod) {
        schedule(cronExpressionOrIsoDate, handlerMethod.name, null)
    }
    @Override void schedule(String cronExpressionOrIsoDate, String handlerMethod) {
        schedule(cronExpressionOrIsoDate, handlerMethod, null)
    }
    @Override void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod, Map options) {
        schedule(cronExpressionOrIsoDate, handlerMethod.name, options)
    }
    @Override void schedule(String cronExpressionOrIsoDate, String handlerMethod, Map options) {
        // Try to parse cronExpressionOrIsoDate as a date.
        // If it parses, then forward the call to schedule(Date, String, Map)
        // If it does not parse, then assume it is a cron expression and continue.

        Date dateTime = null
        try {
            dateTime = Date.parse(ISO_8601_FORMAT, cronExpressionOrIsoDate)
            schedule(dateTime, handlerMethod, options)
            return
        } catch (Exception e) {
            // ignore
        }

        def scheduleRequest = new ScheduleRequest(
            cronExpression: cronExpressionOrIsoDate,
            handlerMethod: handlerMethod,
            options: options,
            deleteAfterSingleRun: false
        )
        addToSchedule(scheduleRequest)
    }

    @Synchronized("integrationSchedulerLock")
    private void addToSchedule(ScheduleRequest scheduleRequest) {
        _scheduleRequests.add(scheduleRequest)
    }

    /**
     * Deletes all scheduled jobs for the App.
     */
    @Synchronized("integrationSchedulerLock")
    @Override void unschedule() {
        _scheduleRequests.clear()
    }

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    @Synchronized("integrationSchedulerLock")
    @Override void unschedule(MetaMethod method) {
        _scheduleRequests.removeIf { it.handlerMethod == method.name }
    }

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    @Synchronized("integrationSchedulerLock")
    @Override void unschedule(String method) {
        _scheduleRequests.removeIf { it.handlerMethod == method }
    }

    // All the jobs that have been scheduled
    CopyOnWriteArrayList<ScheduleRequest> _scheduleRequests = new CopyOnWriteArrayList<ScheduleRequest>()

    private class ScheduleRequest {
        String cronExpression           // Even if they schedule by an iso date, we're going to convert it to a cron expression before we start tracking it.
        Date nextFireAt                 // Cron can't schedule for seconds and milliseconds, so for some requests, we have to use an exact date.
        String handlerMethod
        Map options
        boolean deleteAfterSingleRun
    }
}