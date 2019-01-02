package biocomp.hubitatCiTest.emulation

interface SmartAppApi
{
    def definition(def definitionsMap)
    def preferences(def preferenceCallback)
    void sendHubCommand(HubAction action)

    def subscribe(def device, String propertyName, def handler)
    def subscribe(def device, def handler)

    def getPresenceDevices()
    def getOmniDevices()
    def getMotionDevices()
    def getContactDevices()
    def getAccelerationDevices()
    def getMultiSensors()
    def getOmniSensors()
    def getSwitchDevices()
    def getDimmerDevices()
    def getLocks()
    def getModes()
    Location getLocation()
    String getIp()
    boolean getEnabled()
    boolean getLogEnable()
}