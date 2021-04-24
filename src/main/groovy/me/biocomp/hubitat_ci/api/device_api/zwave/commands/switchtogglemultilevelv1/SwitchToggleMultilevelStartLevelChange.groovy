package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchtogglemultilevelv1

trait SwitchToggleMultilevelStartLevelChange {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getIgnoreStartLevel()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getRollOver()
    abstract java.lang.Short getStartLevel()
    abstract void setIgnoreStartLevel(java.lang.Boolean a)
    abstract void setRollOver(java.lang.Boolean a)
    abstract void setStartLevel(java.lang.Short a)
}
