package biocomp.hubitatCiTest.emulation

/**
 * @note how it's slightly different from State class.
 */
trait AppState
{
    /**
     @return event's value as Date, or null if it's not
     */
    abstract Date getDateValue()

    /**
     @return unique ID of this state
     */
    abstract String getId()

    abstract String getDescriptionText()

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

    abstract Date getLastUpdated()

    /**
     @return value parsed as long
     @exception Exception if value is not a long
     */
    abstract Long getLongValue()

    abstract String getName()

    /**
     @return value parsed as Number
     @exception Exception if value is not a Number
     */
    abstract Number getNumberValue()

    /**
     @return value parsed as Number
     @exception Exception if value is not a Number
     */
    abstract Number getNumericValue()

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

    /**
     * @return value as a 3-entry Map with keys ‘x’, ‘y’, and ‘z’.
     * @example "[x: 11, y: 22, z: 33]"
     * @exception Exception if value can't be parsed as map
     */
    abstract Map<String, BigDecimal> getXyzValue()
}