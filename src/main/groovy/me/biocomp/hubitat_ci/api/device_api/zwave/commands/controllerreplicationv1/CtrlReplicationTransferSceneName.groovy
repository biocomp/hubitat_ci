package me.biocomp.hubitat_ci.api.device_api.zwave.commands.controllerreplicationv1

trait CtrlReplicationTransferSceneName {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSceneId()
    abstract java.util.List getSceneName()
    abstract java.lang.Short getSequenceNumber()
    abstract void setSceneId(java.lang.Short a)
    abstract void setSceneName(java.util.List a)
    abstract void setSequenceNumber(java.lang.Short a)
}
