package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchtogglemultilevelv1

trait SwitchToggleMultilevelReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getValue()
    abstract void setValue(java.lang.Short a)
}
