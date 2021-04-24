package me.biocomp.hubitat_ci.api.device_api.zwave.commands.hrvcontrolv1

trait HrvControlBypassReport {
    abstract java.lang.String format()
    abstract java.lang.Short getBypass()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setBypass(java.lang.Short a)
}
