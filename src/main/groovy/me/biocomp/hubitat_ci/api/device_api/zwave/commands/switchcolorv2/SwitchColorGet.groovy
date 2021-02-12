package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2

trait SwitchColorGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.String getColorComponent()
    abstract short getColorComponentId()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setColorComponent(java.lang.String a)
    abstract void setColorComponentId(short a)
}
