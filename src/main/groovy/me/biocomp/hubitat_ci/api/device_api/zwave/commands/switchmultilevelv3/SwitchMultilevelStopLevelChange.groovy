package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchmultilevelv3

trait SwitchMultilevelStopLevelChange {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
}
