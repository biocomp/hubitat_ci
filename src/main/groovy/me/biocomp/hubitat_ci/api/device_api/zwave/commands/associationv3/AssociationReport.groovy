package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationv3

trait AssociationReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Short getMaxNodesSupported()
    abstract java.util.List getNodeId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getReportsToFollow()
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setMaxNodesSupported(java.lang.Short a)
    abstract void setNodeId(java.util.List a)
    abstract void setReportsToFollow(java.lang.Short a)
}
