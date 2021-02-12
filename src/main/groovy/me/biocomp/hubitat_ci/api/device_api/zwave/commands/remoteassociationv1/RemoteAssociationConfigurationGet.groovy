package me.biocomp.hubitat_ci.api.device_api.zwave.commands.remoteassociationv1

trait RemoteAssociationConfigurationGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getLocalGroupingIdentifier()
    abstract java.util.List getPayload()
    abstract void setLocalGroupingIdentifier(java.lang.Short a)
}
