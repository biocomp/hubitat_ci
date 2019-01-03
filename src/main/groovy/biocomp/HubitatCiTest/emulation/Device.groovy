package biocomp.hubitatCiTest.emulation

trait Device {
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

trait ChildDeviceWrapper extends Device
{}