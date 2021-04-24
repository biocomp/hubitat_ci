package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelzonev1

trait SecurityPanelZoneStateReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getZoneNumber()
    abstract java.lang.Short getZoneState()
    abstract void setZoneNumber(java.lang.Short a)
    abstract void setZoneState(java.lang.Short a)
}
