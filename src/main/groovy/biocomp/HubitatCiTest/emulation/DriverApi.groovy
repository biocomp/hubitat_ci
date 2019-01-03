package biocomp.hubitatCiTest.emulation

trait DriverApi extends AppAndDriverCommonApi
{
    abstract void sendHubCommand(HubAction hubAction)

    abstract Map getState()
    abstract Zwave getZwave()
    abstract Zigbee getZigbee()
    abstract Object getParent()
    abstract void updateDataValue(String name, String value)
    abstract String getDataValue(String name)
    abstract String getDeviceDataByName(String name)
    abstract HubAction response(String cmd)
    abstract HubAction response(zwave.Command cmd)
    abstract HubMultiAction response(List cmds)
    abstract Short getZwaveHubNodeId()
    abstract void sendEvent(Map properties)
    abstract List<Event> eventsSince(Date startDate, Map options = null)
    abstract void telnetConnect(Map options, String ip, int port, String username, String password)
    abstract void telnetConnect(String ip, int port, String username, String password)
    abstract void telnetClose()
    abstract Map createEvent(Map options)
    abstract List<String> delayBetween(List<String> cmds, Long delay)
    abstract List<String> delayBetween(List<String> cmds)
    abstract ChildDeviceWrapper addChildDevice(String typeName, String deviceNetworkId, Map properties = [:])
    abstract ChildDeviceWrapper addChildDevice(String namespace, String typeName, String deviceNetworkId, Map properties = [:])
    abstract List<ChildDeviceWrapper> getChildDevices()
    abstract ChildDeviceWrapper getChildDevice(String deviceNetworkId)
    abstract void deleteChildDevice(String deviceNetworkId)
}

