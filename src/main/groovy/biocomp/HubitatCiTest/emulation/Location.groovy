package biocomp.hubitatCiTest.emulation

/**
 * A Location is user’s geo-location, such as “Home” or “office”.
 *
 * Locations do not have to have a Hub, but generally do.
 * All App and Device objects are injected with a location property
 * that is the Location into which the App or Device was installed.
 */
trait Location {
    abstract boolean getContactBookEnabled()
    abstract Mode getCurrentMode()
    abstract List<Mode> getModes()
    abstract String getName()

    /**
     * @throws Exception if mode is invalid
     */
    abstract void setMode(String mode)

    /**
     * @throws Exception if mode is invalid
     */
    abstract void setMode(Mode mode)

    /**
     * @return "C" or "F"
     */
    abstract String getTemperatureScale()

    abstract TimeZone getTimeZone()

    abstract String getZipCode()
}
