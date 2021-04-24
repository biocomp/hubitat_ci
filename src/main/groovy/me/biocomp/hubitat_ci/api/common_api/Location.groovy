package me.biocomp.hubitat_ci.api.common_api

/**
 * A Location is user’s geo-location, such as "Home" or "office".
 *
 * Locations do not have to have a Hub, but generally do.
 * All App and Device objects are injected with a location property
 * that is the Location into which the App or Device was installed.
 */
interface Location {
    abstract String getMode()
    abstract Mode getCurrentMode()
    abstract List<Mode> getModes()

    abstract String getName()

    abstract Long getId()

    /**
     * @throws Exception if mode is invalid
     */
    abstract void setMode(String mode)

    /**
     * @return "C" or "F"
     */
    abstract String getTemperatureScale()

    abstract TimeZone getTimeZone()

    abstract String getZipCode()

    abstract List<Hub> getHubs()

    abstract BigDecimal getLatitude()
    abstract BigDecimal getLongitude()

    abstract Date getSunrise()
    abstract Date getSunset()
    abstract List getTrueModes()
    abstract List getVariableValues(String name)
    abstract Long getVersion()
    abstract Map toMap()
    abstract Map toMap(boolean a)
    abstract String getFormattedLatitude()
    abstract String getFormattedLongitude()
    abstract String getTimeFormat()
    abstract void createVariable(String name)
    abstract void createVariable(String name, List unknown)
    abstract void removeVariable(String name)
    abstract void setId(Long id)
    abstract void setLatitude(BigDecimal latitude)
    abstract void setLongitude(BigDecimal longitude)
    abstract void setName(String name)
    abstract void setSunrise(Date sunrize)
    abstract void setSunset(Date sunset)
    abstract void setTemperatureScale(String cOrF)
    abstract void setTimeZone(TimeZone zone)
    abstract void setVersion(Long version)
    abstract void setZipCode(String zip)

    abstract me.biocomp.hubitat_ci.api.common_api.Hub getHub() // Original: public static com.hubitat.hub.domain.Hub com.hubitat.hub.domain.Location.getHub()
    abstract java.lang.Long getModeId() // Original: public java.lang.Long com.hubitat.hub.domain.Location.getModeId()
    abstract java.lang.String getState() // Original: public java.lang.String com.hubitat.hub.domain.Location.getState()
    abstract void setModeId(java.lang.Long a) // Original: public void com.hubitat.hub.domain.Location.setModeId(java.lang.Long)
    abstract void setState(java.lang.String a) // Original: public void com.hubitat.hub.domain.Location.setState(java.lang.String)
}
