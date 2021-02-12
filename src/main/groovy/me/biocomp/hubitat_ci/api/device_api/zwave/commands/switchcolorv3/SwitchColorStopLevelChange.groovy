package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3

trait SwitchColorStopLevelChange {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract short getColorComponentId()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setColorComponentId(short a)
}
