package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelassociationv2

trait MultiChannelAssociationGroupingsReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSupportedGroupings()
    abstract void setSupportedGroupings(java.lang.Short a)
}
