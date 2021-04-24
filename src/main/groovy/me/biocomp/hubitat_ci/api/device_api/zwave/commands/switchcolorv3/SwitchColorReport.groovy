package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3

trait SwitchColorReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.String getColorComponent()
    abstract short getColorComponentId()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getDimmingDuration()
    abstract java.util.List getPayload()
    abstract short getTargetValue()
    abstract short getValue()
    abstract void setColorComponent(java.lang.String a)
    abstract void setColorComponentId(short a)
    abstract void setDimmingDuration(short a)
    abstract void setTargetValue(short a)
    abstract void setValue(short a)
}
