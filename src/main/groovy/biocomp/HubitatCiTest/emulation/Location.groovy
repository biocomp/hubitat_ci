package biocomp.hubitatCiTest.emulation

/*
Meta/Common:
public java.util.Map com.hubitat.hub.domain.Location.toMap(),
public java.util.Map com.hubitat.hub.domain.Location.toMap(boolean),
public java.lang.String com.hubitat.hub.domain.Location.toString()
public groovy.lang.MetaClass com.hubitat.hub.domain.Location.getMetaClass(),
public void com.hubitat.hub.domain.Location.setMetaClass(groovy.lang.MetaClass),
public java.lang.Object com.hubitat.hub.domain.Location.getProperty(java.lang.String),
public java.lang.Object com.hubitat.hub.domain.Location.propertyMissing(java.lang.String),
public void com.hubitat.hub.domain.Location.setProperty(java.lang.String,java.lang.Object),
public java.lang.Object com.hubitat.hub.domain.Location.invokeMethod(java.lang.String,java.lang.Object),

Getters/Main methods:
public void com.hubitat.hub.domain.Location.createVariable(java.lang.String),
public void com.hubitat.hub.domain.Location.createVariable(java.lang.String,java.util.List),
public void com.hubitat.hub.domain.Location.removeVariable(java.lang.String),

++ public boolean com.hubitat.hub.domain.Location.getContactBookEnabled(),
++ public com.hubitat.hub.domain.Mode com.hubitat.hub.domain.Location.getCurrentMode(),
public java.lang.String com.hubitat.hub.domain.Location.getFormattedLatitude(),
public java.lang.String com.hubitat.hub.domain.Location.getFormattedLongitude(),
public java.lang.String com.hubitat.hub.domain.Location.getHelloHome(),
++ public java.util.List com.hubitat.hub.domain.Location.getHubs(),
++ public java.lang.Long com.hubitat.hub.domain.Location.getId(),
++ public java.math.BigDecimal com.hubitat.hub.domain.Location.getLatitude(),
++ public java.math.BigDecimal com.hubitat.hub.domain.Location.getLongitude(),
++ public java.lang.String com.hubitat.hub.domain.Location.getMode(),
++ public java.util.List com.hubitat.hub.domain.Location.getModes(),
++ public java.lang.String com.hubitat.hub.domain.Location.getName(),
public java.util.Date com.hubitat.hub.domain.Location.getSunrise(),
public java.util.Date com.hubitat.hub.domain.Location.getSunset(),
++ public java.lang.String com.hubitat.hub.domain.Location.getTemperatureScale(),
public static java.lang.String com.hubitat.hub.domain.Location.getTimeFormat(),
++ public java.util.TimeZone com.hubitat.hub.domain.Location.getTimeZone(),
public java.util.List com.hubitat.hub.domain.Location.getTrueModes(),
public java.util.List com.hubitat.hub.domain.Location.getVariableValues(java.lang.String),
public java.lang.Long com.hubitat.hub.domain.Location.getVersion(),
++ public java.lang.String com.hubitat.hub.domain.Location.getZipCode(),
public boolean com.hubitat.hub.domain.Location.isContactBookEnabled(),

Setters:
public void com.hubitat.hub.domain.Location.setContactBookEnabled(boolean),
public void com.hubitat.hub.domain.Location.setCurrentMode(com.hubitat.hub.domain.Mode),
public void com.hubitat.hub.domain.Location.setHelloHome(java.lang.String),
public void com.hubitat.hub.domain.Location.setHubs(java.util.List),
public void com.hubitat.hub.domain.Location.setId(java.lang.Long),
public void com.hubitat.hub.domain.Location.setLatitude(java.math.BigDecimal),
public void com.hubitat.hub.domain.Location.setLongitude(java.math.BigDecimal),
public void com.hubitat.hub.domain.Location.setMode(java.lang.String),
public void com.hubitat.hub.domain.Location.setName(java.lang.String),
public void com.hubitat.hub.domain.Location.setSunrise(java.util.Date),
public void com.hubitat.hub.domain.Location.setSunset(java.util.Date),
public void com.hubitat.hub.domain.Location.setTemperatureScale(java.lang.String),
public void com.hubitat.hub.domain.Location.setTimeZone(java.util.TimeZone),
public void com.hubitat.hub.domain.Location.setTrueModes(java.util.List),
public void com.hubitat.hub.domain.Location.setVersion(java.lang.Long),
public void com.hubitat.hub.domain.Location.setZipCode(java.lang.String),
 */

/**
 * A Location is user’s geo-location, such as “Home” or “office”.
 *
 * Locations do not have to have a Hub, but generally do.
 * All App and Device objects are injected with a location property
 * that is the Location into which the App or Device was installed.
 */
trait Location {
    abstract boolean getContactBookEnabled()

    abstract Mode getMode()
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
}
