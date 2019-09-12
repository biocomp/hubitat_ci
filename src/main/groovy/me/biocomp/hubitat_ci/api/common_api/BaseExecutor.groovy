package me.biocomp.hubitat_ci.api.common_api


import groovy.util.slurpersupport.GPathResult

/**
 * Methods that can be used inside both App or Driver.
 * */
trait BaseExecutor implements BaseAsyncHttp, BaseScheduler, BaseHttp
{
    /**
     * @return log object
     */
    abstract Log getLog()

    abstract Location getLocation()

    abstract List getLocationVariableNames()
    abstract List getLocationVariableValues(String variableName)

    /**
     * @return "C" or "F"
     */
    abstract String getTemperatureScale()

    /**
     * @return current Unix time in milliseconds.
     */
    abstract long now()
    
    /**
     * @param minutes
     * @return milliseconds in given minutes
     */
    abstract Long timeOffset(Number minutes)

    /**
     * @param hoursAndMinutesString - A string in the format of "hh:mm" to get the offset in milliseconds for.
     *                              Negative offsets are specified by prefixing the string with a minus sign ("-02:30").
     * @return milliseconds in given hours + minutes
     */
    abstract Long timeOffset(String hoursAndMinutesString)

    /**
     * Returns a Date of the next occurrence of the time specified in the input, relative to a reference time.
     *
     * @param referenceTime. Can be an ISO-8601 date string as returned from time input preferences,
     *                       or a simple time string in "hh:mm" format ("21:34").
     * @param timeString     - The time string whose next occurrence is queried.
     *                       Can be an ISO-8601 date string as returned from time input preferences
     *                       , or a simple time string in "hh:mm" format ("21:34").
     * @param timeZone       for date calculations. Please provide it.
     * @return
     */
    abstract Date timeTodayAfter(String referenceTime, String timeString, TimeZone timeZone)

    abstract Date timeTodayAfter(String referenceTime, String timeString)

    /**
     * return true if value is between start and end
     */
    abstract boolean timeOfDayIsBetween(Date start, Date stop, Date value, TimeZone timeZone)

    abstract boolean timeOfDayIsBetween(Date start, Date stop, Date value)

    abstract BigDecimal celsiusToFahrenheit(BigDecimal val)

    abstract BigDecimal fahrenheitToCelsius(BigDecimal val)

    /**
     * @param supported options:
     *                  (not supported in Hubitat?) zipCode (String) - the zip code to use for determining the times.
     *                  If not specified then the coordinates of the Hub location are used.
     *                  (not supported in Hubitat?) locationString (Srtring) - any location string supported by the Weather Underground APIs.
     *                  If not specified then the coordinates of the Hub Location are used
     *                  sunriseOffset (String) - adjust the sunrise time by this amount.
     *                  See timeOffset() for supported formats
     *                  sunsetOffset (String) - adjust the sunset time by this amount.
     *                  See timeOffset() for supported formats
     * @return Map with String keys and Date values: [sunrise: Date, sunset: Date]
     */
    abstract Map getSunriseAndSunset(Map options)

    abstract Map getSunriseAndSunset()
    
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


    abstract void pauseExecution(Long milliseconds)

    abstract def textToSpeech(String stringToBeSynthesized, String voice = null)

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


    abstract Date toDateTime(String dateTimeString)


    /**
     * Return today's date object for given time.
     * @param timeString - Either an ISO-8601 date string as returned from time input preferences, or a simple time string in "hh:mm" format ("21:34").
     * @param timeZone - current time zone. Please use it.
     * @note most likely some date calculations are incorrect in some cases, but this is meant mostly for testing.
     */
    abstract Date timeToday(String timeString, TimeZone timeZone)

    abstract Date timeToday(String timeString)

    abstract def getTTSVoices()

    abstract def setValuesInAsyncResponseAndInvokeCallback(groovyx.net.http.HttpResponseDecorator decorator, Map options, String a)
    abstract def setValuesInAsyncResponseAndInvokeCallback(String a, Map b, String c)
    abstract String getJWTtoken(String a, String b)
    abstract String getJWTtoken(String a, String b,String c)
    abstract void removeLocationVariable(String name)

    abstract void sendPush(String message)
    abstract void sendPush(String a,String b)
    abstract void sendPushMessage(String message)
    abstract void sendPushMessage(String a, String b)

    abstract void createLocationVariable(String name, List a = null, boolean b = false)
}


