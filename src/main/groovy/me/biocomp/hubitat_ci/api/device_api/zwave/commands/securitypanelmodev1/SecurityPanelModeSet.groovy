package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelmodev1

trait SecurityPanelModeSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMode()
    abstract java.util.List getPayload()
    abstract void setMode(java.lang.Short a)
}
