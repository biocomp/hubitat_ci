package me.biocomp.hubitat_ci.api

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
     @return value parsed as JSON object
     @exception Exception if value is not JSON
     */
    abstract Object getJsonValue()

    abstract String getName()

    /**
     @return value parsed as Number
     @exception Exception if value is not a Number
     */
    abstract BigDecimal getNumberValue()

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

    abstract me.biocomp.hubitat_ci.api.State fromJson(java.util.Map a) // Original: public static com.hubitat.hub.domain.State com.hubitat.hub.domain.State.fromJson(java.util.Map)
    abstract void setDataType(java.lang.String a) // Original: public void com.hubitat.hub.domain.State.setDataType(java.lang.String)
    abstract void setDate(java.util.Date a) // Original: public void com.hubitat.hub.domain.State.setDate(java.util.Date)
    abstract void setId(java.lang.Long a) // Original: public void com.hubitat.hub.domain.State.setId(java.lang.Long)
    abstract void setName(java.lang.String a) // Original: public void com.hubitat.hub.domain.State.setName(java.lang.String)
    abstract void setUnit(java.lang.String a) // Original: public void com.hubitat.hub.domain.State.setUnit(java.lang.String)
    abstract void setValue(java.lang.String a) // Original: public void com.hubitat.hub.domain.State.setValue(java.lang.String)
    abstract java.util.Map toJsonMap() // Original: public java.util.Map com.hubitat.hub.domain.State.toJsonMap()
}

