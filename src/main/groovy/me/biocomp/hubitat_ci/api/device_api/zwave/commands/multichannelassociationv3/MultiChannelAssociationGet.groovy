package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelassociationv3

trait MultiChannelAssociationGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.util.List getPayload()
    abstract void setGroupingIdentifier(java.lang.Short a)
}
