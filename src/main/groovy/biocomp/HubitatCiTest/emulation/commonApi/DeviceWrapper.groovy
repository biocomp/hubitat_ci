package biocomp.hubitatCiTest.emulation.commonApi

import biocomp.hubitatCiTest.emulation.Attribute
import biocomp.hubitatCiTest.emulation.Capability
import biocomp.hubitatCiTest.emulation.Command
import biocomp.hubitatCiTest.emulation.State

/**
 * Real class methods:
 * Common/Meta:
 * public java.lang.String com.hubitat.appApi.DeviceWrapper.toString(),
 * public java.lang.Object com.hubitat.appApi.DeviceWrapper.methodMissing(java.lang.String,java.lang.Object),
 * public java.lang.Object com.hubitat.appApi.DeviceWrapper.propertyMissing(java.lang.String),
 *
 * Getters/main methods:
 * public void com.hubitat.appApi.DeviceWrapper.clearSetting(java.lang.String),
 * ++ public com.hubitat.hub.domain.State com.hubitat.appApi.DeviceWrapper.currentState(java.lang.String),
 * ++ public com.hubitat.hub.domain.State com.hubitat.appApi.DeviceWrapper.currentState(java.lang.String,boolean),
 * ++ public java.lang.Object com.hubitat.appApi.DeviceWrapper.currentValue(java.lang.String),
 * ++ public java.lang.Object com.hubitat.appApi.DeviceWrapper.currentValue(java.lang.String,boolean),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.events(),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.events(java.util.Map),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.eventsBetween(java.util.Date,java.util.Date),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.eventsBetween(java.util.Date,java.util.Date,java.util.Map),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.eventsSince(java.util.Date),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.eventsSince(java.util.Date,java.util.Map),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.getCapabilities(),
 * public java.lang.String com.hubitat.appApi.DeviceWrapper.getControllerType(),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.getCurrentStates(),
 * public java.util.Map com.hubitat.appApi.DeviceWrapper.getData(),
 * public java.lang.String com.hubitat.appApi.DeviceWrapper.getDataValue(java.lang.String),
 * public java.lang.String com.hubitat.appApi.DeviceWrapper.getDeviceDataByName(java.lang.String),
 * ++ public java.lang.String com.hubitat.appApi.DeviceWrapper.getDeviceNetworkId(),
 * ++ public java.lang.String com.hubitat.appApi.DeviceWrapper.getDisplayName(),
 * public java.lang.Long com.hubitat.appApi.DeviceWrapper.getDriverId(),
 * public java.lang.String com.hubitat.appApi.DeviceWrapper.getDriverType(),
 * public java.lang.String com.hubitat.appApi.DeviceWrapper.getEndpointId(),
 * ++ public com.hubitat.hub.domain.Hub com.hubitat.appApi.DeviceWrapper.getHub(),
 * ++ public java.lang.String com.hubitat.appApi.DeviceWrapper.getId(),
 * public java.lang.Long com.hubitat.appApi.DeviceWrapper.getIdAsLong(),
 * public java.lang.Boolean com.hubitat.appApi.DeviceWrapper.getIsComponent(),
 * ++ public java.lang.String com.hubitat.appApi.DeviceWrapper.getLabel(),
 * ++ public java.util.Date com.hubitat.appApi.DeviceWrapper.getLastActivity(),
 * ++ public java.lang.String com.hubitat.appApi.DeviceWrapper.getName(),
 * public java.lang.Long com.hubitat.appApi.DeviceWrapper.getParentAppId(),
 * public java.lang.Long com.hubitat.appApi.DeviceWrapper.getParentDeviceId(),
 * ++ public java.lang.String com.hubitat.appApi.DeviceWrapper.getStatus(),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.getSupportedAttributes(),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.getSupportedCommands(),
 * ++ public java.lang.String com.hubitat.appApi.DeviceWrapper.getTypeName(),
 * public java.lang.String com.hubitat.appApi.DeviceWrapper.getZigbeeId(),
 * ++ public java.lang.Boolean com.hubitat.appApi.DeviceWrapper.hasAttribute(java.lang.String),
 * ++ public java.lang.Boolean com.hubitat.appApi.DeviceWrapper.hasCapability(java.lang.String),
 * ++ public java.lang.Boolean com.hubitat.appApi.DeviceWrapper.hasCommand(java.lang.String),
 * public boolean com.hubitat.appApi.DeviceWrapper.isDisabled(),
 * ++ public com.hubitat.hub.domain.State com.hubitat.appApi.DeviceWrapper.latestState(java.lang.String),
 * ++ public com.hubitat.hub.domain.State com.hubitat.appApi.DeviceWrapper.latestState(java.lang.String,boolean),
 * ++ public java.lang.Object com.hubitat.appApi.DeviceWrapper.latestValue(java.lang.String),
 * ++ public java.lang.Object com.hubitat.appApi.DeviceWrapper.latestValue(java.lang.String,boolean),
 * public void com.hubitat.appApi.DeviceWrapper.removeSetting(java.lang.String),
 * public void com.hubitat.appApi.DeviceWrapper.sendEvent(java.util.Map),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.statesSince(java.lang.String,java.util.Date),
 * ++ public java.util.List com.hubitat.appApi.DeviceWrapper.statesSince(java.lang.String,java.util.Date,java.util.Map),
 * public void com.hubitat.appApi.DeviceWrapper.updateDataValue(java.lang.String,java.lang.String),
 * public void com.hubitat.appApi.DeviceWrapper.updateSetting(java.lang.String,java.lang.Boolean),
 * public void com.hubitat.appApi.DeviceWrapper.updateSetting(java.lang.String,java.lang.Double),
 * public void com.hubitat.appApi.DeviceWrapper.updateSetting(java.lang.String,java.lang.Long),
 * public void com.hubitat.appApi.DeviceWrapper.updateSetting(java.lang.String,java.lang.String),
 * public void com.hubitat.appApi.DeviceWrapper.updateSetting(java.lang.String,java.util.Date),
 * public void com.hubitat.appApi.DeviceWrapper.updateSetting(java.lang.String,java.util.List),
 * public void com.hubitat.appApi.DeviceWrapper.updateSetting(java.lang.String,java.util.Map)
 *
 *
 * Setters:
 * public void com.hubitat.appApi.DeviceWrapper.setDeviceNetworkId(java.lang.String),
 * public void com.hubitat.appApi.DeviceWrapper.setDisplayName(java.lang.String),
 * public void com.hubitat.appApi.DeviceWrapper.setLabel(java.lang.String),
 * public void com.hubitat.appApi.DeviceWrapper.setName(java.lang.String),*/

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