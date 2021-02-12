package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sceneactuatorconfv1

trait SceneActuatorConfSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDimmingDuration()
    abstract java.lang.Short getLevel()
    abstract java.lang.Boolean getOverride()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSceneId()
    abstract void setDimmingDuration(java.lang.Short a)
    abstract void setLevel(java.lang.Short a)
    abstract void setOverride(java.lang.Boolean a)
    abstract void setSceneId(java.lang.Short a)
}
