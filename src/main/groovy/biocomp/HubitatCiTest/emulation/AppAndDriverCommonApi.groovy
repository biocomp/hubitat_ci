package biocomp.hubitatCiTest.emulation

import groovy.util.slurpersupport.GPathResult

/**
 * Methods that can be used inside App or Driver, that are also implemented here for simplicity.
 */
trait CommonApiImpl
{
    /**
     * @param map - string of format "key1: value1, key2: value2"
     */
    Map stringToMap(String map)
    {
        def result = [:]
        map.split(',').each {
            def keyValue = it.split(':')
            if (keyValue.size() == 2) {
                result[keyValue[0].trim()] = keyValue[1].trim()
            }
        }

        return result
    }
}

/**
    Methods that can be used inside both App or Driver.
*/
trait AppAndDriverCommonApi extends CommonApiImpl
{
    abstract Location getLocation()

    /**
     * @return "C" or "F"
     */
    abstract String getTemperatureScale()

    /**
     * @return current Unix time in milliseconds.
     */
    abstract long now()

    /**
     * return true if value is between start and end
     */
    abstract boolean timeOfDayIsBetween(Date start, Date stop, Date value, TimeZone timeZone = null)

    abstract BigDecimal celsiusToFahrenheit(BigDecimal val)
    abstract BigDecimal fahrenheitToCelsius(BigDecimal val)

    abstract void httpGet(String uri, Closure closure)
    abstract void httpGet(Map params, Closure closure)
    abstract void httpPost(String uri, String body, Closure closure)
    abstract void httpPost(Map params, Closure closure)
    abstract void httpPutJson(String uri, String body, Closure closure)
    abstract void httpPutJson(String uri, Map body, Closure closure)
    abstract void httpPutJson(Map params, Closure closure)
    abstract void httpPut(String uri, String body, Closure closure)
    abstract void httpPut(Map params, Closure closure)
    abstract void httpDelete(Map params, Closure closure)

    abstract String getMACFromIP(String ipAddr)
    abstract String convertTemperatureIfNeeded(BigDecimal value, String scale, Integer precision)

    abstract Object parseJson(String stringToParse)
    abstract GPathResult parseXML(String stringToParse)

    /**
     * Parses a Base64-encoded LAN message received from the Hub into a map with header and body elements,
     * as well as parsing the body into an XML document.
     * @param stringToParse
     * @return map with:
     *  header (String) - the headers of the request as a single string
     *  headers (Map) - a Map of string/name value pairs for each header
     *  body (String) the request body as a string
     */
    abstract Map parseLanMessage(String stringToParse)


    abstract void pauseExecution(Long millisecs)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery1Minute(def handlerMethod, Map options = null)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery5Minutes(def handlerMethod, Map options = null)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery10Minutes(def handlerMethod, Map options = null)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery15Minutes(def handlerMethod, Map options = null)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery30Minutes(def handlerMethod, Map options = null)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery1Hour(def handlerMethod, Map options = null)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runEvery3Hours(def handlerMethod, Map options = null)

    /**
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runIn(Long delayInSeconds, def handlerMethod, Map options = null)


    abstract void runInMillis(Long delayInMilliSeconds, def handlerMethod, Map options = null)

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runOnce(Date dateTime, String handlerMethod, Map options = null)

    /**
     * Runs specified method at specified date/time.
     *
     * @param dateTime - ISO-8601 date string - when to run
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options map. Supported keys:
     *  overwrite (Boolean) - Specify [overwrite: false] to not overwrite any existing pending schedule handler for the given method (the default behavior is to overwrite the pending schedule). Specifying [overwrite: false] can lead to multiple different schedules for the same handler method, so be sure your handler method can handle this.
     *  data (Map) A map of data that will be passed to the handler method
     */
    abstract void runOnce(String dateTime, String handlerMethod, Map options = null)

    /**
     * Creates a scheduled job that calls the handlerMethod once per day at the time specified.
     * @param dateTime
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    abstract void schedule(Date dateTime, def handlerMethod, Map options = null)

    /**
     * Creates a scheduled job that calls the handlerMethod according to cronExpression, or once a day at specified time.
     * @param cronExpressionOrIsoDate
     *  See this for cron expressions: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
     * @param handlerMethod - could be method name (String) or reference to a method.
     * @param options. Supported keys:
     *  data (Map) - will be passed to handlerMethod
     */
    abstract void schedule(String cronExpressionOrIsoDate, def handlerMethod, Map options = null)

    abstract void asynchttpGet(String callbackMethod = null, Map params, Map data = null)
    abstract void asynchttpPost(String callbackMethod = null, Map params, Map data = null)
    abstract void asynchttpPut(String callbackMethod = null, Map params, Map data = null)
    abstract void asynchttpDelete(String callbackMethod = null, Map params, Map data = null)
    abstract void asynchttpPatch(String callbackMethod = null, Map params, Map data = null)
    abstract void asynchttpHead(String callbackMethod = null, Map params, Map data = null)

    abstract Map textToSpeech(String stringToBeSynthesized, String voice = null)

    abstract String encrypt(String value)
    abstract String decrypt(String value)

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
    abstract void sendLocationEvent(Map properties)

    abstract void httpPostJson(String uri, String body, Closure closure)
    abstract void httpPostJson(String uri, Map body, Closure closure)
    abstract void httpPostJson(Map params, Closure closure)

    abstract Date timeToday(String timeString, TimeZone timeZone = null)
    abstract Date toDateTime(String dateTimeString)
}


