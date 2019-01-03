package biocomp.hubitatCiTest.emulation

/**
 * Methods reflected from driver's code:
 * Methods:

 * # All derived from BaseExecutor skipped #
 * # DeviceExecutor #
 * public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,java.lang.String),
 * public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,java.lang.String,java.lang.String),
 * public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,java.lang.String,java.util.Map),
 * public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,java.lang.String,java.lang.String,java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.attribute(java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.attribute(java.lang.String,java.lang.String,java.util.List),
 * public void com.hubitat.hub.executor.DeviceExecutor.attributeState(java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.attributeState(java.util.Map,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.capability(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.command(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.command(java.lang.String,java.util.List),
 * public void com.hubitat.hub.executor.DeviceExecutor.controlTile(java.util.Map,java.lang.String,java.lang.String,java.lang.String,groovy.lang.Closure),
 * public static java.util.Map com.hubitat.hub.executor.DeviceExecutor.createEvent(java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.definition(java.util.Map,groovy.lang.Closure),
 * public java.util.List com.hubitat.hub.executor.DeviceExecutor.delayBetween(java.util.List),
 * public java.util.List com.hubitat.hub.executor.DeviceExecutor.delayBetween(java.util.List,java.lang.Long),
 * public void com.hubitat.hub.executor.DeviceExecutor.deleteChildDevice(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.details(java.lang.String[]),
 * public void com.hubitat.hub.executor.DeviceExecutor.details(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.details(java.util.List),
 * public static boolean com.hubitat.hub.executor.DeviceExecutor.displayed(java.lang.String,boolean),
 * public void com.hubitat.hub.executor.DeviceExecutor.eventStreamClose(),
 * public void com.hubitat.hub.executor.DeviceExecutor.eventStreamConnect(java.lang.String,java.lang.String),
 * public java.util.List com.hubitat.hub.executor.DeviceExecutor.eventsSince(java.util.Date),
 * public java.util.List com.hubitat.hub.executor.DeviceExecutor.eventsSince(java.util.Date,java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.fingerprint(java.util.Map),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getAttributes(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getCapabilities(),
 * public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.getChildDevice(java.lang.String),
 * public java.util.List com.hubitat.hub.executor.DeviceExecutor.getChildDevices(),
 * public com.hubitat.hub.controller.ChromeCast com.hubitat.hub.executor.DeviceExecutor.getChromeCast(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getCommands(),
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.getDataValue(java.lang.String),
 * public java.util.Map com.hubitat.hub.executor.DeviceExecutor.getDefinitionData(),
 * public com.hubitat.app.DeviceWrapper com.hubitat.hub.executor.DeviceExecutor.getDevice(),
 * public com.hubitat.app.DeviceWrapper com.hubitat.hub.executor.DeviceExecutor.getDeviceById(java.lang.Long),
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.getDeviceDataByName(java.lang.String),
 * public static java.lang.String com.hubitat.hub.executor.DeviceExecutor.getEXECUTOR_TYPE(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getFingerprints(),
 * public static java.lang.String com.hubitat.hub.executor.DeviceExecutor.getLinkText(com.hubitat.app.DeviceWrapper),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getParent(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getPreferences(),
 * public java.util.Map com.hubitat.hub.executor.DeviceExecutor.getState(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getTiles(),
 * public com.hubitat.zigbee.Zigbee com.hubitat.hub.executor.DeviceExecutor.getZigbee(),
 * public hubitat.zwave.Zwave com.hubitat.hub.executor.DeviceExecutor.getZwave(),
 * public static java.lang.Short com.hubitat.hub.executor.DeviceExecutor.getZwaveHubNodeId(),
 * public void com.hubitat.hub.executor.DeviceExecutor.graphTile(java.util.Map),
 * public java.lang.Long com.hubitat.hub.executor.DeviceExecutor.hexStrToSignedInt(java.lang.String),
 * public java.lang.Long com.hubitat.hub.executor.DeviceExecutor.hexStrToUnsignedInt(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.image(java.util.Map),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.input(java.util.Map),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.input(java.lang.String,java.lang.String),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.input(java.util.Map,java.lang.String,java.lang.String),
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.intToHexStr(java.lang.Long),
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.intToHexStr(java.lang.Long,java.lang.Integer),
 * public boolean com.hubitat.hub.executor.DeviceExecutor.isStateChange(com.hubitat.app.DeviceWrapper,java.lang.String,java.lang.String),
 * public boolean com.hubitat.hub.executor.DeviceExecutor.isSystemTypeOrHubDeveloper(),
 * public void com.hubitat.hub.executor.DeviceExecutor.main(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.main(java.util.List),
 * public void com.hubitat.hub.executor.DeviceExecutor.metadata(groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.multiAttributeTile(java.util.Map,groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.preferences(groovy.lang.Closure),
 * public static hubitat.device.HubAction com.hubitat.hub.executor.DeviceExecutor.response(hubitat.zwave.Command),
 * public static hubitat.device.HubAction com.hubitat.hub.executor.DeviceExecutor.response(java.lang.String),
 * public static hubitat.device.HubMultiAction com.hubitat.hub.executor.DeviceExecutor.response(java.util.List),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.section(groovy.lang.Closure),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.section(java.lang.String,groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.sendEvent(java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.setAttributes(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setCapabilities(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setChromeCast(com.hubitat.hub.controller.ChromeCast),
 * public void com.hubitat.hub.executor.DeviceExecutor.setCommands(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setDefinitionData(java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.setDevice(com.hubitat.app.DeviceWrapper),
 * public void com.hubitat.hub.executor.DeviceExecutor.setFingerprints(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setPreferences(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setTiles(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setZigbee(com.hubitat.zigbee.Zigbee),
 * public void com.hubitat.hub.executor.DeviceExecutor.setZwave(hubitat.zwave.Zwave),
 * public void com.hubitat.hub.executor.DeviceExecutor.simulator(groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.lang.String,java.lang.String,groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.util.Map,java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.util.Map,java.lang.String,java.lang.String,groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.state(java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.state(java.util.Map,java.lang.String),
 * public java.util.Map com.hubitat.hub.executor.DeviceExecutor.stringToMap(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.telnetClose(),
 * public void com.hubitat.hub.executor.DeviceExecutor.telnetConnect(java.lang.String,int,java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.telnetConnect(java.util.Map,java.lang.String,int,java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.lang.String,groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.util.Map,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.util.Map,java.lang.String,groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.tiles(groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.tiles(java.util.Map,groovy.lang.Closure),
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.toString(),
 * public void com.hubitat.hub.executor.DeviceExecutor.updateDataValue(java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.lang.String,java.lang.String,groovy.lang.Closure),
 * public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.util.Map,java.lang.String,java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.util.Map,java.lang.String,java.lang.String,groovy.lang.Closure)
 * */

trait DeviceExecutorApi extends BaseExecutorApi
{
    // Command can be invoked by name
    // Needs to be defined by user
    //     Object <command name([arguments])>

    // Need to define parse() method
    // Needs to be defined by user
    //
    // Map parse(String description)
    // List<Map> parse(String description)
    // HubAction parse(String description)
    // List<HubAction> parse(String description)

    /**
     * Called within the definition() method to declare that this Device Handler supports an attribute not defined by any of its declared capabilities.
     *
     * For any supported attribute, it is expected that the Device Handler creates and sends Events with the name of the attribute in the parse() method.
     * @param attributeName
     * @param attributeType - vailable types are “string”, “number”, and “enum”
     * @param possibleValues -  the possible values for this attribute. Only valid with the "enum" attributeType.
     */
    abstract void attribute(String attributeName, String attributeType, List<String> possibleValues = null)

    /**
     * Called in the definition() method to define that this device supports the specified capability.
     *
     * Whatever commands and attributes defined by that capability should be implemented by the Device Handler.
     * For example, the “Switch” capability specifies support for the “switch” attribute and the “on” and “off”
     * commands - any Device Handler supporting the “Switch” capability must define methods for the commands,
     * and support the “switch” attribute by creating the appropriate
     * Events (with the name of the attribute, e.g., “switch”)
     *
     * @param capabilityName - the name of the capability. This is the long-form name of the Capability name,
     * not the “preferences reference”.
     */
    abstract void capability(String capabilityName)

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
}

