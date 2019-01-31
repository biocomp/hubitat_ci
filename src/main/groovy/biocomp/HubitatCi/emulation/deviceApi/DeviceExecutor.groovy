package biocomp.hubitatCi.emulation.deviceApi

import biocomp.hubitatCi.emulation.commonApi.*
import biocomp.hubitatCi.emulation.deviceApi.zigbee.Zigbee
import biocomp.hubitatCi.emulation.deviceApi.zwave.Zwave

/**
 * Real methods:
 * # All derived from BaseExecutor skipped #
 *
 * Meta/Common:
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.toString(),
 *
 * Main methods:
 * ++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,
 * java.lang.String),
 * ++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,java.lang.String,java.lang.String),
 * ++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,java.lang.String,java.util.Map),
 * ++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.addChildDevice(java.lang.String,java.lang.String,java.lang.String,java.util.Map),
 *
 * ++ public static java.util.Map com.hubitat.hub.executor.DeviceExecutor.createEvent(java.util.Map),
 * ++ public java.util.List com.hubitat.hub.executor.DeviceExecutor.delayBetween(java.util.List),
 * ++ public java.util.List com.hubitat.hub.executor.DeviceExecutor.delayBetween(java.util.List,java.lang.Long),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.deleteChildDevice(java.lang.String),
 * public static boolean com.hubitat.hub.executor.DeviceExecutor.displayed(java.lang.String,boolean),
 * public void com.hubitat.hub.executor.DeviceExecutor.eventStreamClose(),
 * public void com.hubitat.hub.executor.DeviceExecutor.eventStreamConnect(java.lang.String,java.lang.String),
 * ++ public java.util.List com.hubitat.hub.executor.DeviceExecutor.eventsSince(java.util.Date),
 * ++ public java.util.List com.hubitat.hub.executor.DeviceExecutor.eventsSince(java.util.Date,java.util.Map),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getAttributes(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getCapabilities(),
 * ++ public com.hubitat.appApi.ChildDeviceWrapper com.hubitat.hub.executor.DeviceExecutor.getChildDevice(java.lang
 * .String),
 * ++ public java.util.List com.hubitat.hub.executor.DeviceExecutor.getChildDevices(),
 * public com.hubitat.hub.controller.ChromeCast com.hubitat.hub.executor.DeviceExecutor.getChromeCast(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getCommands(),
 * ++ public java.lang.String com.hubitat.hub.executor.DeviceExecutor.getDataValue(java.lang.String),
 * public java.util.Map com.hubitat.hub.executor.DeviceExecutor.getDefinitionData(),
 * ++ public com.hubitat.appApi.DeviceWrapper com.hubitat.hub.executor.DeviceExecutor.getDevice(),
 * public com.hubitat.appApi.DeviceWrapper com.hubitat.hub.executor.DeviceExecutor.getDeviceById(java.lang.Long),
 * ++ public java.lang.String com.hubitat.hub.executor.DeviceExecutor.getDeviceDataByName(java.lang.String),
 * public static java.lang.String com.hubitat.hub.executor.DeviceExecutor.getEXECUTOR_TYPE(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getFingerprints(),
 * public static java.lang.String com.hubitat.hub.executor.DeviceExecutor.getLinkText(com.hubitat.appApi.DeviceWrapper),
 * ++ public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getParent(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getPreferences(),
 * ++ public java.util.Map com.hubitat.hub.executor.DeviceExecutor.getState(),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.getTiles(),
 * ++ public com.hubitat.zigbee.Zigbee com.hubitat.hub.executor.DeviceExecutor.getZigbee(),
 * ++ public hubitat.zwave.Zwave com.hubitat.hub.executor.DeviceExecutor.getZwave(),
 * public static java.lang.Short com.hubitat.hub.executor.DeviceExecutor.getZwaveHubNodeId(),
 * public void com.hubitat.hub.executor.DeviceExecutor.graphTile(java.util.Map),
 * public java.lang.Long com.hubitat.hub.executor.DeviceExecutor.hexStrToSignedInt(java.lang.String),
 * public java.lang.Long com.hubitat.hub.executor.DeviceExecutor.hexStrToUnsignedInt(java.lang.String),
 * public void com.hubitat.hub.executor.DeviceExecutor.image(java.util.Map),
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.intToHexStr(java.lang.Long),
 * public java.lang.String com.hubitat.hub.executor.DeviceExecutor.intToHexStr(java.lang.Long,java.lang.Integer),
 * public boolean com.hubitat.hub.executor.DeviceExecutor.isStateChange(com.hubitat.appApi.DeviceWrapper,java.lang.String,java.lang.String),
 * public boolean com.hubitat.hub.executor.DeviceExecutor.isSystemTypeOrHubDeveloper(),
 * ++ public static hubitat.device.HubAction com.hubitat.hub.executor.DeviceExecutor.response(hubitat.zwave.Command),
 * ++ public static hubitat.device.HubAction com.hubitat.hub.executor.DeviceExecutor.response(java.lang.String),
 * ++ public static hubitat.device.HubMultiAction com.hubitat.hub.executor.DeviceExecutor.response(java.util.List),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.sendEvent(java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.simulator(groovy.lang.Closure),
 * ++ public java.util.Map com.hubitat.hub.executor.DeviceExecutor.stringToMap(java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.telnetClose(),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.telnetConnect(java.lang.String,int,java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.telnetConnect(java.util.Map,java.lang.String,int,java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.updateDataValue(java.lang.String,java.lang.String),
 *
 *
 * Methods for metadata definition:
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.metadata(groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.tiles(groovy.lang.Closure),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.tiles(java.util.Map,groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.main(java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.main(java.util.List),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.lang.String,java.lang.String,groovy.lang
 * .Closure),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.util.Map,java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.standardTile(java.util.Map,java.lang.String,java.lang.String,
 * groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.lang.String,java.lang.String,groovy.lang
 * .Closure),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.util.Map,java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.valueTile(java.util.Map,java.lang.String,java.lang.String,
 * groovy.lang.Closure)
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.controlTile(java.util.Map,java.lang.String,java.lang.String,
 * java.lang.String,groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.multiAttributeTile(java.util.Map,groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.lang.String,groovy.lang.Closure),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.util.Map,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.tileAttribute(java.util.Map,java.lang.String,groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.state(java.util.Map),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.state(java.util.Map,java.lang.String),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.attributeState(java.util.Map),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.attributeState(java.util.Map,java.lang.String),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.preferences(groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.capability(java.lang.String),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.command(java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.command(java.lang.String,java.util.List),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.attribute(java.lang.String,java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.attribute(java.lang.String,java.lang.String,java.util.List),
 *
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.definition(java.util.Map,groovy.lang.Closure),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.fingerprint(java.util.Map),
 *
 * ++ public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.input(java.util.Map),
 * ++ public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.input(java.lang.String,java.lang.String),
 * ++ public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.input(java.util.Map,java.lang.String,java.lang
 * .String),
 *
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.details(java.lang.String[]),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.details(java.lang.String),
 * ++ public void com.hubitat.hub.executor.DeviceExecutor.details(java.util.List),
 *
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.section(groovy.lang.Closure),
 * public java.lang.Object com.hubitat.hub.executor.DeviceExecutor.section(java.lang.String,groovy.lang.Closure),
 *
 * Setters:
 * public void com.hubitat.hub.executor.DeviceExecutor.setAttributes(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setCapabilities(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setChromeCast(com.hubitat.hub.controller.ChromeCast),
 * public void com.hubitat.hub.executor.DeviceExecutor.setCommands(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setDefinitionData(java.util.Map),
 * public void com.hubitat.hub.executor.DeviceExecutor.setDevice(com.hubitat.appApi.DeviceWrapper),
 * public void com.hubitat.hub.executor.DeviceExecutor.setFingerprints(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setPreferences(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setTiles(java.lang.Object),
 * public void com.hubitat.hub.executor.DeviceExecutor.setZigbee(com.hubitat.zigbee.Zigbee),
 * public void com.hubitat.hub.executor.DeviceExecutor.setZwave(hubitat.zwave.Zwave),
 * */

trait DeviceExecutor implements biocomp.hubitatCi.emulation.commonApi.BaseExecutor
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

    /**
     * The Device object, from which its current properties and history can be accessed.
     *
     */
    abstract DeviceWrapper getDevice()

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

    /**
     * @throws NotFoundException
     */
    abstract void deleteChildDevice(String deviceNetworkId)

    /**
     * Piece of metadata(), defines major device's properties.
     *
     * @param options.supported parameters are:
     *
     * name (String) The name of the Device Handler.
     * namespace (String) The namespace for this Device Handler. This should be your github user name. This is used
     * when looking up Device Handlers by name to ensure the correct one is found, even if someone else has used the same name.
     * author (String) The author of this Device Handler.
     *
     * @param makeContents
     */
    abstract void definition(Map options, Closure makeContents)

    /**
     * Defines metadata for the device.
     *
     * @param makeContents
     */
    abstract void metadata(Closure makeContents)

    /**
     * Define that device supports this command (use inside closure passed into definition())
     *
     * @param commandName - The name of the command. You must also define a method in your Device Handler with the same name.
     * @param parameterTypes - Optional. An ordered list of the parameter types for the command method, if needed.
     */
    abstract void command(String commandName, List parameterTypes)

    /**
     * Fingerprints for z-wave and zigbee devices.
     * (use inside closure passed into definition())
     *
     * @see https://docs.smartthings.com/en/latest/device-type-developers-guide/definition-metadata.html for more info.
     * @param options
     */
    abstract void fingerprint(Map options)

    /**
     * Tiles define how device is presented in UI to the user.
     * (use inside closure passed into metadata())
     *
     * @param options. Valid options:
     *      scale (Integer) A value of 1 (the default) enables the 3 x Unlimited grid;
 *              a value of 2 enables the 6 x Unlimited grid:
     * @param makeContents
     */
    abstract void tiles(Map options, Closure makeContents)

    /**
     * Define which tile is main tile (use inside closure passed into tiles())
     */
    abstract void main(String tileTitle)

    /**
     * Define which tiles are main tiles? (use inside closure passed into tiles())
     * @param multipleTileTitles - create this many tiles, with these titles
     */
    abstract void main(List multipleTileTitles)

    /**
     * Define which tile is detail tile (use inside closure passed into tiles())
     * @param tileStuff
     */
    abstract void details(String tileTitle)

    /**
     * Define which tiles are detail tiless (and in which order) (use inside closure passed into tiles())
     * @param multipleTileTitles - create this many tiles, with these titles
     */
    abstract void details(List multipleTileTitles)

    /**
     * Defines standard tile (use inside closure passed into tiles())
     *
     * Use a Standard Tile for attributes that have discrete, specific values.
     * For example, a switch is either “on” or “off”; a moisture sensor
     * is “wet” or “dry”; a contact sensor is “open” or “closed”.
     *
     * @param options. Valid options are:
     *      width (Integer?) default is 1. Common for all tiles.
     *      height (Integer?) default is 1. Common for all tiles.
     *      canChangeIcon (boolean) default = false. Allows user to change icon.
     *      decoration (String) "ring" is default, "flat" is also supported.
     */
    abstract void standardTile(options = [:], String name, String associatedAttributeName, Closure makeContents = null)


    /**
     * Defines value tile (use inside closure passed into tiles())
     *
     * Use a Value Tile for attributes that have non-discrete values.
     * Typical examples include temperature, humidity, or power values.
     *
     * @param options. Valid options are:
     *      width (Integer?) default is 1. Common for all tiles.
     *      height (Integer?) default is 1. Common for all tiles.
     *      canChangeIcon (boolean) default = false. Allows user to change icon.
     *      decoration (String) "ring" is default, "flat" is also supported.
     * @param name
     * @param associatedAttributeName
     * @param makeContents
     */
    abstract void valueTile(Map options = [:], String name, String associatedAttributeName, Closure makeContents = null)

    /**
     * Define a slider or color control tile (use inside closure passed into tiles())
     *
     * Use a Slider Control Tile to display a tile that shows a value along a
     * range, and allows the user to adjust the value using the slider control.
     *
     * @param options. Valid options are:
     *      width (Integer?) default is 1. Common for all tiles.
     *      height (Integer?) default is 1. Common for all tiles.
     *      canChangeIcon (boolean) default = false. Allows user to change icon.
     *      decoration (String) "ring" is default, "flat" is also supported.
     *      range (String) - default range is 0-100, but can be changed. Format: range:"(20..80)")
     *      inactiveLabel (boolean) default - true.
     * @param name
     * @param associatedAttributeName
     * @param makeContents
     */
    abstract void controlTile(Map options, String name, String associatedAttributeName, Closure makeContents = null)

    /**
     * Define a multi-attribute tile (use inside closure passed into tiles())
     *
     * Use a Slider Control Tile to display a tile that shows a value along a
     * range, and allows the user to adjust the value using the slider control.
     *
     * @param options. Valid options are:
     *      width (Integer?) MUST BE 6
     *      height (Integer?) MUST BE 4
     *      canChangeIcon (boolean) default = false. Allows user to change icon.
     *      decoration (String) "ring" is default, "flat" is also supported.
     *      inactiveLabel (boolean) default - true.
     *      type (String). One of:
     *          "lighting"
     *          "thermostat"
     *          "mediaPlayer"
     *          "generic"
     * @param name
     * @param associatedAttributeName
     * @param makeContents
     */
    abstract void multiAttributeTile(Map options, Closure makeContents = null)

    /**
     * Bind tile to device's state (for single-attribute tile).
     * (use inside closure passed into a specific tile creation method())
     *
     * @param options: Valid options are:
     *      label (String) what to print on a tile for this attribute.
     *             Could be '${currentValue}' (single quotes are required!) to reflect value dynamically.
     *             Could be '${name}' (single quotes are required!) if value has named values such as "on" and "off".
     *      icon (String) - url to external or path to local icon.
     *      backgroundColor (String) hex color like "#153591"
     *      action - what to do when tile in this state was interacted with. Two forms supported:
     *          "<capability>.<command>"
     *          "<command>" (This form is required for custom (non-capability) commands.)
     *      nextState (String) - name of next state, after tile was interacted with.
     *      unit (String) - units of the value
     *      backgroundColors Map<Object, String> - map from value to color. Can be used instead of backgroundColor.
     *          Example:
     *          backgroundColors:[
     *             [value: 31, color: "#153591"],
     *             [value: 44, color: "#1e9cbb"],
     *             [value: 59, color: "#90d2a7"],
     *             [value: 74, color: "#44b621"],
     *             [value: 84, color: "#f1d801"],
     *             [value: 95, color: "#d04e00"],
     *             [value: 96, color: "#bc2323"]
     *         ]
     *      defaultState (boolean) default - false. If true, this state is used when no matching state can be found.
     * @param stateNameOrAttributeNameOrValue - specific value of an attribute or its name. Or virtual state name (to
     * use with nextState option)
     */
    abstract void state(Map options, String stateNameOrAttributeNameOrValue)

    /**
     * Same as state() above for multi-attribute tiles.
     * @param options. See state() method options parameter.
     * @param stateNameOrAttributeNameOrValue
     */
    abstract void attributeState(Map options, String stateNameOrAttributeNameOrValue)

    /**
     * Add attribute to multi-attribute tile (use inside multiAttributeTile()'s closure)
     *
     * @param options. Valid options:
     *      key (String). Valid values:
     *          COLOR_CONTROL 	Displays a color palette for the user to select a color from.
     *          COOLING_SETPOINT 	Used by the Thermostat Multi-Attribute Tile.
     *          HEATING_SETPOINT 	Used by the Thermostat Multi-Attribute Tile.
     *          MARQUEE 	Displays a rotating marquee message beneath the PRIMARY_CONTROL.
     *          MEDIA_MUTED 	Allows the user to press the volume icon to mute on a Multimedia Multi-Attribute Tile.
     *          MEDIA_STATUS 	Used to display and control the current play status (playing, paused, stopped) on a Multimedia Multi-Attribute Tile.
     *          NEXT_TRACK 	Renders a control for going to the next track on a Multimedia Multi-Attribute Tile.
     *          OPERATING_STATE 	Used by the Thermostat Multi-Attribute Tile.
     *          PREVIOUS_TRACK 	Renders a control for going to the previous track on a Multimedia Multi-Attribute Tile.
     *          PRIMARY_CONTROL 	All tiles must define a PRIMARY_CONTROL. Controls the background color of tile (except for the Thermostat Multi-Attribute Tile), and specifies the attribute to show on the Device list views.
     *          SECONDARY_CONTROL 	Used to display textual information below the PRIMARY_CONTROL.
     *          SLIDER_CONTROL 	Displays a slider input; typically useful for attributes like bulb level or volume.
     *          THERMOSTAT_MODE 	Used by the Thermostat Multi-Attribute Tile.
     *          VALUE_CONTROL 	Renders Up and Down controls for increasing and decreasing an attribute’s value by 1.
     * @param associatedAttributeName
     * @param makeContents
     */
    abstract void tileAttribute(Map options = [:], String associatedAttributeName, Closure makeContents = null)

    /**
     * Define device's preferences (to be used inside closure passed into metadata())
     */
    abstract void preferences(Closure makeContents)

    /**
     * Define a setting inside preferences.
     * (to be used inside closure passed into preferences())
     *
     * @param options. Valid options are:
     *      name (String)
     *      type (String). Supported values:
     *          bool
     *          decimal
     *          email
     *          enum
     *          number
     *          password
     *          phone
     *          time
     *          text
     *      title (String)
     *      description (String)
     *      required (boolean)  true
     *      displayDuringSetup (boolean) default - false
     *      range (String) valid values range. Format "*..*", "10..100"
     *      options (List) - list of values if type == "enum"
     */
    abstract def input(Map options, String name = null, String type = null)

    abstract ChildDeviceWrapper getChildDevice(String deviceNetworkId)
    abstract List<ChildDeviceWrapper> getChildDevices()
}

