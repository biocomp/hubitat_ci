package biocomp.hubitatCiTest.emulation

/*
Real methods:
Common/meta:
public java.lang.Object com.hubitat.hub.domain.State.invokeMethod(java.lang.String,java.lang.Object),
public groovy.lang.MetaClass com.hubitat.hub.domain.State.getMetaClass(),
public java.lang.Object com.hubitat.hub.domain.State.getProperty(java.lang.String),
public void com.hubitat.hub.domain.State.setProperty(java.lang.String,java.lang.Object),
public java.lang.String com.hubitat.hub.domain.State.toString()
public void com.hubitat.hub.domain.State.setMetaClass(groovy.lang.MetaClass),

Getters/main methods:
++ public java.lang.String com.hubitat.hub.domain.State.getDataType(),
++ public java.util.Date com.hubitat.hub.domain.State.getDate(),
++ public java.lang.Double com.hubitat.hub.domain.State.getDoubleValue(),
++ public java.lang.Float com.hubitat.hub.domain.State.getFloatValue(),
++ public java.lang.Long com.hubitat.hub.domain.State.getId(),
++ public java.lang.Object com.hubitat.hub.domain.State.getJsonValue(),
++ public java.lang.String com.hubitat.hub.domain.State.getName(),
++ public java.math.BigDecimal com.hubitat.hub.domain.State.getNumberValue(),
++ public java.lang.String com.hubitat.hub.domain.State.getStringValue(),
++ public java.lang.String com.hubitat.hub.domain.State.getUnit(),
++ public java.lang.String com.hubitat.hub.domain.State.getValue(),

Setters:
public void com.hubitat.hub.domain.State.setDataType(java.lang.String),
public void com.hubitat.hub.domain.State.setDate(java.util.Date),
public void com.hubitat.hub.domain.State.setId(java.lang.Long),
public void com.hubitat.hub.domain.State.setName(java.lang.String),
public void com.hubitat.hub.domain.State.setUnit(java.lang.String),
public void com.hubitat.hub.domain.State.setValue(java.lang.String),
 */

trait State {
    abstract String getDataType()

    /**
     @return when event was created
     */
    abstract Date getDate()

    /**
     @return unique ID of this state
     */
    abstract Long getId()

    /**
     @return value parsed as double
     @exception Exception if value is not a double
     */
    abstract Double getDoubleValue()

    /**
     @return value parsed as float
     @exception Exception if value is not a float
     */
    abstract Float getFloatValue()

    /**
     @return value parsed as Integer
     @exception Exception if value is not an Integer
     */
    abstract Integer getIntegerValue()

    /**
     @return value interpreted as ISO-8601 string with Date, null if it's not
     */
    abstract String getIsoDate()

    /**
     @return value parsed as JSON object
     @exception Exception if value is not JSON
     */
    abstract Object getJsonValue()

    abstract Location getLocation()

    abstract String getLocationId()

    abstract String getName()

    /**
     @return value parsed as Number
     @exception Exception if value is not a Number
     */
    abstract Number getNumberValue()

    /**
     *
     * @return value as String
     */
    abstract String getStringValue()

    /**
     *
     * @retrun The unit of measure for this Event, if applicable
     */
    abstract String getUnit()

    /**
     *
     * @return value as String
     */
    abstract String getValue()
}

