package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv4

trait MultiChannelAggregatedMembersGet {
    abstract java.lang.String format()
    abstract java.lang.Short getAggregatedEndPoint()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setAggregatedEndPoint(java.lang.Short a)
}
