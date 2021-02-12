package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelassociationv4

trait MultiChannelAssociationRemove {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Short getMULTI_CHANNEL_ASSOCIATION_REMOVE_MARKER()
    abstract java.util.List getMultiChannelNodeIds()
    abstract java.util.List getNodeId()
    abstract java.util.List getPayload()
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setMULTI_CHANNEL_ASSOCIATION_REMOVE_MARKER(java.lang.Short a)
    abstract void setMultiChannelNodeIds(java.util.List a)
    abstract void setNodeId(java.util.List a)
}
