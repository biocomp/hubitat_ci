package me.biocomp.hubitat_ci.api.hub.executor

trait BaseExecutor {
    abstract void asynchttpDelete(groovy.lang.MetaMethod a)
    abstract void asynchttpDelete(java.util.Map a)
    abstract void asynchttpDelete(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void asynchttpDelete(java.lang.String a, java.util.Map b)
    abstract void asynchttpDelete(groovy.lang.MetaMethod a, java.util.Map b, java.util.Map c)
    abstract void asynchttpDelete(java.lang.String a, java.util.Map b, java.util.Map c)
    abstract void asynchttpGet(groovy.lang.MetaMethod a)
    abstract void asynchttpGet(java.util.Map a)
    abstract void asynchttpGet(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void asynchttpGet(java.lang.String a, java.util.Map b)
    abstract void asynchttpGet(groovy.lang.MetaMethod a, java.util.Map b, java.util.Map c)
    abstract void asynchttpGet(java.lang.String a, java.util.Map b, java.util.Map c)
    abstract void asynchttpHead(java.util.Map a)
    abstract void asynchttpHead(java.lang.String a, java.util.Map b)
    abstract void asynchttpHead(java.lang.String a, java.util.Map b, java.util.Map c)
    abstract void asynchttpPatch(groovy.lang.MetaMethod a)
    abstract void asynchttpPatch(java.util.Map a)
    abstract void asynchttpPatch(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void asynchttpPatch(java.lang.String a, java.util.Map b)
    abstract void asynchttpPatch(groovy.lang.MetaMethod a, java.util.Map b, java.util.Map c)
    abstract void asynchttpPatch(java.lang.String a, java.util.Map b, java.util.Map c)
    abstract void asynchttpPost(groovy.lang.MetaMethod a)
    abstract void asynchttpPost(java.util.Map a)
    abstract void asynchttpPost(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void asynchttpPost(java.lang.String a, java.util.Map b)
    abstract void asynchttpPost(groovy.lang.MetaMethod a, java.util.Map b, java.util.Map c)
    abstract void asynchttpPost(java.lang.String a, java.util.Map b, java.util.Map c)
    abstract void asynchttpPut(groovy.lang.MetaMethod a)
    abstract void asynchttpPut(java.util.Map a)
    abstract void asynchttpPut(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void asynchttpPut(java.lang.String a, java.util.Map b)
    abstract void asynchttpPut(groovy.lang.MetaMethod a, java.util.Map b, java.util.Map c)
    abstract void asynchttpPut(java.lang.String a, java.util.Map b, java.util.Map c)
    abstract java.math.BigDecimal celsiusToFahrenheit(java.math.BigDecimal a)
    abstract java.lang.String convertTemperatureIfNeeded(java.math.BigDecimal a, java.lang.String b, java.lang.Integer c)
    abstract void createLocationVariable(java.lang.String a)
    abstract void createLocationVariable(java.lang.String a, java.util.List b)
    abstract void createLocationVariable(java.lang.String a, java.util.List b, boolean c)
    abstract java.lang.String decrypt(java.lang.String a)
    abstract java.lang.String encrypt(java.lang.String a)
    abstract java.math.BigDecimal fahrenheitToCelsius(java.math.BigDecimal a)
    abstract java.util.Map getAllEntitlements()
    abstract java.lang.Integer getEntitlementQuantity(java.lang.String a)
    abstract java.lang.String getExceptionMessageWithLine(java.lang.Throwable a)
    abstract java.lang.String getHubAccountHash()
    abstract java.lang.String getJWTtoken(java.lang.String a, java.lang.String b)
    abstract java.lang.String getJWTtoken(java.lang.String a, java.lang.String b, java.lang.String c)
    abstract me.biocomp.hubitat_ci.api.common_api.Location getLocation()
    abstract java.util.List getLocationVariableNames()
    abstract java.util.List getLocationVariableValues(java.lang.String a)
    abstract me.biocomp.hubitat_ci.api.common_api.Log getLog()
    abstract java.lang.String getMACFromIP(java.lang.String a)
    abstract java.lang.String getMacAddress()
    abstract java.lang.String getStackTrace(java.lang.Throwable a)
    abstract java.util.Map getSunriseAndSunset()
    abstract java.util.Map getSunriseAndSunset(java.util.Map a)
    abstract java.lang.Object getTTSVoices()
    abstract java.lang.String getTemperatureScale()
    abstract java.util.Date getTodaysSunrise()
    abstract java.util.Date getTodaysSunset()
    abstract java.util.Date getTomorrowsSunrise()
    abstract java.util.Date getTomorrowsSunset()
    abstract java.lang.String getZWaveDeviceJoinName(java.lang.Integer a, java.lang.Integer b, java.lang.Integer c)
    abstract java.lang.String getZWaveDeviceJoinName(java.lang.String a, java.lang.String b, java.lang.String c)
    abstract java.lang.String getZigbeeDeviceJoinName(java.lang.String a, java.lang.String b)
    abstract java.lang.Boolean hasEntitlement(java.lang.String a)
    abstract void httpDelete(java.util.Map a, groovy.lang.Closure b)
    abstract java.lang.Object httpGet(java.lang.String a, groovy.lang.Closure b)
    abstract java.lang.Object httpGet(java.util.Map a, groovy.lang.Closure b)
    abstract void httpPatch(java.util.Map a, groovy.lang.Closure b)
    abstract void httpPatch(java.lang.String a, java.lang.String b, groovy.lang.Closure c)
    abstract void httpPost(java.util.Map a, groovy.lang.Closure b)
    abstract void httpPost(java.lang.String a, java.lang.String b, groovy.lang.Closure c)
    abstract void httpPostJson(java.util.Map a, groovy.lang.Closure b)
    abstract void httpPostJson(java.lang.String a, java.lang.String b, groovy.lang.Closure c)
    abstract void httpPostJson(java.lang.String a, java.util.Map b, groovy.lang.Closure c)
    abstract void httpPut(java.util.Map a, groovy.lang.Closure b)
    abstract void httpPut(java.lang.String a, java.lang.String b, groovy.lang.Closure c)
    abstract void httpPutJson(java.util.Map a, groovy.lang.Closure b)
    abstract void httpPutJson(java.lang.String a, java.lang.String b, groovy.lang.Closure c)
    abstract void httpPutJson(java.lang.String a, java.util.Map b, groovy.lang.Closure c)
    abstract long now()
    abstract java.lang.Object parseJson(java.lang.String a)
    abstract java.lang.Object parseJsonFromBase64(java.lang.String a)
    abstract java.util.Map parseLanMessage(java.lang.String a)
    abstract groovy.util.slurpersupport.GPathResult parseXML(java.lang.String a)
    abstract boolean ping(java.lang.String a, int b)
    abstract void removeLocationVariable(java.lang.String a)
    abstract void runEvery10Minutes(groovy.lang.MetaMethod a)
    abstract void runEvery10Minutes(java.lang.String a)
    abstract void runEvery10Minutes(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void runEvery10Minutes(java.lang.String a, java.util.Map b)
    abstract void runEvery15Minutes(groovy.lang.MetaMethod a)
    abstract void runEvery15Minutes(java.lang.String a)
    abstract void runEvery15Minutes(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void runEvery15Minutes(java.lang.String a, java.util.Map b)
    abstract void runEvery1Hour(groovy.lang.MetaMethod a)
    abstract void runEvery1Hour(java.lang.String a)
    abstract void runEvery1Hour(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void runEvery1Hour(java.lang.String a, java.util.Map b)
    abstract void runEvery1Minute(groovy.lang.MetaMethod a)
    abstract void runEvery1Minute(java.lang.String a)
    abstract void runEvery1Minute(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void runEvery1Minute(java.lang.String a, java.util.Map b)
    abstract void runEvery30Minutes(groovy.lang.MetaMethod a)
    abstract void runEvery30Minutes(java.lang.String a)
    abstract void runEvery30Minutes(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void runEvery30Minutes(java.lang.String a, java.util.Map b)
    abstract void runEvery3Hours(groovy.lang.MetaMethod a)
    abstract void runEvery3Hours(java.lang.String a)
    abstract void runEvery3Hours(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void runEvery3Hours(java.lang.String a, java.util.Map b)
    abstract void runEvery5Minutes(groovy.lang.MetaMethod a)
    abstract void runEvery5Minutes(java.lang.String a)
    abstract void runEvery5Minutes(groovy.lang.MetaMethod a, java.util.Map b)
    abstract void runEvery5Minutes(java.lang.String a, java.util.Map b)
    abstract void runIn(java.lang.Long a, groovy.lang.MetaMethod b)
    abstract void runIn(java.lang.Long a, java.lang.String b)
    abstract void runIn(java.lang.Long a, groovy.lang.MetaMethod b, java.util.Map c)
    abstract void runIn(java.lang.Long a, java.lang.String b, java.util.Map c)
    abstract void runInMillis(java.lang.Long a, groovy.lang.MetaMethod b)
    abstract void runInMillis(java.lang.Long a, java.lang.String b)
    abstract void runInMillis(java.lang.Long a, groovy.lang.MetaMethod b, java.util.Map c)
    abstract void runInMillis(java.lang.Long a, java.lang.String b, java.util.Map c)
    abstract void runOnce(java.lang.String a, groovy.lang.MetaMethod b)
    abstract void runOnce(java.lang.String a, java.lang.String b)
    abstract void runOnce(java.util.Date a, groovy.lang.MetaMethod b)
    abstract void runOnce(java.util.Date a, java.lang.String b)
    abstract void runOnce(java.lang.String a, groovy.lang.MetaMethod b, java.util.Map c)
    abstract void runOnce(java.lang.String a, java.lang.String b, java.util.Map c)
    abstract void runOnce(java.util.Date a, groovy.lang.MetaMethod b, java.util.Map c)
    abstract void runOnce(java.util.Date a, java.lang.String b, java.util.Map c)
    abstract void schedule(java.lang.String a, groovy.lang.MetaMethod b)
    abstract void schedule(java.lang.String a, java.lang.String b)
    abstract void schedule(java.util.Date a, groovy.lang.MetaMethod b)
    abstract void schedule(java.util.Date a, java.lang.String b)
    abstract void schedule(java.lang.String a, groovy.lang.MetaMethod b, java.util.Map c)
    abstract void schedule(java.lang.String a, java.lang.String b, java.util.Map c)
    abstract void schedule(java.util.Date a, groovy.lang.MetaMethod b, java.util.Map c)
    abstract void schedule(java.util.Date a, java.lang.String b, java.util.Map c)
    abstract void sendLocationEvent(java.util.Map a)
    abstract void sendPush(java.lang.String a)
    abstract void sendPush(java.lang.String a, java.lang.String b)
    abstract void sendPushMessage(java.lang.String a)
    abstract void sendPushMessage(java.lang.String a, java.lang.String b)
    abstract java.lang.Object setValuesInAsyncResponseAndInvokeCallback(groovyx.net.http.HttpResponseDecorator a, java.util.Map b, java.lang.String c)
    abstract java.lang.Object setValuesInAsyncResponseAndInvokeCallback(java.lang.String a, java.util.Map b, java.lang.String c)
    abstract java.lang.Object textToSpeech(java.lang.String a)
    abstract java.lang.Object textToSpeech(java.lang.String a, java.lang.String b)
    abstract boolean timeOfDayIsBetween(java.util.Date a, java.util.Date b, java.util.Date c)
    abstract boolean timeOfDayIsBetween(java.util.Date a, java.util.Date b, java.util.Date c, java.util.TimeZone d)
    abstract java.lang.Long timeOffset(java.lang.Number a)
    abstract java.lang.Long timeOffset(java.lang.String a)
    abstract java.util.Date timeToday(java.lang.String a)
    abstract java.util.Date timeToday(java.lang.String a, java.util.TimeZone b)
    abstract java.util.Date timeTodayAfter(java.lang.String a, java.lang.String b)
    abstract java.util.Date timeTodayAfter(java.lang.String a, java.lang.String b, java.util.TimeZone c)
    abstract java.util.Date toDateTime(java.lang.String a)
    abstract void unschedule()
    abstract void unschedule(groovy.lang.MetaMethod a)
    abstract void unschedule(java.lang.String a)
}