package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchmultilevelv3

trait SwitchMultilevelStartLevelChange {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDimmingDuration()
    abstract java.lang.Boolean getIgnoreStartLevel()
    abstract java.lang.Short getIncDec()
    abstract java.util.List getPayload()
    abstract java.lang.Short getStartLevel()
    abstract java.lang.Short getStepSize()
    abstract java.lang.Boolean getUpDown()
    abstract void setDimmingDuration(java.lang.Short a)
    abstract void setIgnoreStartLevel(java.lang.Boolean a)
    abstract void setIncDec(java.lang.Short a)
    abstract void setStartLevel(java.lang.Short a)
    abstract void setStepSize(java.lang.Short a)
    abstract void setUpDown(java.lang.Boolean a)
}
