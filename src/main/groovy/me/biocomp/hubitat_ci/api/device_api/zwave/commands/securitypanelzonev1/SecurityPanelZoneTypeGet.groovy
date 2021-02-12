package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelzonev1

trait SecurityPanelZoneTypeGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getZoneNumber()
    abstract void setZoneNumber(java.lang.Short a)
}
