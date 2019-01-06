package biocomp.hubitatCiTest.emulation

import biocomp.hubitatCiTest.util.CapturingLog
import biocomp.hubitatCiTest.util.Log
import biocomp.hubitatCiTest.util.Utility
import groovy.util.slurpersupport.GPathResult

import java.time.ZonedDateTime

/*
 Methods from real class:
 Meta/Common methods:
 * -- public void com.hubitat.hub.executor.BaseExecutor.setMetaClass(groovy.lang.MetaClass),
 * -- public void com.hubitat.hub.executor.BaseExecutor.setProperty(java.lang.String,java.lang.Object),
 * public groovy.lang.MetaClass com.hubitat.hub.executor.BaseExecutor.getMetaClass(),
 * public java.lang.Object com.hubitat.hub.executor.BaseExecutor.getProperty(java.lang.String),
 * public java.lang.Object com.hubitat.hub.executor.BaseExecutor.invokeMethod(java.lang.String,java.lang.Object),

 Main/Getter methods:
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpDelete(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpDelete(java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpDelete(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpDelete(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpDelete(groovy.lang.MetaMethod,java.util.Map,java.util
 * .Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpDelete(java.lang.String,java.util.Map,java.util.Map),
 *
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpGet(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpGet(java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpGet(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpGet(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpGet(groovy.lang.MetaMethod,java.util.Map,java.util
 * .Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpGet(java.lang.String,java.util.Map,java.util.Map),
 *
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpHead(java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpHead(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpHead(java.lang.String,java.util.Map,java.util.Map),
 *
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPatch(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPatch(java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPatch(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPatch(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPatch(groovy.lang.MetaMethod,java.util.Map,java.util
 * .Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPatch(java.lang.String,java.util.Map,java.util.Map),
 *
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPost(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPost(java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPost(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPost(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPost(groovy.lang.MetaMethod,java.util.Map,java.util
 * .Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPost(java.lang.String,java.util.Map,java.util.Map),
 *
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPut(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPut(java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPut(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPut(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPut(groovy.lang.MetaMethod,java.util.Map,java.util
 * .Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.asynchttpPut(java.lang.String,java.util.Map,java.util.Map),
 *
 * ++ public static java.math.BigDecimal com.hubitat.hub.executor.BaseExecutor.celsiusToFahrenheit(java.math
 * .BigDecimal),
 * ++ public java.lang.String com.hubitat.hub.executor.BaseExecutor.convertTemperatureIfNeeded(java.math.BigDecimal,
 * java.lang.String,java.lang.Integer),
 * ++ public static java.lang.String com.hubitat.hub.executor.BaseExecutor.decrypt(java.lang.String),
 * ++ public static java.lang.String com.hubitat.hub.executor.BaseExecutor.encrypt(java.lang.String),
 * ++ public static java.math.BigDecimal com.hubitat.hub.executor.BaseExecutor.fahrenheitToCelsius(java.math
 * .BigDecimal),
 * public static java.lang.String com.hubitat.hub.executor.BaseExecutor.getJWTtoken(java.lang.String,java.lang.String),
 * public static java.lang.String com.hubitat.hub.executor.BaseExecutor.getJWTtoken(java.lang.String,java.lang.String,java.lang.String),
 * ++ public com.hubitat.hub.domain.Location com.hubitat.hub.executor.BaseExecutor.getLocation(),
 *
 * public void com.hubitat.hub.executor.BaseExecutor.createLocationVariable(java.lang.String),
 * public void com.hubitat.hub.executor.BaseExecutor.createLocationVariable(java.lang.String,java.util.List),
 * public void com.hubitat.hub.executor.BaseExecutor.createLocationVariable(java.lang.String,java.util.List,boolean),
 * public java.util.List com.hubitat.hub.executor.BaseExecutor.getLocationVariableNames(),
 * public java.util.List com.hubitat.hub.executor.BaseExecutor.getLocationVariableValues(java.lang.String),
 * public void com.hubitat.hub.executor.BaseExecutor.removeLocationVariable(java.lang.String),
 *
 * public com.hubitat.hub.executor.BaseExecutor$Log com.hubitat.hub.executor.BaseExecutor.getLog(),
 * ++ public static java.lang.String com.hubitat.hub.executor.BaseExecutor.getMACFromIP(java.lang.String),
 * public static java.lang.Object com.hubitat.hub.executor.BaseExecutor.getTTSVoices(),
 * ++ public java.lang.String com.hubitat.hub.executor.BaseExecutor.getTemperatureScale(),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpDelete(java.util.Map,groovy.lang.Closure),
 * ++ public static java.lang.Object com.hubitat.hub.executor.BaseExecutor.httpGet(java.lang.String,groovy.lang
 * .Closure),
 * ++ public static java.lang.Object com.hubitat.hub.executor.BaseExecutor.httpGet(java.util.Map,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPost(java.util.Map,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPost(java.lang.String,java.lang.String,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPostJson(java.util.Map,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPostJson(java.lang.String,java.lang.String,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPostJson(java.lang.String,java.util.Map,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPut(java.util.Map,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPut(java.lang.String,java.lang.String,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPutJson(java.util.Map,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPutJson(java.lang.String,java.lang.String,groovy.lang.Closure),
 * ++ public static void com.hubitat.hub.executor.BaseExecutor.httpPutJson(java.lang.String,java.util.Map,groovy.lang.Closure),
 * ++ public static long com.hubitat.hub.executor.BaseExecutor.now(),
 * ++ public static java.lang.Object com.hubitat.hub.executor.BaseExecutor.parseJson(java.lang.String),
 * ++ public static java.util.Map com.hubitat.hub.executor.BaseExecutor.parseLanMessage(java.lang.String),
 * ++ public static groovy.util.slurpersupport.GPathResult com.hubitat.hub.executor.BaseExecutor.parseXML(java.lang
 * .String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.pauseExecution(java.lang.Long),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery10Minutes(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery10Minutes(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery10Minutes(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery10Minutes(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery15Minutes(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery15Minutes(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery15Minutes(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery15Minutes(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Hour(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Hour(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Hour(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Hour(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Minute(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Minute(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Minute(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery1Minute(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery30Minutes(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery30Minutes(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery30Minutes(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery30Minutes(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery3Hours(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery3Hours(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery3Hours(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery3Hours(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery5Minutes(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery5Minutes(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery5Minutes(groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runEvery5Minutes(java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runIn(java.lang.Long,groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runIn(java.lang.Long,java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runIn(java.lang.Long,groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runIn(java.lang.Long,java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runInMillis(java.lang.Long,groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runInMillis(java.lang.Long,java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runInMillis(java.lang.Long,groovy.lang.MetaMethod,java.util
 * .Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runInMillis(java.lang.Long,java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.lang.String,groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.util.Date,groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.util.Date,java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.lang.String,groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.lang.String,java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.util.Date,groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.runOnce(java.util.Date,java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.lang.String,groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.util.Date,groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.util.Date,java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.lang.String,groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.lang.String,java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.util.Date,groovy.lang.MetaMethod,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.schedule(java.util.Date,java.lang.String,java.util.Map),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.sendLocationEvent(java.util.Map),
 * public void com.hubitat.hub.executor.BaseExecutor.sendPush(java.lang.String),
 * public void com.hubitat.hub.executor.BaseExecutor.sendPush(java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.BaseExecutor.sendPushMessage(java.lang.String),
 * public void com.hubitat.hub.executor.BaseExecutor.sendPushMessage(java.lang.String,java.lang.String),
 * public java.lang.Object com.hubitat.hub.executor.BaseExecutor.setValuesInAsyncResponseAndInvokeCallback(groovyx.net.http.HttpResponseDecorator,java.util.Map,java.lang.String),
 * public java.lang.Object com.hubitat.hub.executor.BaseExecutor.setValuesInAsyncResponseAndInvokeCallback(java.lang.String,java.util.Map,java.lang.String),
 * ++ public static java.lang.Object com.hubitat.hub.executor.BaseExecutor.textToSpeech(java.lang.String),
 * ++ public static java.lang.Object com.hubitat.hub.executor.BaseExecutor.textToSpeech(java.lang.String,java.lang
 * .String),
 * ++ public static boolean com.hubitat.hub.executor.BaseExecutor.timeOfDayIsBetween(java.util.Date,java.util.Date,
 * java.util.Date),
 * ++ public static boolean com.hubitat.hub.executor.BaseExecutor.timeOfDayIsBetween(java.util.Date,java.util.Date,
 * java.util.Date,java.util.TimeZone),
 * ++ public static java.util.Date com.hubitat.hub.executor.BaseExecutor.timeToday(java.lang.String),
 * ++ public static java.util.Date com.hubitat.hub.executor.BaseExecutor.timeToday(java.lang.String,java.util.TimeZone),
 * ++ public static java.util.Date com.hubitat.hub.executor.BaseExecutor.toDateTime(java.lang.String),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.unschedule(),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.unschedule(groovy.lang.MetaMethod),
 * ++ public void com.hubitat.hub.executor.BaseExecutor.unschedule(java.lang.String),
 */

/**
 * Methods that can be used inside App or Driver, that are also implemented here for simplicity.*/
interface SeparateHelperMethodsApiImpl {
    /**
     * @param map - string of format "key1: value1, key2: value2"
     */
    Map stringToMap(String map)
}

/**Methods that can be used inside both App or Driver.*/
interface BaseExecutorApi extends
        SeparateHelperMethodsApiImpl
{
    Location getLocation()

    /**
     * @return "C" or "F"
     */
    String getTemperatureScale()

    /**
     * @return current Unix time in milliseconds.
     */
    long now()

    /**
     * return true if value is between start and end
     */
    boolean timeOfDayIsBetween(Date start, Date stop, Date value, TimeZone timeZone)
    boolean timeOfDayIsBetween(Date start, Date stop, Date value)

    BigDecimal celsiusToFahrenheit(BigDecimal val)

    BigDecimal fahrenheitToCelsius(BigDecimal val)

    void httpGet(String uri, Closure closure)

    void httpGet(Map params, Closure closure)

    void httpPost(String uri, String body, Closure closure)

    void httpPost(Map params, Closure closure)

    void httpPutJson(String uri, String body, Closure closure)

    void httpPutJson(String uri, Map body, Closure closure)

    void httpPutJson(Map params, Closure closure)

    void httpPut(String uri, String body, Closure closure)

    void httpPut(Map params, Closure closure)

    void httpDelete(Map params, Closure closure)

    String getMACFromIP(String ipAddr)

    String convertTemperatureIfNeeded(BigDecimal value, String scale, Integer precision)

    Object parseJson(String stringToParse)

    GPathResult parseXML(String stringToParse)

    /**
     * Parses a Base64-encoded LAN message received from the Hub into a map with header and body elements,
     * as well as parsing the body into an XML document.
     * @param stringToParse
     * @return map with:
     *  header (String) - the headers of the request as a single string
     *  headers (Map) - a Map of string/name value pairs for each header
     *  body (String) the request body as a string
     */
    Map parseLanMessage(String stringToParse)


    void pauseExecution(Long milliseconds)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Minute(def handlerMethod, Map options)
    void runEvery1Minute(def handlerMethod)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery5Minutes(def handlerMethod, Map options)
    void runEvery5Minutes(def handlerMethod)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery10Minutes(def handlerMethod, Map options)
    void runEvery10Minutes(def handlerMethod)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery15Minutes(def handlerMethod, Map options)
    void runEvery15Minutes(def handlerMethod)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery30Minutes(def handlerMethod, Map options)
    void runEvery30Minutes(def handlerMethod)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery1Hour(def handlerMethod, Map options)
    void runEvery1Hour(def handlerMethod)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runEvery3Hours(def handlerMethod, Map options)
    void runEvery3Hours(def handlerMethod)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runIn(Long delayInSeconds, def handlerMethod, Map options)
    void runIn(Long delayInSeconds, def handlerMethod)


    void runInMillis(Long delayInMilliSeconds, def handlerMethod, Map options)
    void runInMillis(Long delayInMilliSeconds, def handlerMethod)

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(Date dateTime, def handlerMethod, Map options)
    void runOnce(Date dateTime, def handlerMethod)

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - ISO-8601 date string - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options map. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    void runOnce(String dateTime, def handlerMethod, Map options)
    void runOnce(String dateTime, def handlerMethod)

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(Date dateTime, def handlerMethod, Map options)
    void schedule(Date dateTime, def handlerMethod)

    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    void schedule(String cronExpressionOrIsoDate, def handlerMethod, Map options)
    void schedule(String cronExpressionOrIsoDate, def handlerMethod)

    void asynchttpGet(String callbackMethod, Map params, Map data)
    void asynchttpGet(String callbackMethod, Map params)
    void asynchttpGet(Map params, Map data)
    void asynchttpGet(Map params)


    void asynchttpPost(String callbackMethod, Map params, Map data)
    void asynchttpPost(String callbackMethod, Map params)
    void asynchttpPost(Map params, Map data)
    void asynchttpPost(Map params)

    void asynchttpPut(String callbackMethod, Map params, Map data)
    void asynchttpPut(String callbackMethod, Map params)
    void asynchttpPut(Map params, Map data)
    void asynchttpPut(Map params)

    void asynchttpDelete(String callbackMethod, Map params, Map data)
    void asynchttpDelete(String callbackMethod, Map params)
    void asynchttpDelete(Map params, Map data)
    void asynchttpDelete(Map params)

    void asynchttpPatch(String callbackMethod, Map params, Map data)
    void asynchttpPatch(String callbackMethod, Map params)
    void asynchttpPatch(Map params, Map data)
    void asynchttpPatch(Map params)

    void asynchttpHead(String callbackMethod, Map params, Map data)
    void asynchttpHead(String callbackMethod, Map params)
    void asynchttpHead(Map params, Map data)
    void asynchttpHead(Map params)

    Map textToSpeech(String stringToBeSynthesized, String voice)
    Map textToSpeech(String stringToBeSynthesized)

    String encrypt(String value)

    String decrypt(String value)

    /**
     * Sends a LOCATION Event constructed from the specified properties.
     * See the Event reference for a list of available properties.
     * Other Apps can receive Location Events by subscribing to the Location.
     * Examples of existing Location Events include sunrise and sunset.
     * @param properties. Supported keys:
     * name (required) 	String - The name of the Event. Typically corresponds to an attribute name of a capability.
     * value (required) 	The value of the Event. The value is stored as a string, but you can pass numbers or other objects.
     * descriptionText 	String - The description of this Event. This appears in the mobile application activity for the device. If not specified, this will be created using the Event name and value.
     * displayed 	Pass true to display this Event in the mobile application activity feed, false to not display. Defaults to true.
     * linkText 	String - Name of the Event to show in the mobile application activity feed.
     * isStateChange 	true if this Event caused a device attribute to change state. Typically not used, since it will be set automatically.
     * unit 	String - a unit string, if desired. This will be used to create the descriptionText if it (the descriptionText option) is not specified.
     * data 	A map of additional information to store with the Event
     */
    void sendLocationEvent(Map properties)

    void httpPostJson(String uri, String body, Closure closure)

    void httpPostJson(String uri, Map body, Closure closure)

    void httpPostJson(Map params, Closure closure)

    Date toDateTime(String dateTimeString)


    /**
     * Return today's date object for given time.
     * @param timeString - Either an ISO-8601 date string as returned from time input preferences, or a simple time string in "hh:mm" format (“21:34”).
     * @param timeZone - current time zone. Please use it.
     * @note most likely some date calculations are incorrect in some cases, but this is meant mostly for testing.
     */
    Date timeToday(String timeString, TimeZone timeZone)
    Date timeToday(String timeString)

    // ST has this, but HE does not?
    // TimeZone timeZone(String timePreferenceString)

    /**
     * Deletes all scheduled jobs for the App.
     * If using the optional method parameter, then it deletes the scheduled job for the specified handler name only.
     * @param method - optional specific method to unschedule
     */
    void unschedule(String method)
    void unschedule()


    List getChildDevices(boolean includeVirtualDevices)
    List getChildDevices()
}


