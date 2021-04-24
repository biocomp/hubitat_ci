package me.biocomp.hubitat_ci.api.device_api.zwave.commands.schedulev1

trait ScheduleStateSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScheduleId()
    abstract java.lang.Short getScheduleState()
    abstract void setScheduleId(java.lang.Short a)
    abstract void setScheduleState(java.lang.Short a)
}
