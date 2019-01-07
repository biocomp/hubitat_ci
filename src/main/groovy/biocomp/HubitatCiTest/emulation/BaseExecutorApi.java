package biocomp.hubitatCiTest.emulation;

import biocomp.hubitatCiTest.util.Log;
import biocomp.hubitatCiTest.util.Utility;
import groovy.lang.Closure;
import groovy.lang.MetaMethod;
import groovy.util.slurpersupport.GPathResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

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

interface BaseHttpApi
{
    default Object httpGet(String address, Closure handler) { return null; }
    default Object httpGet(Map options, Closure handler) { return null; }

    default void httpPost(Map options, Closure handler) {}
    default void httpPost(String address, String request, Closure handler) {}

    default void httpPostJson(Map options, Closure handler) {}
    default void httpPostJson(String uri, String body, Closure handler) {}
    default void httpPostJson(String uri, Map options, Closure handler) {}

    default void httpPutJson(String uri, String body, Closure closure) {}
    default void httpPutJson(String uri, Map body, Closure closure) {}
    default void httpPutJson(Map params, Closure closure) {}

    default void httpPut(Map uri, Closure handler) {}
    default void httpPut(String uri,String body, Closure handler) {}

    default void httpDelete(Map params, Closure closure) {}
}


interface BaseAsyncHttpApi
{
    // GET

    default void asynchttpGet(MetaMethod handlerMethod) {}
    default void asynchttpGet(Map options) {}
    default void asynchttpGet(MetaMethod handlerMethod, Map options) {}
    default void asynchttpGet(String handlerMethod, Map options) {}

    /**
     * Send an http GET request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpGet(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpGet(String handlerMethod, Map options, Map data) {}


    // POST

    default void asynchttpPost(MetaMethod handlerMethod) {}
    default void asynchttpPost(Map options) {}
    default void asynchttpPost(MetaMethod handlerMethod, Map options) {}
    default void asynchttpPost(String handlerMethod, Map options) {}

    /**
     * Send an http POST request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpPost(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpPost(String handlerMethod, Map options, Map data) {}


    // PUT

    default void asynchttpPut(MetaMethod handlerMethod) {}
    default void asynchttpPut(Map options) {}
    default void asynchttpPut(MetaMethod handlerMethod, Map options) {}
    default void asynchttpPut(String handlerMethod, Map options) {}

    /**
     * Send an http PUT request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpPut(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpPut(String handlerMethod, Map options, Map data) {}


    // DELETE

    default void asynchttpDelete(MetaMethod handlerMethod) {}
    default void asynchttpDelete(Map options) {}
    default void asynchttpDelete(MetaMethod handlerMethod, Map options) {}
    default void asynchttpDelete(String handlerMethod, Map options) {}

    /**
     * Send an http DELETE request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpDelete(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpDelete(String handlerMethod, Map options, Map data) {}


    // PATCH

    default void asynchttpPatch(MetaMethod handlerMethod) {}
    default void asynchttpPatch(Map options) {}
    default void asynchttpPatch(MetaMethod handlerMethod, Map options) {}
    default void asynchttpPatch(String handlerMethod, Map options) {}

    /**
     * Send an http PATCH request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    default void asynchttpPatch(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpPatch(String handlerMethod, Map options, Map data) {}


    // HEAD

    // Not present? default void asynchttpHead(MetaMethod handlerMethod) {}
    default void asynchttpHead(Map options) {}
    // Not present? default void asynchttpHead(MetaMethod handlerMethod, Map options) {}
    default void asynchttpHead(String handlerMethod, Map options) {}

    /**
     * Send an http DELETE request and return control to the calling code. Any response from the call will be passed to the callback method.
     *
     * @param handlerMethod The name of a callback method to send the response to.
     *                      Can be null if the response can be ignored.
     * @param options - the parameters to use to build the http GET call.
     * @param data - optional data to be passed to the callback method.
     */
    // Not present? default void asynchttpHead(MetaMethod handlerMethod, Map options, Map data) {}
    default void asynchttpHead(String handlerMethod, Map options, Map data) {}
}

interface BaseSchedulerApi
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
    default void unschedule(groovy.lang.MetaMethod method) {}

    /**
     * Deletes scheduled job for the App.
     * @param method - method to unschedule
     */
    default void unschedule(java.lang.String method) {}
}

/**
 * Methods that can be used inside both App or Driver.
 * */
interface BaseExecutorApi extends BaseAsyncHttpApi, BaseHttpApi, BaseSchedulerApi
{
    /**
     * @return log object
     */
    default Log getLog() { return null; }

    default Location getLocation() { return null; }

    /**
     * @return "C" or "F"
     */
    default String getTemperatureScale() { return null; }

    /**
     * @return current Unix time in milliseconds.
     */
    default long now() { return 0; }

    /**
     * return true if value is between start and end
     */
    default boolean timeOfDayIsBetween(Date start, Date stop, Date value, TimeZone timeZone) {
        return Utility.timeOfDayIsBetween(start, stop, value, timeZone);
    }

    default boolean timeOfDayIsBetween(Date start, Date stop, Date value) {
        return timeOfDayIsBetween(start, stop, null);
    }

    default BigDecimal celsiusToFahrenheit(BigDecimal val) { return BigDecimal.ZERO; }

    default BigDecimal fahrenheitToCelsius(BigDecimal val) { return BigDecimal.ZERO; }


    default String getMACFromIP(String ipAddr) { return null; }

    default String convertTemperatureIfNeeded(BigDecimal value, String scale, Integer precision) { return null; }

    default Object parseJson(String stringToParse) { return null; }

    default GPathResult parseXML(String stringToParse) { return null; }

    /**
     * Parses a Base64-encoded LAN message received from the Hub into a map with header and body elements,
     * as well as parsing the body into an XML document.
     * @param stringToParse
     * @return map with:
     *  header (String) - the headers of the request as a single string
     *  headers (Map) - a Map of string/name value pairs for each header
     *  body (String) the request body as a string
     */
    default Map parseLanMessage(String stringToParse) { return null; }


    default void pauseExecution(Long milliseconds) {}

    default Map textToSpeech(String stringToBeSynthesized, String voice) { return null; }
    default Map textToSpeech(String stringToBeSynthesized) { return textToSpeech(stringToBeSynthesized, null); }

    default String encrypt(String value) { return null; }
    default String decrypt(String value) { return null; }

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
    default void sendLocationEvent(Map properties) {}


    default Date toDateTime(String dateTimeString) { return null; }


    /**
     * Return today's date object for given time.
     * @param timeString - Either an ISO-8601 date string as returned from time input preferences, or a simple time string in "hh:mm" format (“21:34”).
     * @param timeZone - current time zone. Please use it.
     * @note most likely some date calculations are incorrect in some cases, but this is meant mostly for testing.
     */
    default Date timeToday(String timeString, TimeZone timeZone) {
        return Utility.timeToday(timeString, timeZone);
    }

    default Date timeToday(String timeString) { return timeToday(timeString, null); }

    // ST has this, but HE does not?
    // TimeZone timeZone(String timePreferenceString)
    //default List getChildDevices(boolean includeVirtualDevices = false) { return null; }

    /**
     * @param map - string of format "key1: value1, key2: value2"
     */
    //default Map stringToMap(String map) { return null; }
}


