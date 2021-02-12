package me.biocomp.hubitat_ci.api.device_api.zwave.commands.controllerreplicationv1

trait CtrlReplicationTransferGroupName {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupId()
    abstract java.util.List getGroupName()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSequenceNumber()
    abstract void setGroupId(java.lang.Short a)
    abstract void setGroupName(java.util.List a)
    abstract void setSequenceNumber(java.lang.Short a)
}
