package me.biocomp.hubitat_ci.api.device_api.zwave.commands.remoteassociationv1

trait RemoteAssociationConfigurationReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getLocalGroupingIdentifier()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRemoteGroupingIdentifier()
    abstract java.lang.Short getRemoteNodeid()
    abstract void setLocalGroupingIdentifier(java.lang.Short a)
    abstract void setRemoteGroupingIdentifier(java.lang.Short a)
    abstract void setRemoteNodeid(java.lang.Short a)
}
