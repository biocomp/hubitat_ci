package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationv2

trait AssociationSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Object getNodeId()
    abstract java.util.List getPayload()
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setNodeId(java.lang.Object a)
}
