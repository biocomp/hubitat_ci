package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchtogglemultilevelv1

trait SwitchToggleMultilevelGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
}
