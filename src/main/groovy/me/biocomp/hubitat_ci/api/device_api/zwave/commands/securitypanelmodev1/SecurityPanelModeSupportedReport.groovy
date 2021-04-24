package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelmodev1

trait SecurityPanelModeSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getSupportedModeBitMask()
    abstract void setSupportedModeBitMask(java.lang.Integer a)
}
