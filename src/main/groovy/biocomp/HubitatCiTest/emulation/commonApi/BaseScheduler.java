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
    default void runEvery1Minute(MetaMethod handlerMethod) {}
    default void runEvery1Minute(String handlerMethod) {}
    default void runEvery1Minute(MetaMethod handlerMethod, Map options) {}
    default void runEvery1Minute(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runEvery5Minutes(MetaMethod handlerMethod) {}
    default void runEvery5Minutes(String handlerMethod) {}
    default void runEvery5Minutes(MetaMethod handlerMethod, Map options) {}
    default void runEvery5Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runEvery10Minutes(MetaMethod handlerMethod) {}
    default void runEvery10Minutes(String handlerMethod) {}
    default void runEvery10Minutes(MetaMethod handlerMethod, Map options) {}
    default void runEvery10Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runEvery15Minutes(MetaMethod handlerMethod) {}
    default void runEvery15Minutes(String handlerMethod) {}
    default void runEvery15Minutes(MetaMethod handlerMethod, Map options) {}
    default void runEvery15Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runEvery30Minutes(MetaMethod handlerMethod) {}
    default void runEvery30Minutes(String handlerMethod) {}
    default void runEvery30Minutes(MetaMethod handlerMethod, Map options) {}
    default void runEvery30Minutes(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runEvery1Hour(MetaMethod handlerMethod) {}
    default void runEvery1Hour(String handlerMethod) {}
    default void runEvery1Hour(MetaMethod handlerMethod, Map options) {}
    default void runEvery1Hour(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runEvery3Hours(MetaMethod handlerMethod) {}
    default void runEvery3Hours(String handlerMethod) {}
    default void runEvery3Hours(MetaMethod handlerMethod, Map options) {}
    default void runEvery3Hours(String handlerMethod, Map options) {}

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runIn(Long delayInSeconds, MetaMethod handlerMethod) {}
    default void runIn(Long delayInSeconds, String handlerMethod) {}
    default void runIn(Long delayInSeconds, MetaMethod handlerMethod, Map options) {}
    default void runIn(Long delayInSeconds, String handlerMethod, Map options) {}

    default void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod) {}
    default void runInMillis(Long delayInMilliSeconds, String handlerMethod) {}
    default void runInMillis(Long delayInMilliSeconds, MetaMethod handlerMethod, Map options) {}
    default void runInMillis(Long delayInMilliSeconds, String handlerMethod, Map options) {}

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runOnce(Date dateTime, MetaMethod handlerMethod) {}
    default void runOnce(Date dateTime, String handlerMethod) {}
    default void runOnce(Date dateTime, MetaMethod handlerMethod, Map options) {}
    default void runOnce(Date dateTime, String handlerMethod, Map options) {}

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - ISO-8601 date string - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options map. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    default void runOnce(String dateTime, MetaMethod handlerMethod) {}
    default void runOnce(String dateTime, String handlerMethod) {}
    default void runOnce(String dateTime, MetaMethod handlerMethod, Map options) {}
    default void runOnce(String dateTime, String handlerMethod, Map options) {}

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    default void schedule(Date dateTime, MetaMethod handlerMethod) {}
    default void schedule(Date dateTime, String handlerMethod) {}
    default void schedule(Date dateTime, MetaMethod handlerMethod, Map options) {}
    default void schedule(Date dateTime, String handlerMethod, Map options) {}
    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    default void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod) {}
    default void schedule(String cronExpressionOrIsoDate, String handlerMethod) {}
    default void schedule(String cronExpressionOrIsoDate, MetaMethod handlerMethod, Map options) {}
    default void schedule(String cronExpressionOrIsoDate, String handlerMethod, Map options) {}

    /**
     * Deletes all scheduled jobs for the App.
     */
    default void unschedule() {}

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    default void unschedule(MetaMethod method) {}

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    default void unschedule(String method) {}
}
