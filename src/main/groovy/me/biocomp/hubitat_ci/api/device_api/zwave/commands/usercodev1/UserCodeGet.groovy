package me.biocomp.hubitat_ci.api.device_api.zwave.commands.usercodev1

trait UserCodeGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getUserIdentifier()
    abstract void setUserIdentifier(java.lang.Short a)
}
