package biocomp.hubitatCiTest.emulation

/*
Methods from reflected real code:
Methods:

 * # All derived from BaseExecutor skipped #
 * # AppExecutor #
public com.hubitat.app.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.addChildApp(java.lang.String,java.lang.String,java.lang.String),
public com.hubitat.app.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.addChildApp(java.lang.String,java.lang.String,java.lang.String,java.util.Map),
public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java.lang.String,java.lang.String),
public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java.lang.String,java.lang.String,java.lang.Long),
public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java.lang.String,java.lang.String,java.lang.Long,java.util.Map),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.apiServerUrl(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.app(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.app(java.lang.String,java.lang.String,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.app(java.util.Map,java.lang.String,java.lang.String,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.appSetting(java.lang.String),
public java.lang.String com.hubitat.hub.executor.AppExecutor.createAccessToken(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.definition(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.definition(java.util.Map,groovy.lang.Closure),
public void com.hubitat.hub.executor.AppExecutor.deleteChildApp(java.lang.Long),
public void com.hubitat.hub.executor.AppExecutor.deleteChildDevice(java.lang.String),
public java.util.Map com.hubitat.hub.executor.AppExecutor.dynamicPage(java.util.Map,groovy.lang.Closure),
public java.lang.String com.hubitat.hub.executor.AppExecutor.fullApiServerUrl(java.lang.String),
public java.lang.String com.hubitat.hub.executor.AppExecutor.fullLocalApiServerUrl(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getAllChildApps(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getAllChildDevices(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getAllDeviceIds(),
public com.hubitat.app.DeviceWrapperList com.hubitat.hub.executor.AppExecutor.getAllDevicesByCapability(java.lang.String),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.getApiServerUrl(),
public com.hubitat.app.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.getApp(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getAppMappings(),
public com.hubitat.hub.domain.AppType com.hubitat.hub.executor.AppExecutor.getAppType(),
public java.lang.String com.hubitat.hub.executor.AppExecutor.getAppTypeType(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildAppById(java.lang.Long),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildAppByLabel(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildApps(),
public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.getChildDevice(java.lang.String),
public java.util.List com.hubitat.hub.executor.AppExecutor.getChildDevices(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getCurrentPage(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getCurrentSection(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getDashboard(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getDashboardEventsMap(java.util.List,java.lang.Integer),
public com.hubitat.app.DeviceWrapper com.hubitat.hub.executor.AppExecutor.getDeviceById(java.lang.Long),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.getEXECUTOR_TYPE(),
public java.lang.String com.hubitat.hub.executor.AppExecutor.getFullApiServerUrl(),
public java.lang.String com.hubitat.hub.executor.AppExecutor.getFullLocalApiServerUrl(),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.getHubUID(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getInstalledCapabilities(),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.getLocalApiServerUrl(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getLocationEventsSince(java.lang.String,java.util.Date),
public java.util.List com.hubitat.hub.executor.AppExecutor.getLocationEventsSince(java.lang.String,java.util.Date,java.util.Map),
public java.util.List com.hubitat.hub.executor.AppExecutor.getPages(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getParent(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getPreferences(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getState(),
public com.hubitat.app.DeviceWrapper com.hubitat.hub.executor.AppExecutor.getSubscribedDeviceById(java.lang.Long),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getSunriseAndSunset(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getSunriseAndSunset(java.util.Map),
public java.util.List com.hubitat.hub.executor.AppExecutor.getThirdPartyHubIPList(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.util.Map,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.lang.String,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.util.Map,java.lang.String,java.lang.String),
public boolean com.hubitat.hub.executor.AppExecutor.isAppInstalled(java.lang.String,java.lang.String),
public boolean com.hubitat.hub.executor.AppExecutor.isAppInstalled(java.lang.String,java.lang.String,java.lang.String),
public boolean com.hubitat.hub.executor.AppExecutor.isSystemTypeOrHubDeveloper(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.label(java.util.Map),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.localApiServerUrl(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.mappings(groovy.lang.Closure),
public void com.hubitat.hub.executor.AppExecutor.metadata(groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.mode(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map,groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.lang.String,java.lang.String,groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map,java.lang.String,java.lang.String,groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.paragraph(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.paragraph(java.util.Map,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.path(java.lang.String,groovy.lang.Closure),
public void com.hubitat.hub.executor.AppExecutor.pause(java.lang.Long),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.preferences(groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.preferences(java.util.Map,groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.render(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.render(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.saveState(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.lang.String,groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.util.Map,groovy.lang.Closure),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.util.Map,java.lang.String,groovy.lang.Closure),
public void com.hubitat.hub.executor.AppExecutor.sendEvent(java.util.Map),
public static void com.hubitat.hub.executor.AppExecutor.sendEvent(com.hubitat.app.DeviceWrapper,java.util.Map),
public static void com.hubitat.hub.executor.AppExecutor.sendEvent(java.lang.String,java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.sendSms(java.lang.String,java.lang.String),
public void com.hubitat.hub.executor.AppExecutor.sendSmsMessage(java.lang.String,java.lang.String),

public void com.hubitat.hub.executor.AppExecutor.setApp(com.hubitat.app.InstalledAppWrapper),
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

public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.Object),
public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.String,java.lang.Object),
public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.String,java.lang.Object,java.util.Map),

public static java.lang.Long com.hubitat.hub.executor.AppExecutor.timeOffset(java.lang.Number),
public static java.lang.Long com.hubitat.hub.executor.AppExecutor.timeOffset(java.lang.String),
public static java.util.Date com.hubitat.hub.executor.AppExecutor.timeTodayAfter(java.lang.String,java.lang.String),
public static java.util.Date com.hubitat.hub.executor.AppExecutor.timeTodayAfter(java.lang.String,java.lang.String,java.util.TimeZone),

public void com.hubitat.hub.executor.AppExecutor.unsubscribe(),
public void com.hubitat.hub.executor.AppExecutor.unsubscribe(com.hubitat.app.DeviceWrapper),
public void com.hubitat.hub.executor.AppExecutor.unsubscribe(java.util.List)
*/

trait AppExecutorApi extends BaseExecutorApi
{
    // Returns list of devices or one device from config
    // Device/List<Device> get<device or capability preference name>()

    // Returns Number or Decimal config value
    // BigDecimal get<number or decimal preference name>()

    // Return value of config property.
    // If it's text, returns: String - the value entered as text
    // If it's mode, returns: String - the name of the mode selected
    // If it's time, returns: String - the full date string in the format of “yyyy-MM-dd’T’HH:mm:ss.SSSZ”
    // String get<text, mode, or time preference name>()

    /**
        @throws IllegalArgumentException - If a label was not supplied
        @throws NotFoundException - If the given SmartApp name was not found in the given Namespace.
        @throws SizeLimitExceededException - If this SmartApp already has the maximum number of children allowed (500).
    */
    abstract InstalledAppWrapper addChildApp(String namespace, String smartAppVersionName, String label, Map properties)

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
     * @return true if max of 6 scheduled jobs was not reached.
     */
    abstract boolean canSchedule()

    /**
     * Creates access token and puts into state.accessToken
     * @return may return access token here too.
     */
    abstract def createAccessToken()

    abstract List<InstalledAppWrapper> findAllChildAppsByName(String name)

    abstract List<InstalledAppWrapper> findAllChildAppsByNamespaceAndName(String namespace, String name)

    /**
     * @return found app (if many are matching, first one is returned) or null.
     */
    abstract InstalledAppWrapper findChildAppByName(String appName)

    /**
     * @return found app (if many are matching, first one is returned) or null.
     */
    abstract InstalledAppWrapper findChildAppByNamespaceAndName(String namespace, String appName)

    /**
     @return all child apps, even if installation state is "incomplete"
     */
    abstract List<InstalledAppWrapper> getAllChildApps()


    /**
     * @return getChildDevices(true)
     */
    abstract List getAllChildDevices()

    abstract String getApiServerUrl()

    abstract ColorUtilities getColorUtil()


    /**
     * @param supported options:
     * (not supported in Hubitat?) zipCode (String) - the zip code to use for determining the times.
     *  If not specified then the coordinates of the Hub location are used.
     * (not supported in Hubitat?) locationString (Srtring) - any location string supported by the Weather Underground APIs.
     *  If not specified then the coordinates of the Hub Location are used
     * sunriseOffset (String) - adjust the sunrise time by this amount.
     *  See timeOffset() for supported formats
     * sunsetOffset (String) - adjust the sunset time by this amount.
     *  See timeOffset() for supported formats
     * @return Map with String keys and Date values: [sunrise: Date, sunset: Date]
     */
    abstract Map getSunriseAndSunset(Map options = null)

    abstract void pauseExecution(Long millisecs)

    /**
     @return only child apps whose state is "complete"
     */
    abstract List<InstalledAppWrapper> getChildApps()

    /**
     * Creates App event with specified properties.
     * @param properties. Supported keys:
     * name (required) (String) - The name of the Event. Typically corresponds to an attribute name of a capability.
     * value (required) The value of the Event. The value is stored as a string, but you can pass numbers or other objects.
     * descriptionText (String) - The description of this Event. This appears in the mobile application activity for the device. If not specified, this will be created using the Event name and value.
     * displayed (boolean) - Pass true to display this Event in the mobile application activity feed, false to not display. Defaults to true.
     * linkText (String) - Name of the Event to show in the mobile application activity feed.
     * isStateChange (boolean) - true if this Event caused a device attribute to change state. Typically not used, since it will be set automatically.
     * unit (String) - a unit string, if desired. This will be used to create the descriptionText if it (the descriptionText option) is not specified.
     * device (Device) - The device for which this Event is created for.
     * data (Map) A map of additional information to store with the Event
     */
    abstract void sendEvent(Map properties)

    /**
     * Creates Device event with specified properties.
     * @param properties. Supported keys:
     * name (required) (String) - The name of the Event. Typically corresponds to an attribute name of a capability.
     * value (required) The value of the Event. The value is stored as a string, but you can pass numbers or other objects.
     * descriptionText (String) - The description of this Event. This appears in the mobile application activity for the device. If not specified, this will be created using the Event name and value.
     * displayed (boolean) - Pass true to display this Event in the mobile application activity feed, false to not display. Defaults to true.
     * linkText (String) - Name of the Event to show in the mobile application activity feed.
     * isStateChange (boolean) - true if this Event caused a device attribute to change state. Typically not used, since it will be set automatically.
     * unit (String) - a unit string, if desired. This will be used to create the descriptionText if it (the descriptionText option) is not specified.
     * device (Device) - The device for which this Event is created for.
     * data (Map) A map of additional information to store with the Event
     */
    abstract void sendEvent(DeviceWrapper device, Map properties)

    /**
     * Creates Device event with specified properties.
     * @param properties. Supported keys:
     * name (required) (String) - The name of the Event. Typically corresponds to an attribute name of a capability.
     * value (required) The value of the Event. The value is stored as a string, but you can pass numbers or other objects.
     * descriptionText (String) - The description of this Event. This appears in the mobile application activity for the device. If not specified, this will be created using the Event name and value.
     * displayed (boolean) - Pass true to display this Event in the mobile application activity feed, false to not display. Defaults to true.
     * linkText (String) - Name of the Event to show in the mobile application activity feed.
     * isStateChange (boolean) - true if this Event caused a device attribute to change state. Typically not used, since it will be set automatically.
     * unit (String) - a unit string, if desired. This will be used to create the descriptionText if it (the descriptionText option) is not specified.
     * device (Device) - The device for which this Event is created for.
     * data (Map) A map of additional information to store with the Event
     */
    abstract void sendEvent(String dni, Map properties)

    /**
     * Sends the message as an SMS message to the specified phone number and displays it in Hello, Home.
     * @param message - no longer than 140 chars
     */
    abstract void sendSms(String phone, String message)

    /**
     * Sends the message as an SMS message to the specified phone number but does not display it in Hello, Home.
     * @param message - no longer than 140 chars
     */
    abstract void sendSmsMessage(String phone, String message)

    /**
     * Set the Mode for this Location.
     */
    abstract void setLocationMode(String mode)

    /**
     * Set the Mode for this Location.
     */
    abstract void setLocationMode(Mode mode)

    /**
     * @return configuration settings for the app.
     */
    abstract Map getSettings()

    /**
     * Subscribe to event, or attribute value changes.
     * @param thing - could be Device, Location (or app)?
     * @param attributeNameOrNameAndValueOrEventName. Could be:
     *  1. name of event,
     *  2. name of attribute that changed
     *  3. Format of 'attribute.value' to get notification only for specific value
     * @param handlerMethod - The method to call when the Event is fired.
     *  Can be a String of the method name or the method reference itself.
     * @param options
     */
    abstract void subscribe(
            def appOrDeviceOrLocation,
            String attributeNameOrNameAndValueOrEventName,
            def handlerMethod,
            Map options = null)


    /**
     * @param minutes
     * @return milliseconds in given minutes
     */
    abstract Long timeOffset(Number minutes)

    /**
     * @param hoursAndMinutesString - A string in the format of "hh:mm" to get the offset in milliseconds for.
     *  Negative offsets are specified by prefixing the string with a minus sign ("-02:30").
     *  @return milliseconds in given hours + minutes
     */
    abstract Long timeOffset(String hoursAndMinutesString)

    abstract String getFullApiServerUrl()
    abstract String getLocalApiServerUrl()
    abstract String getFullLocalApiServerUrl()
    abstract String localApiServerUrl(String url)
    abstract String fullLocalApiServerUrl(String url)



//    abstract def definition(def definitionsMap)
//    abstract def preferences(def preferenceCallback)
//    abstract void sendHubCommand(HubAction action)
//
//    abstract def getPresenceDevices()
//    abstract def getOmniDevices()
//    abstract def getMotionDevices()
//    abstract def getContactDevices()
//    abstract def getAccelerationDevices()
//    abstract def getMultiSensors()
//    abstract def getOmniSensors()
//    abstract def getSwitchDevices()
//    abstract def getDimmerDevices()
//    abstract def getLocks()
//    abstract def getModes()

    /**
     * Deletes all subscriptions for the installed App.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    abstract void unsubscribe()

    /**
     * Deletes device subscription.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    abstract void unsubscribe(DeviceWrapper device)

    /**
     * Deletes subscriptions for devices.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    abstract void unsubscribe(List<DeviceWrapper> devices)
}