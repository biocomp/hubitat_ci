package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatmodev3

trait ThermostatModeSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getMODE_AUTO()
    abstract short getMODE_AUTO_CHANGEOVER()
    abstract short getMODE_AUXILIARY_HEAT()
    abstract short getMODE_AWAY()
    abstract short getMODE_COOL()
    abstract short getMODE_DRY_AIR()
    abstract short getMODE_ENERGY_SAVE_COOL()
    abstract short getMODE_ENERGY_SAVE_HEAT()
    abstract short getMODE_FAN_ONLY()
    abstract short getMODE_FULL_POWER()
    abstract short getMODE_FURNACE()
    abstract short getMODE_HEAT()
    abstract short getMODE_MANUFACTURER_SPECIFIC()
    abstract short getMODE_MOIST_AIR()
    abstract short getMODE_OFF()
    abstract short getMODE_RESUME()
    abstract java.util.List getManufacturerData()
    abstract java.lang.Short getMode()
    abstract java.lang.Short getNoOfManufacturerDataFields()
    abstract java.util.List getPayload()
    abstract void setManufacturerData(java.util.List a)
    abstract void setMode(java.lang.Short a)
    abstract void setNoOfManufacturerDataFields(java.lang.Short a)
}
