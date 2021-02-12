package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scenecontrollerconfv1

trait SceneControllerConfSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDimmingDuration()
    abstract java.lang.Short getGroupId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSceneId()
    abstract void setDimmingDuration(java.lang.Short a)
    abstract void setGroupId(java.lang.Short a)
    abstract void setSceneId(java.lang.Short a)
}
