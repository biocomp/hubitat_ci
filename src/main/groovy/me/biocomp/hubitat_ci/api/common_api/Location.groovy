package me.biocomp.hubitat_ci.api.common_api

/**
 * A Location is user’s geo-location, such as "Home" or "office".
 *
 * Locations do not have to have a Hub, but generally do.
 * All App and Device objects are injected with a location property
 * that is the Location into which the App or Device was installed.
 */
interface Location {
    abstract boolean getContactBookEnabled()

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
    abstract String getHelloHome()
    abstract String getTimeFormat()
    abstract boolean isContactBookEnabled()
    abstract void createVariable(String name)
    abstract void createVariable(String name, List unknown)
    abstract void removeVariable(String name)
    abstract void setContactBookEnabled(boolean enable)
    abstract void setHelloHome(String helloString)
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
}
