package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationcommandconfigurationv1

trait CommandConfigurationSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.util.List getCommandByte()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandClassIdentifier()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getCommandIdentifier()
    abstract java.lang.Short getCommandLength()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Short getNodeId()
    abstract java.util.List getPayload()
    abstract void setCommandByte(java.util.List a)
    abstract void setCommandClassIdentifier(java.lang.Short a)
    abstract void setCommandIdentifier(java.lang.Short a)
    abstract void setCommandLength(java.lang.Short a)
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setNodeId(java.lang.Short a)
}
