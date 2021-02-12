package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatheatingv1

trait ThermostatHeatingStatusReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSTATUS_COOLING()
    abstract java.lang.Short getSTATUS_HEATING()
    abstract java.lang.Short getStatus()
    abstract void setSTATUS_COOLING(java.lang.Short a)
    abstract void setSTATUS_HEATING(java.lang.Short a)
    abstract void setStatus(java.lang.Short a)
}
