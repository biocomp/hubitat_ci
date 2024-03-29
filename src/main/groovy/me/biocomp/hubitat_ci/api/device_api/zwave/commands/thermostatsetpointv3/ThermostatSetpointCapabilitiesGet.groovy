package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatsetpointv3

trait ThermostatSetpointCapabilitiesGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract short getSETPOINT_TYPE_AUTO_CHANGEOVER()
    abstract short getSETPOINT_TYPE_AWAY_COOLING()
    abstract short getSETPOINT_TYPE_AWAY_HEATING()
    abstract short getSETPOINT_TYPE_COOLING_1()
    abstract short getSETPOINT_TYPE_DRY_AIR()
    abstract short getSETPOINT_TYPE_ENERGY_SAVE_COOLING()
    abstract short getSETPOINT_TYPE_ENERGY_SAVE_HEATING()
    abstract short getSETPOINT_TYPE_FULL_POWER()
    abstract short getSETPOINT_TYPE_FURNACE()
    abstract short getSETPOINT_TYPE_HEATING_1()
    abstract short getSETPOINT_TYPE_MOIST_AIR()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED1()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED2()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED3()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED4()
    abstract java.lang.Short getSetpointType()
    abstract void setSetpointType(java.lang.Short a)
}
