package me.biocomp.hubitat_ci.api.common_api

import biocomp.hubitatCi.api.Attribute
import biocomp.hubitatCi.api.Capability
import biocomp.hubitatCi.api.Command
import biocomp.hubitatCi.api.State

/**
 * Real class methods:
 * Common/Meta:
 * public java.lang.String com.hubitat.app_api.DeviceWrapper.toString(),
 * public java.lang.Object com.hubitat.app_api.DeviceWrapper.methodMissing(java.lang.String,java.lang.Object),
 * public java.lang.Object com.hubitat.app_api.DeviceWrapper.propertyMissing(java.lang.String),
 *
 * Getters/main methods:
 * public void com.hubitat.app_api.DeviceWrapper.clearSetting(java.lang.String),
 * ++ public com.hubitat.hub.domain.State com.hubitat.app_api.DeviceWrapper.currentState(java.lang.String),
 * ++ public com.hubitat.hub.domain.State com.hubitat.app_api.DeviceWrapper.currentState(java.lang.String,boolean),
 * ++ public java.lang.Object com.hubitat.app_api.DeviceWrapper.currentValue(java.lang.String),
 * ++ public java.lang.Object com.hubitat.app_api.DeviceWrapper.currentValue(java.lang.String,boolean),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.events(),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.events(java.util.Map),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.eventsBetween(java.util.Date,java.util.Date),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.eventsBetween(java.util.Date,java.util.Date,java.util.Map),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.eventsSince(java.util.Date),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.eventsSince(java.util.Date,java.util.Map),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.getCapabilities(),
 * public java.lang.String com.hubitat.app_api.DeviceWrapper.getControllerType(),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.getCurrentStates(),
 * public java.util.Map com.hubitat.app_api.DeviceWrapper.getData(),
 * public java.lang.String com.hubitat.app_api.DeviceWrapper.getDataValue(java.lang.String),
 * public java.lang.String com.hubitat.app_api.DeviceWrapper.getDeviceDataByName(java.lang.String),
 * ++ public java.lang.String com.hubitat.app_api.DeviceWrapper.getDeviceNetworkId(),
 * ++ public java.lang.String com.hubitat.app_api.DeviceWrapper.getDisplayName(),
 * public java.lang.Long com.hubitat.app_api.DeviceWrapper.getDriverId(),
 * public java.lang.String com.hubitat.app_api.DeviceWrapper.getDriverType(),
 * public java.lang.String com.hubitat.app_api.DeviceWrapper.getEndpointId(),
 * ++ public com.hubitat.hub.domain.Hub com.hubitat.app_api.DeviceWrapper.getHub(),
 * ++ public java.lang.String com.hubitat.app_api.DeviceWrapper.getId(),
 * public java.lang.Long com.hubitat.app_api.DeviceWrapper.getIdAsLong(),
 * public java.lang.Boolean com.hubitat.app_api.DeviceWrapper.getIsComponent(),
 * ++ public java.lang.String com.hubitat.app_api.DeviceWrapper.getLabel(),
 * ++ public java.util.Date com.hubitat.app_api.DeviceWrapper.getLastActivity(),
 * ++ public java.lang.String com.hubitat.app_api.DeviceWrapper.getName(),
 * public java.lang.Long com.hubitat.app_api.DeviceWrapper.getParentAppId(),
 * public java.lang.Long com.hubitat.app_api.DeviceWrapper.getParentDeviceId(),
 * ++ public java.lang.String com.hubitat.app_api.DeviceWrapper.getStatus(),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.getSupportedAttributes(),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.getSupportedCommands(),
 * ++ public java.lang.String com.hubitat.app_api.DeviceWrapper.getTypeName(),
 * public java.lang.String com.hubitat.app_api.DeviceWrapper.getZigbeeId(),
 * ++ public java.lang.Boolean com.hubitat.app_api.DeviceWrapper.hasAttribute(java.lang.String),
 * ++ public java.lang.Boolean com.hubitat.app_api.DeviceWrapper.hasCapability(java.lang.String),
 * ++ public java.lang.Boolean com.hubitat.app_api.DeviceWrapper.hasCommand(java.lang.String),
 * public boolean com.hubitat.app_api.DeviceWrapper.isDisabled(),
 * ++ public com.hubitat.hub.domain.State com.hubitat.app_api.DeviceWrapper.latestState(java.lang.String),
 * ++ public com.hubitat.hub.domain.State com.hubitat.app_api.DeviceWrapper.latestState(java.lang.String,boolean),
 * ++ public java.lang.Object com.hubitat.app_api.DeviceWrapper.latestValue(java.lang.String),
 * ++ public java.lang.Object com.hubitat.app_api.DeviceWrapper.latestValue(java.lang.String,boolean),
 * public void com.hubitat.app_api.DeviceWrapper.removeSetting(java.lang.String),
 * public void com.hubitat.app_api.DeviceWrapper.sendEvent(java.util.Map),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.statesSince(java.lang.String,java.util.Date),
 * ++ public java.util.List com.hubitat.app_api.DeviceWrapper.statesSince(java.lang.String,java.util.Date,java.util.Map),
 * public void com.hubitat.app_api.DeviceWrapper.updateDataValue(java.lang.String,java.lang.String),
 * public void com.hubitat.app_api.DeviceWrapper.updateSetting(java.lang.String,java.lang.Boolean),
 * public void com.hubitat.app_api.DeviceWrapper.updateSetting(java.lang.String,java.lang.Double),
 * public void com.hubitat.app_api.DeviceWrapper.updateSetting(java.lang.String,java.lang.Long),
 * public void com.hubitat.app_api.DeviceWrapper.updateSetting(java.lang.String,java.lang.String),
 * public void com.hubitat.app_api.DeviceWrapper.updateSetting(java.lang.String,java.util.Date),
 * public void com.hubitat.app_api.DeviceWrapper.updateSetting(java.lang.String,java.util.List),
 * public void com.hubitat.app_api.DeviceWrapper.updateSetting(java.lang.String,java.util.Map)
 *
 *
 * Setters:
 * public void com.hubitat.app_api.DeviceWrapper.setDeviceNetworkId(java.lang.String),
 * public void com.hubitat.app_api.DeviceWrapper.setDisplayName(java.lang.String),
 * public void com.hubitat.app_api.DeviceWrapper.setLabel(java.lang.String),
 * public void com.hubitat.app_api.DeviceWrapper.setName(java.lang.String),*/

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

    abstract String getManufacturerName()

    abstract String getModelName()

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

    /**Latest reported value for the specified attribute.*/

    abstract def latestValue(String attributeName)


    /**Device's states in reverse chronological order.
     @param options : max (Number) - max states to return (default = 10)
     */
    abstract List<State> statesBetween(String attributeName, Date startDate, Date endDate, Map options = null)

    /**Device's states in reverse chronological order.
     @param options : max (Number) - max states to return (default = 10).
     @return list of states, up to 7 days, and up to 1000.
     */
    abstract List<State> statesSince(String attributeName, Date startDate, Map options = null)
}