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


    /**The latest reported values of the specified attribute.*/
    abstract def currentValue(String attributeName)

    /**The latest reported values of the specified attribute.*/
    abstract def currentValue(String attributeName, boolean unknown)

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

    /**Label set by user*/

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

    abstract boolean hasAttribute(String attributeName)

    abstract boolean hasCapability(String capabilityName)

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
    abstract String getDataValue(String name)
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
    abstract void setLabel(String label)
    abstract void setLanId(String id)
    abstract void setName(String name)
    abstract void updateDataValue(String name, String value)
    abstract void updateSetting(String name, Boolean value)
    abstract void updateSetting(String name, Date value)
    abstract void updateSetting(String name, Double value)
    abstract void updateSetting(String name, List value)
    abstract void updateSetting(String name, Long value)
    abstract void updateSetting(String name, Map value)
    abstract void updateSetting(String name, String value)
}