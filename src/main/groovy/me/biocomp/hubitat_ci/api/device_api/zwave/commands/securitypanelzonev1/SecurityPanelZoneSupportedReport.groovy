package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelzonev1

trait SecurityPanelZoneSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getZm()
    abstract java.lang.Short getZonesSupported()
    abstract void setZm(java.lang.Boolean a)
    abstract void setZonesSupported(java.lang.Short a)
}
