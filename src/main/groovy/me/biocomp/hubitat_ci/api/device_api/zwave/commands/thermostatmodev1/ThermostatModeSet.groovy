package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatmodev1

trait ThermostatModeSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getMODE_AUTO()
    abstract short getMODE_AUTO_CHANGEOVER()
    abstract short getMODE_AUXILIARY_HEAT()
    abstract short getMODE_COOL()
    abstract short getMODE_DRY_AIR()
    abstract short getMODE_FAN_ONLY()
    abstract short getMODE_FURNACE()
    abstract short getMODE_HEAT()
    abstract short getMODE_MOIST_AIR()
    abstract short getMODE_OFF()
    abstract short getMODE_RESUME()
    abstract java.lang.Short getMode()
    abstract java.util.List getPayload()
    abstract void setMode(java.lang.Short a)
}
