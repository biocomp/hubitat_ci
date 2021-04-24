package me.biocomp.hubitat_ci.api.device_api.zwave.commands.basicv2

trait BasicReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDuration()
    abstract java.util.List getPayload()
    abstract java.lang.Short getTargetValue()
    abstract java.lang.Short getValue()
    abstract void setDuration(java.lang.Short a)
    abstract void setTargetValue(java.lang.Short a)
    abstract void setValue(java.lang.Short a)
}
