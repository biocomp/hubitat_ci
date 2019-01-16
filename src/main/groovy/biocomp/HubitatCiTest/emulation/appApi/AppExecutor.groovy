package biocomp.hubitatCiTest.emulation.appApi;

/*
Methods from reflected real code:
Methods:
 * # All derived from BaseExecutor skipped #

Meta/Common:

Main methods/getters:
++ public com.hubitat.appApi.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.addChildApp(java.lang.String,java
.lang.String,java.lang.String),
++ public com.hubitat.appApi.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.addChildApp(java.lang.String,java
.lang.String,java.lang.String,java.util.Map),
++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java
.lang.String,java.lang.String),
++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java
.lang.String,java.lang.String,java.lang.Long),
++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java
.lang.String,java.lang.String,java.lang.Long,java.util.Map),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.apiServerUrl(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.appApi(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.appApi(java.lang.String,java.lang.String,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.appApi(java.util.Map,java.lang.String,java.lang.String,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.appSetting(java.lang.String),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.createAccessToken(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.definition(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.definition(java.util.Map,groovy.lang.Closure),
++ public void com.hubitat.hub.executor.AppExecutor.deleteChildApp(java.lang.Long),
++ public void com.hubitat.hub.executor.AppExecutor.deleteChildDevice(java.lang.String),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.dynamicPage(java.util.Map,groovy.lang.Closure),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.fullApiServerUrl(java.lang.String),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.fullLocalApiServerUrl(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getAllChildApps(),
++ public java.util.List com.hubitat.hub.executor.AppExecutor.getAllChildDevices(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getAllDeviceIds(),
public com.hubitat.appApi.DeviceWrapperList com.hubitat.hub.executor.AppExecutor.getAllDevicesByCapability(java.lang.String),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.getApiServerUrl(),
public com.hubitat.appApi.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.getApp(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getAppMappings(),
public com.hubitat.hub.domain.AppType com.hubitat.hub.executor.AppExecutor.getAppType(),
public java.lang.String com.hubitat.hub.executor.AppExecutor.getAppTypeType(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildAppById(java.lang.Long),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildAppByLabel(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildApps(),
++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.getChildDevice(java.lang.String),
public java.util.List com.hubitat.hub.executor.AppExecutor.getChildDevices(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getCurrentPage(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getCurrentSection(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getDashboard(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getDashboardEventsMap(java.util.List,java.lang.Integer),
public com.hubitat.appApi.DeviceWrapper com.hubitat.hub.executor.AppExecutor.getDeviceById(java.lang.Long),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.getEXECUTOR_TYPE(),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.getFullApiServerUrl(),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.getFullLocalApiServerUrl(),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.getHubUID(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getInstalledCapabilities(),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.getLocalApiServerUrl(),
++ public java.util.List com.hubitat.hub.executor.AppExecutor.getLocationEventsSince(java.lang.String,java.util.Date),
++ public java.util.List com.hubitat.hub.executor.AppExecutor.getLocationEventsSince(java.lang.String,java.util.Date,
java.util.Map),
public java.util.List com.hubitat.hub.executor.AppExecutor.getPages(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getParent(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getPreferences(),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.getState(),
++ public com.hubitat.appApi.DeviceWrapper com.hubitat.hub.executor.AppExecutor.getSubscribedDeviceById(java.lang.Long),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.getSunriseAndSunset(),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.getSunriseAndSunset(java.util.Map),
public java.util.List com.hubitat.hub.executor.AppExecutor.getThirdPartyHubIPList(),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.util.Map,java.lang.String),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.lang.String,java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.util.Map,java.lang.String,java.lang.String),

public boolean com.hubitat.hub.executor.AppExecutor.isAppInstalled(java.lang.String,java.lang.String),
public boolean com.hubitat.hub.executor.AppExecutor.isAppInstalled(java.lang.String,java.lang.String,java.lang.String),
public boolean com.hubitat.hub.executor.AppExecutor.isSystemTypeOrHubDeveloper(),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.label(java.util.Map),

++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.localApiServerUrl(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.mappings(groovy.lang.Closure),
public void com.hubitat.hub.executor.AppExecutor.metadata(groovy.lang.Closure),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.mode(java.util.Map),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.lang.String,java.lang.String,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map,java.lang.String,java.lang.String,groovy.lang.Closure),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.paragraph(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.paragraph(java.util.Map,java.lang.String),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.path(java.lang.String,groovy.lang.Closure),

++ public void com.hubitat.hub.executor.AppExecutor.pause(java.lang.Long),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.preferences(groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.preferences(java.util.Map,groovy.lang.Closure),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.render(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.render(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.saveState(),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.lang.String,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.util.Map,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.util.Map,java.lang.String,groovy.lang.Closure),

++ public void com.hubitat.hub.executor.AppExecutor.sendEvent(java.util.Map),
++ public static void com.hubitat.hub.executor.AppExecutor.sendEvent(com.hubitat.appApi.DeviceWrapper,java.util.Map),
++ public static void com.hubitat.hub.executor.AppExecutor.sendEvent(java.lang.String,java.util.Map),
++ public void com.hubitat.hub.executor.AppExecutor.sendSms(java.lang.String,java.lang.String),
++ public void com.hubitat.hub.executor.AppExecutor.sendSmsMessage(java.lang.String,java.lang.String),

++ public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.Object),
++ public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.String,java.lang.Object),
++ public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.String,java.lang.Object,java
.util.Map),

++ public static java.lang.Long com.hubitat.hub.executor.AppExecutor.timeOffset(java.lang.Number),
++ public static java.lang.Long com.hubitat.hub.executor.AppExecutor.timeOffset(java.lang.String),
++ public static java.util.Date com.hubitat.hub.executor.AppExecutor.timeTodayAfter(java.lang.String,java.lang.String),
++ public static java.util.Date com.hubitat.hub.executor.AppExecutor.timeTodayAfter(java.lang.String,java.lang.String,
java.util.TimeZone),

++ public void com.hubitat.hub.executor.AppExecutor.unsubscribe(),
++ public void com.hubitat.hub.executor.AppExecutor.unsubscribe(com.hubitat.appApi.DeviceWrapper),
++ public void com.hubitat.hub.executor.AppExecutor.unsubscribe(java.util.List)


Setters:
public void com.hubitat.hub.executor.AppExecutor.setApp(com.hubitat.appApi.InstalledAppWrapper),
public void com.hubitat.hub.executor.AppExecutor.setAppMappings(java.lang.Object),
public void com.hubitat.hub.executor.AppExecutor.setAppType(com.hubitat.hub.domain.AppType),
public void com.hubitat.hub.executor.AppExecutor.setAppTypeType(java.lang.String),
public void com.hubitat.hub.executor.AppExecutor.setChildApps(java.util.List),
public void com.hubitat.hub.executor.AppExecutor.setCurrentPage(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setCurrentSection(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setLocationMode(java.lang.String),
public void com.hubitat.hub.executor.AppExecutor.setPages(java.util.List),
public void com.hubitat.hub.executor.AppExecutor.setPreferences(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setState(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setThirdPartyHubIPList(java.util.List),
*/

import biocomp.hubitatCiTest.emulation.commonApi.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

;


/**
 * Most of operations available inside an App.
 *
 * @note about using default implementations for methods - needed to be able
 * to implement just a few of methods for preferences validation,
 * but AutoImplement had some strange issues (like implementing getMetaClass() that was returning null always, and was crashing at runtime).
 * So the easiest way to go was to have default implementations right here.
 */
interface AppExecutor extends
        BaseExecutor,
        PreferencesSource,
        MappingsSource, Mappings,
        DefinitionReader,
        Subscription {
    // Returns list of devices or one device from config
    // Device/List<Device> get<device or capability preference name>()

    // Returns Number or Decimal config value
    // BigDecimal get<number or decimal preference name>()

    // Return value of config property.
    // If it's text, returns: String - the value entered as text
    // If it's mode, returns: String - the name of the mode selected
    // If it's time, returns: String - the full date string in the format of "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    // String get<text, mode, or time preference name>()

    /**
     * @throws IllegalArgumentException   - If a label was not supplied
     * @throws NotFoundException          - If the given SmartApp name was not found in the given Namespace.
     * @throws SizeLimitExceededException - If this SmartApp already has the maximum number of children allowed (500).
     */
    abstract InstalledAppWrapper addChildApp(String namespace, String smartAppVersionName, String label, Map
            properties)

    /**
     * @throws IllegalArgumentException   - If a label was not supplied
     * @throws NotFoundException          - If the given SmartApp name was not found in the given Namespace.
     * @throws SizeLimitExceededException - If this SmartApp already has the maximum number of children allowed (500).
     */
    abstract InstalledAppWrapper addChildApp(String namespace, String smartAppVersionName, String label)

    /**
     * @return Returns the URL of the server where this App can be reached for API calls,
     * along with the specified path appended to it.
     * Use this instead of hard-coding a URL to ensure that the correct server URL
     * for this installed instance is returned.
     */
    abstract String apiServerUrl(String path)

    /**
     * @return A map of name/value pairs that App can use to save and retrieve
     * data across App executions.
     * This is similar to getState(), but will immediately write and read from the backing data store.
     * Prefer using getState() over getAtomicState() when possible.
     */
    abstract Map getAtomicState()

    /**
     * @return A map of name/value pairs that App can use to save and retrieve data across App executions.
     * @note Though state can be treated as a map in most regards, certain convenience
     * operations that you may be accustomed to in maps will not work with state.
     * For example, state.count++ will not increment the count.
     * Use the longer form of state.count = state.count + 1.
     */
    abstract Map getState()

    /**
     * Creates access token and puts into state.accessToken
     *
     * @return may return access token here too.
     */
    abstract String createAccessToken()
//
//    List<InstalledAppWrapper> findAllChildAppsByName(String name) {
//        return null;
//    }
//
//    List<InstalledAppWrapper> findAllChildAppsByNamespaceAndName(String namespace, String name) {
//        return null;
//    }
//
//    /**
//     * @return found appApi (if many are matching, first one is returned) or return null;.
//     */
//    InstalledAppWrapper findChildAppByName(String appName) {
//        return null;
//    }
//
//    /**
//     * @return found appApi (if many are matching, first one is returned) or return null;.
//     */
//    InstalledAppWrapper findChildAppByNamespaceAndName(String namespace, String appName) {
//        return null;
//    }

    /**
     * @return all child apps, even if installation state is "incomplete"
     */
    abstract Object getAllChildApps()

    /**
     * @return getChildDevices()
     */
    abstract List getAllChildDevices()

    abstract String getApiServerUrl()
//
//    ColorUtilities getColorUtil() {
//        return null;
//    }


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

    abstract void pauseExecution(Long millisecs)
    /**
     * @return only child apps whose state is "complete"
     */
    abstract Object getChildApps()

    /**
     * Creates App event with specified properties.
     *
     * @param properties. Supported keys:
     *                    name (required) (String) - The name of the Event. Typically corresponds to an attribute name of a capability.
     *                    value (required) The value of the Event. The value is stored as a string, but you can pass numbers or other objects.
     *                    descriptionText (String) - The description of this Event. This appears in the mobile application activity for the device. If not specified, this will be created using the Event name and value.
     *                    displayed (boolean) - Pass true to display this Event in the mobile application activity feed, false to not display. Defaults to true.
     *                    linkText (String) - Name of the Event to show in the mobile application activity feed.
     *                    isStateChange (boolean) - true if this Event caused a device attribute to change state. Typically not used, since it will be set automatically.
     *                    unit (String) - a unit string, if desired. This will be used to create the descriptionText if it (the descriptionText option) is not specified.
     *                    device (Device) - The device for which this Event is created for.
     *                    data (Map) A map of additional information to store with the Event
     */
    abstract void sendEvent(Map properties)

    /**
     * Creates Device event with specified properties.
     *
     * @param properties. Supported keys:
     *                    name (required) (String) - The name of the Event. Typically corresponds to an attribute name of a capability.
     *                    value (required) The value of the Event. The value is stored as a string, but you can pass numbers or other objects.
     *                    descriptionText (String) - The description of this Event. This appears in the mobile application activity for the device. If not specified, this will be created using the Event name and value.
     *                    displayed (boolean) - Pass true to display this Event in the mobile application activity feed, false to not display. Defaults to true.
     *                    linkText (String) - Name of the Event to show in the mobile application activity feed.
     *                    isStateChange (boolean) - true if this Event caused a device attribute to change state. Typically not used, since it will be set automatically.
     *                    unit (String) - a unit string, if desired. This will be used to create the descriptionText if it (the descriptionText option) is not specified.
     *                    device (Device) - The device for which this Event is created for.
     *                    data (Map) A map of additional information to store with the Event
     */
    abstract void sendEvent(DeviceWrapper device, Map properties)

    /**
     * Creates Device event with specified properties.
     *
     * @param properties. Supported keys:
     *                    name (required) (String) - The name of the Event. Typically corresponds to an attribute name of a capability.
     *                    value (required) The value of the Event. The value is stored as a string, but you can pass numbers or other objects.
     *                    descriptionText (String) - The description of this Event. This appears in the mobile application activity for the device. If not specified, this will be created using the Event name and value.
     *                    displayed (boolean) - Pass true to display this Event in the mobile application activity feed, false to not display. Defaults to true.
     *                    linkText (String) - Name of the Event to show in the mobile application activity feed.
     *                    isStateChange (boolean) - true if this Event caused a device attribute to change state. Typically not used, since it will be set automatically.
     *                    unit (String) - a unit string, if desired. This will be used to create the descriptionText if it (the descriptionText option) is not specified.
     *                    device (Device) - The device for which this Event is created for.
     *                    data (Map) A map of additional information to store with the Event
     */
    abstract void sendEvent(String dni, Map properties)

    /**
     * Sends the message as an SMS message to the specified phone number and displays it in Hello, Home.
     *
     * @param message - no longer than 140 chars
     */
    abstract void sendSms(String phone, String message)

    /**
     * Sends the message as an SMS message to the specified phone number but does not display it in Hello, Home.
     *
     * @param message - no longer than 140 chars
     */
    abstract void sendSmsMessage(String phone, String message)

    /**
     * Set the Mode for this Location.
     */
    abstract void setLocationMode(String mode)

    /**
     * @return configuration settings for the appApi.
     */
    abstract Map getSettings()


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


    abstract String fullApiServerUrl(String base)

    abstract String getFullApiServerUrl()

    abstract String getLocalApiServerUrl()

    abstract String getFullLocalApiServerUrl()

    abstract String localApiServerUrl(String url)

    abstract String fullLocalApiServerUrl(String url)

    /**
     * @throws UnknownDeviceTypeException - If a Device with the specified name and namespace is not found.
     * @throws IllegalArgumentException   - If the deviceNetworkId is not specified.
     * @throws SizeLimitExceededException - If this App already has the maximum number of children allowed (500).
     */
    abstract ChildDeviceWrapper addChildDevice(String namespace, String typeName, String deviceNetworkId, Long hubId, Map options)

    abstract ChildDeviceWrapper addChildDevice(String namespace, String typeName, String deviceNetworkId)

    abstract ChildDeviceWrapper addChildDevice(String namespace, String typeName, String deviceNetworkId, Long hubId)

    abstract void deleteChildApp(Long id)

    /**
     * @throws NotFoundException
     */
    abstract void deleteChildDevice(String deviceNetworkId)

    /**
     * @return most likely InstalledChildWrapper
     */
    abstract Object getChildAppById(Long childAppId)

    /**
     * @return most likely InstalledChildWrapper
     */
    abstract Object getChildAppByLabel(String childAppLabel)


    abstract ChildDeviceWrapper getChildDevice(String deviceNetworkId)

    /**
     * @return most likely List<ChildDeviceWrapper>
     */
    abstract List getChildDevices()

    abstract String getHubUID()

    /**
     * @return most likely, List of Event objects
     */
    abstract List getLocationEventsSince(String attributeName, Date startDate, Map options)

    /**
     * @return most likely, List of Event objects
     */
    abstract List getLocationEventsSince(String attributeName, Date startDate)

    /**
     * @return Likely, InstalledAppWrapper
     */
    abstract Object getParent()

    abstract DeviceWrapper getSubscribedDeviceById(Long deviceId)

    /**
     * Returns a HTTP response to the calling client with the options specified.
     *
     * @param options. Valid options:
     *                 contentType (String) The value of the "Content-Type" - request header. "application/json" if not specified.
     *                 status (?) The HTTP status of the response. 200 if not specified.
     *                 data (?) Required. The data for this response.
     * @return http response.
     */
    abstract Object render(Map options)

    abstract Object render()

    abstract void pause(Long milliseconds)

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
};