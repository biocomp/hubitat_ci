package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchmultilevelv4

trait SwitchMultilevelSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDimmingDuration()
    abstract java.util.List getPayload()
    abstract java.lang.Short getValue()
    abstract void setDimmingDuration(java.lang.Short a)
    abstract void setValue(java.lang.Short a)
}
