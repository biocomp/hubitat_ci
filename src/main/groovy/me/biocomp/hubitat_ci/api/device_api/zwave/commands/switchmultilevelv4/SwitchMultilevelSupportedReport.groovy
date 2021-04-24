package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchmultilevelv4

trait SwitchMultilevelSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrimarySwitchType()
    abstract java.lang.Short getSecondarySwitchType()
    abstract void setPrimarySwitchType(java.lang.Short a)
    abstract void setSecondarySwitchType(java.lang.Short a)
}
