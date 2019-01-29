package biocomp.hubitatCiTest.emulation.commonApi;

import groovy.lang.MetaMethod;

import java.util.Date;
import java.util.Map;

interface BaseScheduler
{
    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery1Minute(MetaMethod handlerMethod)
    abstract void runEvery1Minute(String handlerMethod)
    abstract void runEvery1Minute(MetaMethod handlerMethod, Map options)
    abstract void runEvery1Minute(String handlerMethod, Map options)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery5Minutes(MetaMethod handlerMethod)
    abstract void runEvery5Minutes(String handlerMethod)
    abstract void runEvery5Minutes(MetaMethod handlerMethod, Map options)
    abstract void runEvery5Minutes(String handlerMethod, Map options)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery10Minutes(MetaMethod handlerMethod)
    abstract void runEvery10Minutes(String handlerMethod)
    abstract void runEvery10Minutes(MetaMethod handlerMethod, Map options)
    abstract void runEvery10Minutes(String handlerMethod, Map options)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery15Minutes(MetaMethod handlerMethod)
    abstract void runEvery15Minutes(String handlerMethod)
    abstract void runEvery15Minutes(MetaMethod handlerMethod, Map options)
    abstract void runEvery15Minutes(String handlerMethod, Map options)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery30Minutes(MetaMethod handlerMethod)
    abstract void runEvery30Minutes(String handlerMethod)
    abstract void runEvery30Minutes(MetaMethod handlerMethod, Map options)
    abstract void runEvery30Minutes(String handlerMethod, Map options)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery1Hour(MetaMethod handlerMethod)
    abstract void runEvery1Hour(String handlerMethod)
    abstract void runEvery1Hour(MetaMethod handlerMethod, Map options)
    abstract void runEvery1Hour(String handlerMethod, Map options)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery3Hours(MetaMethod handlerMethod)
    abstract void runEvery3Hours(String handlerMethod)
    abstract void runEvery3Hours(MetaMethod handlerMethod, Map options)
    abstract void runEvery3Hours(String handlerMethod, Map options)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runIn(Long delayInSeconds, MetaMethod handlerMethod)
    abstract void runIn(Long delayInSeconds, String handlerMethod)
    abstract void runIn(Long delayInSeconds, MetaMethod handlerMethod, Map options)
    abstract void runIn(Long delayInSeconds, String handlerMethod, Map options)

    abstract void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod)
    abstract void runInMillis(Long delayInMilliSeconds, String handlerMethod)
    abstract void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod, Map options)
    abstract void runInMillis(Long delayInMilliSeconds, String handlerMethod, Map options)

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runOnce(Date dateTime, MetaMethod handlerMethod)
    abstract void runOnce(Date dateTime, String handlerMethod)
    abstract void runOnce(Date dateTime, MetaMethod handlerMethod, Map options)
    abstract void runOnce(Date dateTime, String handlerMethod, Map options)

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - ISO-8601 date string - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options map. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the abstract behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runOnce(String dateTime, MetaMethod handlerMethod)
    abstract void runOnce(String dateTime, String handlerMethod)
    abstract void runOnce(String dateTime, MetaMethod handlerMethod, Map options)
    abstract void runOnce(String dateTime, String handlerMethod, Map options)

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    abstract void schedule(Date dateTime, MetaMethod handlerMethod)
    abstract void schedule(Date dateTime, String handlerMethod)
    abstract void schedule(Date dateTime, MetaMethod handlerMethod, Map options)
    abstract void schedule(Date dateTime, String handlerMethod, Map options)
    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    abstract void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod)
    abstract void schedule(String cronExpressionOrIsoDate, String handlerMethod)
    abstract void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod, Map options)
    abstract void schedule(String cronExpressionOrIsoDate, String handlerMethod, Map options)

    /**
     * Deletes all scheduled jobs for the App.
     */
    abstract void unschedule()

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    abstract void unschedule(MetaMethod method)

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    abstract void unschedule(String method)
}
