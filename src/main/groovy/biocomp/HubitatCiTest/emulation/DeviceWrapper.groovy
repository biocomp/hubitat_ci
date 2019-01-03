package biocomp.hubitatCiTest.emulation

/**
 * Real class methods:
 * public void com.hubitat.app.DeviceWrapper.clearSetting(java.lang.String),
 * public com.hubitat.hub.domain.State com.hubitat.app.DeviceWrapper.currentState(java.lang.String),
 * public com.hubitat.hub.domain.State com.hubitat.app.DeviceWrapper.currentState(java.lang.String,boolean),
 * public java.lang.Object com.hubitat.app.DeviceWrapper.currentValue(java.lang.String),
 * public java.lang.Object com.hubitat.app.DeviceWrapper.currentValue(java.lang.String,boolean),
 * public java.util.List com.hubitat.app.DeviceWrapper.events(),
 * public java.util.List com.hubitat.app.DeviceWrapper.events(java.util.Map),
 * public java.util.List com.hubitat.app.DeviceWrapper.eventsBetween(java.util.Date,java.util.Date),
 * public java.util.List com.hubitat.app.DeviceWrapper.eventsBetween(java.util.Date,java.util.Date,java.util.Map),
 * public java.util.List com.hubitat.app.DeviceWrapper.eventsSince(java.util.Date),
 * public java.util.List com.hubitat.app.DeviceWrapper.eventsSince(java.util.Date,java.util.Map),
 * public java.util.List com.hubitat.app.DeviceWrapper.getCapabilities(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getControllerType(),
 * public java.util.List com.hubitat.app.DeviceWrapper.getCurrentStates(),
 * public java.util.Map com.hubitat.app.DeviceWrapper.getData(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getDataValue(java.lang.String),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getDeviceDataByName(java.lang.String),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getDeviceNetworkId(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getDisplayName(),
 * public java.lang.Long com.hubitat.app.DeviceWrapper.getDriverId(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getDriverType(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getEndpointId(),
 * public com.hubitat.hub.domain.Hub com.hubitat.app.DeviceWrapper.getHub(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getId(),
 * public java.lang.Long com.hubitat.app.DeviceWrapper.getIdAsLong(),
 * public java.lang.Boolean com.hubitat.app.DeviceWrapper.getIsComponent(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getLabel(),
 * public java.util.Date com.hubitat.app.DeviceWrapper.getLastActivity(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getName(),
 * public java.lang.Long com.hubitat.app.DeviceWrapper.getParentAppId(),
 * public java.lang.Long com.hubitat.app.DeviceWrapper.getParentDeviceId(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getStatus(),
 * public java.util.List com.hubitat.app.DeviceWrapper.getSupportedAttributes(),
 * public java.util.List com.hubitat.app.DeviceWrapper.getSupportedCommands(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getTypeName(),
 * public java.lang.String com.hubitat.app.DeviceWrapper.getZigbeeId(),
 * public java.lang.Boolean com.hubitat.app.DeviceWrapper.hasAttribute(java.lang.String),
 * public java.lang.Boolean com.hubitat.app.DeviceWrapper.hasCapability(java.lang.String),
 * public java.lang.Boolean com.hubitat.app.DeviceWrapper.hasCommand(java.lang.String),
 * public boolean com.hubitat.app.DeviceWrapper.isDisabled(),
 * public com.hubitat.hub.domain.State com.hubitat.app.DeviceWrapper.latestState(java.lang.String),
 * public com.hubitat.hub.domain.State com.hubitat.app.DeviceWrapper.latestState(java.lang.String,boolean),
 * public java.lang.Object com.hubitat.app.DeviceWrapper.latestValue(java.lang.String),
 * public java.lang.Object com.hubitat.app.DeviceWrapper.latestValue(java.lang.String,boolean),
 * public java.lang.Object com.hubitat.app.DeviceWrapper.methodMissing(java.lang.String,java.lang.Object),
 * public java.lang.Object com.hubitat.app.DeviceWrapper.propertyMissing(java.lang.String),
 * public void com.hubitat.app.DeviceWrapper.removeSetting(java.lang.String),
 * public void com.hubitat.app.DeviceWrapper.sendEvent(java.util.Map),
 * public void com.hubitat.app.DeviceWrapper.setDeviceNetworkId(java.lang.String),
 * public void com.hubitat.app.DeviceWrapper.setDisplayName(java.lang.String),
 * public void com.hubitat.app.DeviceWrapper.setLabel(java.lang.String),
 * public void com.hubitat.app.DeviceWrapper.setName(java.lang.String),
 * public java.util.List com.hubitat.app.DeviceWrapper.statesSince(java.lang.String,java.util.Date),
 * public java.util.List com.hubitat.app.DeviceWrapper.statesSince(java.lang.String,java.util.Date,java.util.Map),
 * public java.lang.String com.hubitat.app.DeviceWrapper.toString(),
 * public void com.hubitat.app.DeviceWrapper.updateDataValue(java.lang.String,java.lang.String),
 * public void com.hubitat.app.DeviceWrapper.updateSetting(java.lang.String,java.lang.Boolean),
 * public void com.hubitat.app.DeviceWrapper.updateSetting(java.lang.String,java.lang.Double),
 * public void com.hubitat.app.DeviceWrapper.updateSetting(java.lang.String,java.lang.Long),
 * public void com.hubitat.app.DeviceWrapper.updateSetting(java.lang.String,java.lang.String),
 * public void com.hubitat.app.DeviceWrapper.updateSetting(java.lang.String,java.util.Date),
 * public void com.hubitat.app.DeviceWrapper.updateSetting(java.lang.String,java.util.List),
 * public void com.hubitat.app.DeviceWrapper.updateSetting(java.lang.String,java.util.Map)
 */

trait DeviceWrapper {
    /**
     The latest instance for the specified Attribute.
     The exact name will vary depending on the device and its available attributes.
     */
    //def get<attribute name>State()

/**
 Executes command on the device.
 ** Parameters:**
 @param arguments - The arguments to the command, if required.
 @param options - A map of options to send to the command. Only the 'delay' option is currently supported:
  delay - Number The number of milliseconds to wait before sending the command to the device.
 */
    //void <command name> ( )


/**
 The latest reported values for the specified attribute.
 */
    //def getCurrent<Uppercase attribute_name>()

    /**
     Gets the latest state for the specified attribute.
     */
    abstract State currentState(String attributeName)


    /**
     The latest reported values of the specified attribute.
     */
    abstract def currentValue(String attributeName)

    /**
     List of Events for the Device in reverse chronological order (newest first).
     @param options - map that only supports one option:
      max (Number) - max events to return. Default is 10.
     */
    abstract List<Event> events(Map options = null)

    /**
     Get a list of Events between the specified start and end dates.
     @param options. Supported options: max (Number) - events to return (default = 10)
     */
    abstract List<Event> eventsBetween(Date startDate, Date endDate, Map options = null)

/**
 Get a list of Events between since date.
 @param options. Supported options: max (Number) - events to return (default = 10)
 */

    abstract List<Event> eventsSince(Date startDate, Map options = null)

/**
 Device capabilties
 */

    abstract List<Capability> getCapabilities()

    abstract String getDeviceNetworkId()

    abstract String getDisplayName()

/**
 Hub associated with device
 */

    abstract Hub getHub()

/**
 Unique system ID
 */

    abstract String getId()

/**
 Label set by user
 */

    abstract String getLabel()

/**
 @return Date but as a String?
 */

    abstract String getLastActivity()

    abstract String getManufacturerName()

    abstract String getModelName()

/**
 @return Device's status. 'INACTIVE' in case no status was found.
 */

    abstract String getStatus()

/**
 Internal device name.
 */

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

/**
 Latest device state for the specified attribute.
 */

    abstract State latestState(String attributeName)

/**
 Latest reported value for the specified attribute.
 */

    abstract def latestValue(String attributeName)


    /**
     Device's states in reverse chronological order.
     @param options : max (Number) - max states to return (default = 10)
     */
    abstract List<State> statesBetween(String attributeName, Date startDate, Date endDate, Map options = null)

    /**
     Device's states in reverse chronological order.
     @param options : max (Number) - max states to return (default = 10).
     @return list of states, up to 7 days, and up to 1000.
     */
    abstract List<State> statesSince(String attributeName, Date startDate, Map options = null)
}