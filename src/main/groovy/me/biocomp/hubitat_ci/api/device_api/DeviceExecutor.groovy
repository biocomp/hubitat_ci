package me.biocomp.hubitat_ci.api.device_api

import me.biocomp.hubitat_ci.api.Command
import me.biocomp.hubitat_ci.api.common_api.*
import me.biocomp.hubitat_ci.api.device_api.zigbee.Zigbee
import me.biocomp.hubitat_ci.api.device_api.zwave.Zwave

interface DeviceTileAttribute {
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

    /**
     * Same as state() above for multi-attribute tiles.
     * @param options. See state() method options parameter.
     * @param stateNameOrAttributeNameOrValue
     */
    abstract void attributeState(Map options, String stateNameOrAttributeNameOrValue)
    abstract void attributeState(Map options)

}

trait DeviceMultiAttributeTile implements
        DeviceTileAttribute
{
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

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
    abstract void tileAttribute(Map options, String associatedAttributeName, Closure makeContents = null)

    abstract void tileAttribute(String associatedAttributeName, Closure makeContents = null)
}

trait DeviceTile {
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

    /**
     * Bind tile to device's state (for single-attribute tile).
     * (use inside closure passed into a specific tile creation method())
     *
     * @param options : Valid options are:
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
    abstract void state(Map options, String stateNameOrAttributeNameOrValue = null)
}

trait DeviceDefinition {
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

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
     * Define that device supports this command (use inside closure passed into definition())
     *
     * @param commandName - The name of the command. You must also define a method in your Device Handler with the same name.
     * @param parameterTypes - Optional. An ordered list of the parameter types for the command method, if needed.
     */
    abstract void command(String commandName, List parameterTypes = [])

    /**
     * Fingerprints for z-wave and zigbee devices.
     * (use inside closure passed into definition())
     *
     * @see https://docs.smartthings.com/en/latest/device-type-developers-guide/definition-metadata.html for more info.
     * @param options
     */
    abstract void fingerprint(Map options)
}

trait DeviceTiles extends
        DeviceMultiAttributeTile implements
        DeviceTile
{
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

    /**
     * Define which tile is main tile (use inside closure passed into tiles())*/
    abstract void main(String tileTitle)

    /**
     * Define which tiles are main tiles? (use inside closure passed into tiles())
     * @param multipleTileTitles - create this many tiles, with these titles
     */
    abstract void main(List multipleTileTitles)

    /**
     * Define which tile is detail tile (use inside closure passed into tiles())
     * @param tileTitle
     */
    abstract void details(String tileTitle)

    /**
     * Define which tile is detail tile (use inside closure passed into tiles())
     * @param tileTitles
     */
    abstract void details(String[] tileTitles)

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
    abstract void standardTile(
            Map options, String name, String associatedAttributeName,
            @DelegatesTo(DeviceTile) Closure makeContents = null)

    abstract void standardTile(
            String name, String associatedAttributeName,
            @DelegatesTo(DeviceTile) Closure makeContents = null)


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
    abstract void valueTile(
            Map options, String name, String associatedAttributeName,
            @DelegatesTo(DeviceTile) Closure makeContents = null)

    abstract void valueTile(
            String name, String associatedAttributeName,
            @DelegatesTo(DeviceTile) Closure makeContents = null)

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
    abstract void controlTile(
            Map options, String name, String associatedAttributeName, String controlType,
            @DelegatesTo(DeviceTile) Closure makeContents)

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
    abstract void multiAttributeTile(Map options, @DelegatesTo(DeviceMultiAttributeTile) Closure makeContents)
}

trait DevicePreferences {
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

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
     *          paragraph - it's not clear that it's "supported". I saw scripts using it and Hubitat displays it as text input.
     *      title (String)
     *      description (String)
     *      required (boolean)  true
     *      displayDuringSetup (boolean) default - false
     *      range (String) valid values range. Format "*..*", "10..100"
     *      options (List) - list of values if type == "enum"
     *      defaultValue (Object) - undocumented but works in Hubitat (actually provides the default in UI).
     */
    abstract def input(Map options, String name, String type)
    abstract def input(String name, String type)
    abstract def input(Map options)

    /**
     * Really, undocumented anywhere.
     * Not supported in SmartThings, but used (successfully?) by at least
     * one hubitat script in the wild.
     * And obviously exists in real Hubitat DeviceExecutror class.
     * @param name of the section
     * @param makeContents
     */
    abstract def section(String name = null, groovy.lang.Closure makeContents)
}

/**
 * What can be declared inside metadata's closure.*/
trait DeviceMetadata implements
        DeviceDefinition,
        DeviceTiles,
        DevicePreferences
{
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

    /**
     * Piece of metadata(), defines major device's properties.
     *
     * @param options.supported parameters are:
     * <ul>
     * <li><code>name</code> (String) The name of the Device Handler.</li>
     * <li><code>namespace</code> (String) The namespace for this Device Handler. This should be your github user name. This is used
     * when looking up Device Handlers by name to ensure the correct one is found, even if someone else has used the same name.
     * <li><code>author</code> (String) The author of this Device Handler.</li>
     * </ul>
     * @param makeContents
     */
    abstract void definition(Map options, @DelegatesTo(DeviceDefinition) Closure makeContents)

    /**
     * Define device's preferences (to be used inside closure passed into metadata())*/
    abstract void preferences(@DelegatesTo(DevicePreferences) Closure makeContents)

    /**
     * Tiles define how device is presented in UI to the user.
     * (use inside closure passed into metadata())
     *
     * @param options. Valid options:
     *      scale (Integer) A value of 1 (the default) enables the 3 x Unlimited grid;
     *              a value of 2 enables the 6 x Unlimited grid:
     * @param makeContents
     */
    abstract void tiles(Map options = [:], @DelegatesTo(DeviceTiles) Closure makeContents)
}

interface DeviceMetadataSource extends
        DeviceMetadata
{
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

    /**
     * Defines metadata for the device.
     *
     * @param makeContents
     */
    abstract void metadata(@DelegatesTo(DeviceMetadata) Closure makeContents)
}

trait DeviceExecutor implements
        me.biocomp.hubitat_ci.api.common_api.BaseExecutor,
        DeviceMetadataSource
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



    abstract Map getState()

    abstract Zwave getZwave()

    abstract Zigbee getZigbee()

    abstract Object getParent()

    abstract void updateDataValue(String name, String value)

    abstract String getDataValue(String name)

    /**
     * Undocumented Hubitat simulator support (may not be working at all, but some scripts call this method).
     * @param closure. Probably called at some point.
     */
    abstract void simulator(Closure closure)

    /**
     * The Device object, from which its current properties and history can be accessed.
     **/
    abstract DeviceWrapper getDevice()

    abstract String getDeviceDataByName(String name)

    abstract DeviceWrapper getDeviceByDNI(String dni)

    abstract DeviceWrapper getDeviceByZigbeeId(String zigbeeId)

    abstract DeviceWrapper getDeviceById(Long id)

    abstract void sendEvent(Map properties)

    abstract List<Event> eventsSince(Date startDate, Map options = null)

    abstract void telnetConnect(Map options, String ip, int port, String username, String password)

    abstract void telnetConnect(String ip, int port, String username, String password)

    abstract void telnetClose()

    abstract Map createEvent(Map options)

    abstract List<String> delayBetween(List<String> cmds, Long delay)

    abstract List<String> delayBetween(List<String> cmds)

    abstract ChildDeviceWrapper addChildDevice(String typeName, String deviceNetworkId, Map properties = [:])

    abstract ChildDeviceWrapper addChildDevice(
            String namespace, String typeName, String deviceNetworkId, Map properties = [:])

    /**
     * @throws NotFoundException
     */
    abstract void deleteChildDevice(String deviceNetworkId)

    abstract ChildDeviceWrapper getChildDevice(String deviceNetworkId)

    abstract List<ChildDeviceWrapper> getChildDevices()

    abstract InterfaceHelper getInterfaces()
    abstract def getLanFingerprints()
    abstract void lanFingerprint(Map options)
    abstract void setLanFingerprints(def fingerprints)
    abstract Long hexStrToSignedInt(String str)
    abstract Long hexStrToUnsignedInt(String str)
    abstract Map getDefinitionData()

    abstract Map stringToMap(String str)

    abstract String intToHexStr(Long val)
    abstract String intToHexStr(Long val, Integer base)
    abstract boolean isStateChange(DeviceWrapper device, String a, String b)
    abstract boolean isSystemTypeOrHubDeveloper()

    abstract ChromeCast getChromeCast()

    abstract def getAttributes()
    abstract def getCapabilities()
    abstract def getCommands()
    abstract def getFingerprints()
    abstract def getPreferences()
    abstract def getTiles()

    abstract String getEXECUTOR_TYPE()
    abstract String getLinkText(DeviceWrapper w)
    abstract boolean displayed(String a, boolean b)
    abstract HubAction response(String s)
    abstract HubMultiAction response(List l)
    abstract HubAction response(me.biocomp.hubitat_ci.api.device_api.zwave.Command a) // Original: public static hubitat.device.HubAction com.hubitat.hub.executor.DeviceExecutor.response(hubitat.zwave.Command)

    abstract Short getZwaveHubNodeId()

    abstract void setAttributes(def a)
    abstract void setCapabilities(def c)
    abstract void setChromeCast(ChromeCast c)
    abstract void setCommands(def c)
    abstract void setDefinitionData(Map d)
    abstract void setDevice(DeviceWrapper w)
    abstract void setFingerprints(def f)

    abstract void setPreferences(def p)
    abstract void setTiles(def t)
    abstract void setZigbee(Zigbee zb)
    abstract void setZwave(Zwave zw)

    abstract void eventStreamClose()
    abstract void eventStreamConnect(String a,String b)

    abstract void graphTile(Map options)
    abstract void image(Map options)

    abstract void removeDataValue(java.lang.String a) // Original: public void com.hubitat.hub.executor.DeviceExecutor.removeDataValue(java.lang.String)
    abstract java.lang.String zwaveSecureEncap(me.biocomp.hubitat_ci.api.device_api.zwave.Command a) // Original: public java.lang.String com.hubitat.hub.executor.DeviceExecutor.zwaveSecureEncap(hubitat.zwave.Command)
    abstract java.lang.String zwaveSecureEncap(java.lang.String a) // Original: public java.lang.String com.hubitat.hub.executor.DeviceExecutor.zwaveSecureEncap(java.lang.String)
}

