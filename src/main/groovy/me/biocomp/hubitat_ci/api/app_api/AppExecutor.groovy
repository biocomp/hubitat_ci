package me.biocomp.hubitat_ci.api.app_api

import me.biocomp.hubitat_ci.api.common_api.BaseExecutor
import me.biocomp.hubitat_ci.api.common_api.ChildDeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapperList
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location

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
        MappingsSource,
        Mappings,
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
     * This is similar to getCurrentState(), but will immediately write and read from the backing data store.
     * Prefer using getCurrentState() over getAtomicState() when possible.
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
//     * @return found app_api (if many are matching, first one is returned) or return null;.
//     */
//    InstalledAppWrapper findChildAppByName(String appName) {
//        return null;
//    }
//
//    /**
//     * @return found app_api (if many are matching, first one is returned) or return null;.
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
     * @return configuration settings for the app_api.
     */
    abstract Map getSettings()

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

    abstract DeviceWrapper getDeviceById(Long deviceId)

    abstract DeviceWrapperList getAllDevicesByCapability(String capability)

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

    abstract InstalledAppWrapper getApp()

    abstract void unsubscribe(InstalledAppWrapper appWrapper)
    abstract void unsubscribe(Location location)
    abstract void unsubscribe(String handlerMethod)
    abstract void unsubscribe(List<DeviceWrapper> listOfDevices)
    abstract void unsubscribe(DeviceWrapper device, String attributeName)
    abstract void unsubscribe(Location location, String attributeName)
    abstract void unsubscribe(List<DeviceWrapper> devices, String attributeName)
    abstract void unsubscribe(DeviceWrapper decice, String attributeName, String handlerMethod)
    abstract void unsubscribe(List<DeviceWrapper> devices, String attributeName, String handlerMethod)

    abstract Long cloneChildApp(Long a, String unknown);
    abstract Long cloneChildApp(Long a, String unknown, Map options)
    abstract def component(Map options)
};