package biocomp.hubitatCiTest.emulation

trait Event {
    /**
     @return JSON with additional event data
     */
    abstract String getData()

    /**
     @return when event was created
     */
    abstract Date getDate()

    /**
     @return event's value as Date, or null if it's not
     */
    abstract Date getDateValue()

    abstract String getDescription()

    abstract String getDescriptionText()

    abstract Device getDevice()

    /**
     @return user-friendly name of event source
     */
    abstract String getDisplayName()

    /**
     @return getDevice( ).getId( )
     */
    abstract String getDeviceId()

    /**
     @return unique ID of this event
     */
    abstract String getId()

    /**
     @return value of event parsed as double
     @exception Exception if value is not a double
     */
    abstract Double getDoubleValue()

    /**
     @return value of event parsed as float
     @exception Exception if value is not a float
     */
    abstract Float getFloatValue()

    abstract String getHubId()

    /**
     * @return The unique system identifier of the SmartApp instance associated with this Event.
     */
    abstract String getInstalledSmartAppId()

    /**
     @return value of event parsed as Integer
     @exception Exception if value is not an Integer
     */
    abstract Integer getIntegerValue()

    /**
     @return value interpreted as ISO-8601 string with Date, null if it's not
     */
    abstract String getIsoDate()

    /**
     @return value of event parsed as JSON object
     @exception Exception if value is not JSON
     */
    abstract Object getJsonValue()

    abstract Location getLocation()

    abstract String getLocationId()

    /**
     @return value of event parsed as long
     @exception Exception if value is not a long
     */
    abstract Long getLongValue()

    abstract String getName()

    /**
     @return value of event parsed as Number
     @exception Exception if value is not a Number
     */
    abstract Number getNumberValue()

    /**
     @return value of event parsed as Number
     @exception Exception if value is not a Number
     */
    abstract Number getNumericValue()

    /**
     *
     * @return event source.
     * Supported values:
     * "APP” 	Event originated by an app touch Event in the mobile application.
     * “APP_COMMAND” 	Event originated by using the mobile application (for example, using the mobile application to turn a light off)
     * “COMMAND” 	Event originated by a SmartApp or Device Handler calling a command on a device.
     * “DEVICE“ 	Event originated by the physical actuation of a device.
     * “HUB” 	Event originated on the Hub.
     * “LOCATION” 	Event originated by a Location state change (for example, sunrise and sunset events)
     * “USER”
     */
    abstract String getSource()

    /**
     *
     * @return value of event as String
     */
    abstract String getStringValue()

    /**
     *
     * @retrun The unit of measure for this Event, if applicable
     */
    abstract String getUnit()

    /**
     *
     * @return value of event as String
     */
    abstract String getValue()

    /**
     * @return value of event as a 3-entry Map with keys ‘x’, ‘y’, and ‘z’.
     * @example "[x: 11, y: 22, z: 33]"
     * @exception Exception if value can't be parsed as map
     */
    abstract Map<String, BigDecimal> getXyzValue()

    /**
     * @return false if triggered by physical device
     */
    abstract boolean isDigital()

    /**
     * @return true if triggered by physical device
     */
    abstract boolean isPhysical()

    /**
     * @return true if current value is different from previous
     */
    abstract boolean isStateChange()
}

