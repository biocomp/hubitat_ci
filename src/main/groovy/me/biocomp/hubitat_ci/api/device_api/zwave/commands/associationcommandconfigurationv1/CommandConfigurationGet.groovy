package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationcommandconfigurationv1

trait CommandConfigurationGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Short getNodeId()
    abstract java.util.List getPayload()
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setNodeId(java.lang.Short a)
}
