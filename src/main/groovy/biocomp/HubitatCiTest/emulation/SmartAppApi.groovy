package biocomp.hubitatCiTest.emulation

trait SmartAppApi
{
    abstract def definition(def definitionsMap)
    abstract def preferences(def preferenceCallback)
    abstract void sendHubCommand(HubAction action)

    abstract def subscribe(def device, String propertyName, Closure<Event> handler)
    abstract def subscribe(def device, Closure<Event> handler)

    abstract def getPresenceDevices()
    abstract def getOmniDevices()
    abstract def getMotionDevices()
    abstract def getContactDevices()
    abstract def getAccelerationDevices()
    abstract def getMultiSensors()
    abstract def getOmniSensors()
    abstract def getSwitchDevices()
    abstract def getDimmerDevices()
    abstract def getLocks()
    abstract def getModes()
    abstract Location getLocation()
    abstract String getIp()
    abstract boolean getEnabled()
    abstract boolean getLogEnable()

    /*
    Returns a list of all child devices. An example use would be in Service Manager SmartApps.
     */
    abstract List getChildDevices(boolean includeVirtualDevices = false)
}