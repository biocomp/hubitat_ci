package me.biocomp.hubitat_ci.api.device_api.zigbee

import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.device_api.zigbee.clusters.iaszone.ZoneStatus

trait Zigbee {
    final static int STANDARD_DELAY_INT = 2000

    enum ZigbeeCluster {
        BASIC_CLUSTER(0x0000),
        POWER_CONFIGURATION_CLUSTER(0x0001),
        TEMPERATURE_CONFIGURATION_CLUSTER(0x0002),
        IDENTIFY_CLUSTER(0x0003),
        GROUPS_CLUSTER(0x0004),
        SCENES_CLUSTER(0x0005),
        ON_OFF_CLUSTER(0x0006),
        ON_OFF_SWITCH_CONFIGURATION_CLUSTER(0x0007),
        LEVEL_CONTROL_CLUSTER(0x0008),
        ALARMS_CLUSTER(0x0009),
        TIME_CLUSTER(0x000A),
        RSSI_LOCATION_CLUSTER(0x000B),
        ANALOG_INPUT_CLUSTER(0x000C),
        ANALOG_OUTPUT_CLUSTER(0x000D),
        ANALOG_VALUE_CLUSTER(0x000E),
        BINARY_INPUT_CLUSTER(0x000F),
        BINARY_OUTPUT_CLUSTER(0x0010),
        BINARY_VALUE_CLUSTER(0x0011),
        MULTISTATE_INPUT_CLUSTER(0x0012),
        MULTISTATE_OUTPUT_CLUSTER(0x0013),
        MULTISTATE_VALUE_CLUSTER(0x0014),
        OTA_CLUSTER(0x0019),
        POLL_CONTROL_CLUSTER(0x0020),

        POWER_PROFILE_CLUSTER(0x001A),
        SHADE_CONFIGURATION_CLUSTER(0x0100),
        DOOR_LOCK_CLUSTER(0x0101),
        WINDOW_COVERING_CLUSTER(0x0102),
        PUMP_CONFIGURATION_CONTROL_CLUSTER(0x0200),
        THERMOSTAT_CLUSTER(0x0201),
        FAN_CONTROL_CLUSTER(0x0202),
        DEHUMIDIFICATION_CONTROL_CLUSTER(0x0203),
        THERMOSTAT_USER_INTERFACE_CONFIGURATION_CLUSTER(0x0204),
        COLOR_CONTROL_CLUSTER(0x0300),
        BALLAST_CONFIGURATION_CLUSTER(0x0301),
        ILLUMINANCE_MEASUREMENT_CLUSTER(0x0400),
        ILLUMINANCE_LEVEL_SENSING_CLUSTER(0x0401),
        TEMPERATURE_MEASUREMENT_CLUSTER(0x0402),
        PRESSURE_MEASUREMENT_CLUSTER(0x0403),
        FLOW_MEASUREMENT_CLUSTER(0x0404),
        REALATIVE_HUMIDITY_MEASUREMENT_CLUSTER(0x0405),
        OCCUPANCY_SENSING_CLUSTER(0x0406),
        IAS_ZONE_CLUSTER(0x0500),
        IAS_ACE_CLUSTER(0x0501),
        IAS_WD_CLUSTER(0x0502),
        PRICE_CLUSTER(0x0700),
        DEMAND_RESPONSE_CLUSTER(0x0701),
        METERING_CLUSTER(0x0702),
        MESSAGING_CLUSTER(0x0703),
        TUNNELING_CLUSTER(0x0704),
        KEY_ESTABLISHMENT_CLUSTER(0x0800),
        DIAGONSTICS_CLUSTER(0x0B05),
        METER_IDENTIFICATION_CLUSTER(0x0B01),
        ELECTRICAL_MEASUREMENT_CLUSTER(0x0B04),
    }

    abstract List batteryConfig()

    /**
     * Get ZHA cluster information
     * @param value The integer value to lookup
     * @return Cluster entry, null if no matching entry found.
     */
    abstract ZigbeeCluster clusterLookup(int value)

    abstract List colorTemperatureConfig()

    abstract List colorTemperatureRefresh(int delay = STANDARD_DELAY_INT)

    abstract List command(int cluster, int command, String... payload)
    abstract List command(int cluster, int command, Map additionalParams = [:], int delay = STANDARD_DELAY_INT)
    abstract List command(int cluster, int command, Map additionalParam, int delay, String... payload)

    abstract List configureReporting(int clusterId, int attributeId, int dataType, int minReportTime, int maxReportTime,
                                     int reportableChange = null, Map additionalParams = [:],
                                     int delay = STANDARD_DELAY_INT)

    abstract int convertHexToInt(String value)
    abstract String convertToHexString(int value, int width)

    abstract List electricMeasurementPowerRefresh(int delay = STANDARD_DELAY_INT)

    abstract List enrollResponse()
    // Couldn't find any documentation for this overload
    // abstract List enrollResponse(int = null)

    abstract Class getCluster()

    abstract DeviceWrapper getDevice()

    abstract Map getEvent(String)

    abstract static Map getKnownDescription(String)

    /**
     * Generate the off command for the on/off ZigBee cluster for a group
     * @param groupID
     * @param delay The number of milliseconds to delay after sending the command, if omitted then the standard delay
     *              of 2000ms is used.
     * @return a list of zigbee commands to be sent to the group
     */
    abstract List groupOff(int groupID, int delay = STANDARD_DELAY_INT)

    /**
     * Generate the on command for the on/off ZigBee cluster for a group
     * @param groupID
     * @param delay The number of milliseconds to delay after sending the command, if omitted then the standard delay
     *              of 2000ms is used.
     * @return a list of zigbee commands to be sent to the group
     */
    abstract List groupOn(int groupID, int delay = STANDARD_DELAY_INT)

    abstract String hex2String(String arg)

    abstract List levelConfig()

    abstract List levelRefresh(int delay = STANDARD_DELAY_INT)

    /**
     * Generate the off command for the on/off ZigBee cluster
     * @param delay The number of milliseconds to delay after sending the command, if omitted then the standard delay
     *              of 2000ms is used.
     * @return A list of zigbee commands to be sent to the device
     */
    abstract List off(int delay = STANDARD_DELAY_INT)

    /**
     * Generate the on command for the on/off ZigBee cluster
     * @param delay The number of milliseconds to delay after sending the command, if omitted then the standard delay
     *              of 2000ms is used.
     * @return A list of zigbee commands to be sent to the device
     */
    abstract List on(int delay = STANDARD_DELAY_INT)

    abstract List onOffConfig()
    abstract List onOffConfig(int minRefreshInterval, int maxRefreshInterval)

    // Couldn't find any documentation for these overloads
    /*
    abstract List onOffConfig(String)
    abstract List onOffConfig(String, int, int)
     */

    abstract List onOffRefresh(int delay = STANDARD_DELAY_INT)

    abstract SmartShield parse(String description)

    /**
     * Parse a zigbee message into a Map. This method currently handles messages that start with 'catchall' or 'read attr'
     * @param String The zigbee message to parse
     * @return Map of key/value pairs that have been parsed from the zigbee message
     */
    abstract Map parseDescriptionAsMap(String description)

    abstract ZoneStatus parseZoneStatus(String description)

    abstract List readAttribute(int cluster, int attributeId, Map additionalParams = [:], int delay = STANDARD_DELAY_INT)
    abstract List readAttribute(int cluster, List attributeIds, Map additionalParams = [:], int delay = STANDARD_DELAY_INT)

    abstract List refreshData(String attr1, String attr2)

    abstract List setColor(Map value, int delay = STANDARD_DELAY_INT)

    abstract List setColorTemperature(int value, int delay = STANDARD_DELAY_INT)
    abstract List setColorTemperature(String value, int delay = STANDARD_DELAY_INT)
    abstract List setColorTemperature(BigDecimal value, int delay = STANDARD_DELAY_INT)

    // Couldn't find any documentation for these overloads
    /*
    abstract List setColorTemperature(int, int, int)
    abstract List setColorTemperature(String, int, int)
    abstract List setColorTemperature(BigDecimal, int, int)

    abstract List setColorXY(Map)
    abstract List setColorXY(Map, int)
     */

    abstract void setDevice(DeviceWrapper)

    abstract List setGroupColorTemperature(int groupID, int value, int delay = STANDARD_DELAY_INT)
    abstract List setGroupColorTemperature(int groupID, String value, int delay = STANDARD_DELAY_INT)
    abstract List setGroupColorTemperature(int groupID, BigDecimal value, int delay = STANDARD_DELAY_INT)

    // Couldn't find any documentation for these overloads
    /*
    abstract List setGroupColorTemperature(int groupId, int, int, int)
    abstract List setGroupColorTemperature(int groupId, String, int, int)
    abstract List setGroupColorTemperature(int groupId, BigDecimal, int, int)
     */

    abstract List setGroupLevel(int groupID, int level, BigDecimal rate = null)
    abstract List setGroupLevel(int groupID, BigDecimal level, BigDecimal rate = null)
    // Couldn't find any documentation for this overload
    // abstract List setGroupLevel(int groupID, int, BigDecimal, int)

    abstract List setHue(int value, int delay = STANDARD_DELAY_INT)
    // Couldn't find any documentation for this overload
    // abstract List setHue(int, int, int)

    abstract List setLevel(int level)
    abstract List setLevel(int level, int rate)
    abstract List setLevel(int level, BigDecimal rate)
    abstract List setLevel(BigDecimal level, BigDecimal rate = 0xFFFF)
    // Couldn't find any documentation for this overload
    // abstract List setLevel(int, BigDecimal, int)

    abstract List setSaturation(int value, int delay = STANDARD_DELAY_INT)
    // Couldn't find any documentation for this overload
    // abstract List setSaturation(int, int, int)

    abstract static String swapOctets(String bytes)

    abstract List temperatureConfig()
    abstract List temperatureConfig(int minRefreshInterval, int maxRefreshInterval)

    abstract Object this$dist$get$1(String)

    abstract Object this$dist$invoke$1(String, Object)

    abstract void this$dist$set$1(String, Object)

    abstract List writeAttribute(int cluster, int attributeId, int dataType, int value, Map additionalParams = [:],
                                 int delay=null)
}

