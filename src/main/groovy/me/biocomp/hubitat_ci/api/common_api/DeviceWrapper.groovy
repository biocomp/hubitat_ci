package me.biocomp.hubitat_ci.api.common_api

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.Capability
import me.biocomp.hubitat_ci.api.Command
import me.biocomp.hubitat_ci.api.State

trait DeviceWrapper {
    /**The latest instance for the specified Attribute.
     The exact name will vary depending on the device and its available attributes.*/
    //def get<attribute name>State()

    /**Executes command on the device.
     ** Parameters:**
     @param arguments - The arguments to the command, if required.
     @param options - A map of options to send to the command. Only the 'delay' option is currently supported:
      delay - Number The number of milliseconds to wait before sending the command to the device.
     */
    //void <command name> ( )

    /**The latest reported values for the specified attribute.*/
    //def getCurrent<Uppercase attribute_name>()

    /**Gets the latest state for the specified attribute.*/
    abstract State currentState(String attributeName, boolean unknown)

    abstract List<State> getCurrentStates()

    /**
     * Retrieve the current value of an attribute. By default this value is cached during a single run of the driver.
     * attributeName - The attribute to get the current value of.
     * skipCache - Optional, do not use the cached value of the attribute, instead force the system
     *             to read the latest from the database.
     */
    abstract def currentValue(String attributeName, boolean skipCache=false)

    /**List of Events for the Device in reverse chronological order (newest first).
     @param options - map that only supports one option:
      max (Number) - max events to return. Default is 10.
     */
    abstract List<Event> events(Map options = null)

    /**Get a list of Events between the specified start and end dates.
     @param options. Supported options: max (Number) - events to return (default = 10)
     */
    abstract List<Event> eventsBetween(Date startDate, Date endDate, Map options = null)

    /**Get a list of Events between since date.
     @param options. Supported options: max (Number) - events to return (default = 10)
     */
    abstract List<Event> eventsSince(Date startDate, Map options = null)

    /**Device capabilties*/
    abstract List<Capability> getCapabilities()

    abstract String getDeviceNetworkId()

    abstract String getDisplayName()

    /**Hub associated with device*/
    abstract Hub getHub()

    /**Unique system ID*/
    abstract String getId()

    abstract String getLabel()

    /**
     @return Date of last activity
     */
    abstract Date getLastActivity()


    /**
     @return Device's status. 'INACTIVE' in case no status was found.
     */
    abstract String getStatus()

    /**Internal device name.*/
    abstract String getName()

    abstract List<Attribute> getSupportedAttributes()

    abstract List<Command> getSupportedCommands()

    /**
     @return device's type.
     */
    abstract String getTypeName()

    /**
     * Determines if the device has the specified attribute. This works for both built-in and custom attributes.
     * @param attributeName The attribute to check for.
     * @return True if the attribute exists, false otherwise
     */
    abstract boolean hasAttribute(String attributeName)

    /**
     * Determines if the device has the specified capability.
     * @param commandName The capability to check for.
     * @return True if the capability exists, false otherwise.
     */
    abstract boolean hasCapability(String capabilityName)

    /**
     * Determines if the device has the specified command. This works for both built-in and custom commands.
     * @param commandName The command to check for.
     * @return True if the command exists, false otherwise.
     */
    abstract boolean hasCommand(String commandName)

    /**Latest device state for the specified attribute.*/
    abstract State latestState(String attributeName)

    abstract State latestState(String attributeName, boolean unknown)

    /**Latest reported value for the specified attribute.*/
    abstract def latestValue(String attributeName)

    abstract def latestValue(String attributeName, boolean unknown)

    /**Device's states in reverse chronological order.
     @param options : max (Number) - max states to return (default = 10).
     @return list of states, up to 7 days, and up to 1000.
     */
    abstract List<State> statesSince(String attributeName, Date startDate, Map options = null)

    abstract Boolean getDisplayAsChild()
    abstract Boolean getIsComponent()
    abstract Long getDriverId()
    abstract Long getIdAsLong()
    abstract Long getParentAppId()
    abstract Long getParentDeviceId()
    abstract Map getData()

    /**
     * Get a data value that was set for this device.
     * @param The String value of the data item.
     * @return The value of the data.
     */
    abstract String getDataValue(String name)

    /**
     * DEPRECATED: See getDataValue, this method is a wrapper for getDataValue.
     */
    abstract String getDeviceDataByName(String deviceName)

    abstract String getDriverType()
    abstract String getControllerType()
    abstract String getEndpointId()
    abstract String getLanId()
    abstract String getZigbeeId()
    abstract boolean isDisabled()
    abstract void clearSetting(String settingName)
    abstract void removeSetting(String settingName)
    abstract void sendEvent(Map)
    abstract void setDeviceNetworkId(String)
    abstract void setDisplayName(String)

    /**
     * Update the label of the device.
     * @param the new label for the device.
     */
    abstract void setLabel(String label)

    abstract void setLanId(String id)

    /**
     * Update the name of the device.
     * @param name the new name for the device.
     */
    abstract void setName(String name)

    /**
     * Update or create a data value for this device.
     * @param The name of the data item to store.
     * @param The value of the data item to store.
     */
    abstract void updateDataValue(String name, String value)

    /**
     * Remove a data value from a device.
     * @param The name of the data item to remove.
     */
    abstract void removeDataValue(String name, String value)

    abstract void updateSetting(String name, Boolean value)
    abstract void updateSetting(String name, Date value)
    abstract void updateSetting(String name, Double value)
    abstract void updateSetting(String name, List value)
    abstract void updateSetting(String name, Long value)
    abstract void updateSetting(String name, Map value)
    abstract void updateSetting(String name, String value)
}