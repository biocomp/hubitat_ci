package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scenecontrollerconfv1

trait SceneControllerConfGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupId()
    abstract java.util.List getPayload()
    abstract void setGroupId(java.lang.Short a)
}
