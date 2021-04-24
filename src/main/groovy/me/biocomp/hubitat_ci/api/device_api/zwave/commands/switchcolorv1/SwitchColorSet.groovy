package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1

trait SwitchColorSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.util.List getColorComponentBytes()
    abstract short getColorComponentCount()
    abstract java.util.Map getColorComponents()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setColorComponentBytes(java.util.List a)
    abstract void setColorComponentCount(short a)
    abstract void setColorComponents(java.util.Map a)
}
