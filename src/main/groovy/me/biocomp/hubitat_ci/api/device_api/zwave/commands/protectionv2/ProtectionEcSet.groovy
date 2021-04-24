package me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv2

trait ProtectionEcSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNodeId()
    abstract java.util.List getPayload()
    abstract void setNodeId(java.lang.Short a)
}
