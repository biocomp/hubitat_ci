package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatheatingv1

trait ThermostatHeatingRelayStatusReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRELAY_STATUS_OFF()
    abstract java.lang.Short getRELAY_STATUS_ON()
    abstract java.lang.Short getRelayStatus()
    abstract void setRELAY_STATUS_OFF(java.lang.Short a)
    abstract void setRELAY_STATUS_ON(java.lang.Short a)
    abstract void setRelayStatus(java.lang.Short a)
}
