package biocomp.hubitatCiTest.emulation

trait AppApi extends AppAndDriverCommonApi
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
     * @throws UnknownDeviceTypeException - If a Device with the specified name and namespace is not found.
     * @throws IllegalArgumentException - If the deviceNetworkId is not specified.
     * @throws SizeLimitExceededException - If this App already has the maximum number of children allowed (500).
     */
    abstract ChildDeviceWrapper addChildDevice(String typeName, String deviceNetworkId, String hubId, Map properties)

    abstract ChildDeviceWrapper addChildDevice(String namespace, String typeName, String deviceNetworkId, String hubId, Map properties)

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
     * @throws NotFoundException
     */
    abstract void deleteChildDevice(String deviceNetworkId)

    /**
     * @return getChildDevices(true)
     */
    abstract List getAllChildDevices()

    abstract String getApiServerUrl()

    abstract Device getChildDevice(String deviceNetworkId)

    abstract List getChildDevices(boolean includeVirtualDevices = false)

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
    abstract void sendEvent(Device device, Map properties)

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






    abstract def definition(def definitionsMap)
    abstract def preferences(def preferenceCallback)
    abstract void sendHubCommand(HubAction action)

    abstract def subscribe(def device, String propertyName, Closure<Event> handler)
    abstract def subscribe(def device, Closure<Event> handler)

    abstract def getPresenceDevices()
    abstract def getOmniDevices()
    abstract def getMotionDevices()
    abstract def getContactDevices()
    abstract def getAccelerationDevices()
    abstract def getMultiSensors()
    abstract def getOmniSensors()
    abstract def getSwitchDevices()
    abstract def getDimmerDevices()
    abstract def getLocks()
    abstract def getModes()

    abstract String getIp()
    abstract boolean getEnabled()
    abstract boolean getLogEnable()
}