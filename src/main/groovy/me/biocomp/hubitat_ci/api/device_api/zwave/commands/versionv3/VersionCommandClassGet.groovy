package me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv3

trait VersionCommandClassGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRequestedCommandClass()
    abstract void setRequestedCommandClass(java.lang.Short a)
}
