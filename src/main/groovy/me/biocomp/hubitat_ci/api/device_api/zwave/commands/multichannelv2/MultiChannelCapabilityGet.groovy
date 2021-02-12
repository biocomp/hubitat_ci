package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv2

trait MultiChannelCapabilityGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getEndPoint()
    abstract java.util.List getPayload()
    abstract void setEndPoint(java.lang.Short a)
}
