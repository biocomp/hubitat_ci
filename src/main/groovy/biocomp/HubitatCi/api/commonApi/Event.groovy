package me.biocomp.hubitat_ci.api.common_api

/**
 * Real methods:
 * Meta/Common:
 * public java.util.Map com.hubitat.hub.domain.Event.toMap()
 * public static void com.hubitat.hub.domain.Event.__$swapInit(),
 * public java.lang.Object com.hubitat.hub.domain.Event.getProperty(java.lang.String),
 * public groovy.lang.MetaClass com.hubitat.hub.domain.Event.getMetaClass(),
 * public void com.hubitat.hub.domain.Event.setProperty(java.lang.String,java.lang.Object),
 * public java.lang.Object com.hubitat.hub.domain.Event.invokeMethod(java.lang.String,java.lang.Object),
 * public void com.hubitat.hub.domain.Event.setMetaClass(groovy.lang.MetaClass),
 *
 * Getters/main methods:
 * public boolean com.hubitat.hub.domain.Event.getArchivable(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getData(),
 * ++ public java.util.Date com.hubitat.hub.domain.Event.getDate(),
 * ++ public java.util.Date com.hubitat.hub.domain.Event.getDateValue(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getDescription(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getDescriptionText(),
 * ++ public com.hubitat.app_api.DeviceWrapper com.hubitat.hub.domain.Event.getDevice(),
 * ++ public java.lang.Long com.hubitat.hub.domain.Event.getDeviceId(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getDisplayName(),
 * public boolean com.hubitat.hub.domain.Event.getDisplayed(),
 * ++ public java.lang.Double com.hubitat.hub.domain.Event.getDoubleValue(),
 * ++ public java.lang.Float com.hubitat.hub.domain.Event.getFloatValue(),
 * ++ public java.lang.Long com.hubitat.hub.domain.Event.getHubId(),
 * ++ public java.lang.Long com.hubitat.hub.domain.Event.getId(),
 * public java.lang.Long com.hubitat.hub.domain.Event.getInstalledAppId(),
 * ++ public java.lang.Integer com.hubitat.hub.domain.Event.getIntegerValue(),
 * ++ public java.lang.Boolean com.hubitat.hub.domain.Event.getIsStateChange(),
 * public java.util.Map com.hubitat.hub.domain.Event.getJsonData(),
 * public java.lang.String com.hubitat.hub.domain.Event.getLinkText(),
 * ++ public com.hubitat.hub.domain.Location com.hubitat.hub.domain.Event.getLocation(),
 * ++ public java.lang.Long com.hubitat.hub.domain.Event.getLocationId(),
 * ++ public java.lang.Long com.hubitat.hub.domain.Event.getLongValue(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getName(),
 * ++ public java.lang.Number com.hubitat.hub.domain.Event.getNumberValue(),
 * ++ public java.lang.Number com.hubitat.hub.domain.Event.getNumericValue(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getSource(),
 * public boolean com.hubitat.hub.domain.Event.getTranslatable(),
 * public java.lang.String com.hubitat.hub.domain.Event.getType(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getUnit(),
 * public long com.hubitat.hub.domain.Event.getUnixTime(),
 * ++ public java.lang.String com.hubitat.hub.domain.Event.getValue(),
 * public boolean com.hubitat.hub.domain.Event.isArchivable(),
 * ++ public java.lang.Boolean com.hubitat.hub.domain.Event.isDigital(),
 * public boolean com.hubitat.hub.domain.Event.isDisplayed(),
 * ++ public java.lang.Boolean com.hubitat.hub.domain.Event.isPhysical(),
 * public boolean com.hubitat.hub.domain.Event.isTranslatable(),
 * public com.hubitat.hub.domain.Event com.hubitat.hub.domain.Event.populateValues(java.util.Map),
 *
 * Setters:
 * public void com.hubitat.hub.domain.Event.setArchivable(boolean),
 * public void com.hubitat.hub.domain.Event.setData(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setDate(java.util.Date),
 * public void com.hubitat.hub.domain.Event.setDescription(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setDescriptionText(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setDevice(com.hubitat.app_api.DeviceWrapper),
 * public void com.hubitat.hub.domain.Event.setDeviceId(java.lang.Long),
 * public void com.hubitat.hub.domain.Event.setDisplayName(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setDisplayed(boolean),
 * public void com.hubitat.hub.domain.Event.setHubId(java.lang.Long),
 * public void com.hubitat.hub.domain.Event.setId(java.lang.Long),
 * public void com.hubitat.hub.domain.Event.setInstalledAppId(java.lang.Long),
 * public void com.hubitat.hub.domain.Event.setIsStateChange(java.lang.Boolean),
 * public void com.hubitat.hub.domain.Event.setLinkText(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setLocation(com.hubitat.hub.domain.Location),
 * public void com.hubitat.hub.domain.Event.setLocationId(java.lang.Long),
 * public void com.hubitat.hub.domain.Event.setName(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setSource(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setTranslatable(boolean),
 * public void com.hubitat.hub.domain.Event.setType(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setUnit(java.lang.String),
 * public void com.hubitat.hub.domain.Event.setValue(java.lang.String),
 */


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

    abstract DeviceWrapper getDevice()

    /**
     @return user-friendly name of event source
     */
    abstract String getDisplayName()

    /**
     @return getDevice( ).getId( )
     */
    abstract Long getDeviceId()

    /**
     @return unique ID of this event
     */
    abstract Long getId()

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

    abstract Long getHubId()

    /**
     @return value of event parsed as Integer
     @exception Exception if value is not an Integer
     */
    abstract Integer getIntegerValue()

    /**
     @return value of event parsed as JSON object
     @exception Exception if value is not JSON
     */
    abstract Map getJsonData()

    abstract Location getLocation()

    abstract Long getLocationId()

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
     * "APP” 	Event originated by an app_api touch Event in the mobile application.
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
     * @retrun The unit of measure for this Event, if applicable
     */
    abstract String getUnit()

    /**
     *
     * @return value of event as String
     */
    abstract String getValue()

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
    abstract boolean getIsStateChange()
}

