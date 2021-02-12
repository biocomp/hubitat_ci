package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelzonev1

trait SecurityPanelZoneTypeReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getZoneNumber()
    abstract java.lang.Short getZoneType()
    abstract void setZoneNumber(java.lang.Short a)
    abstract void setZoneType(java.lang.Short a)
}
