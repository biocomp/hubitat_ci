package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatheatingv1

trait ThermostatHeatingTimedOffSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getHours()
    abstract java.lang.Short getMinutes()
    abstract java.util.List getPayload()
    abstract void setHours(java.lang.Short a)
    abstract void setMinutes(java.lang.Short a)
}
