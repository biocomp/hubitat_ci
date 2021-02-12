package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchbinaryv2

trait SwitchBinarySet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDuration()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSwitchValue()
    abstract java.lang.Short getTargetValue()
    abstract void setDuration(java.lang.Short a)
    abstract void setSwitchValue(java.lang.Short a)
    abstract void setTargetValue(java.lang.Short a)
}
