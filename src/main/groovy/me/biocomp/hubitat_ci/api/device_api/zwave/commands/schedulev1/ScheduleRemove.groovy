package me.biocomp.hubitat_ci.api.device_api.zwave.commands.schedulev1

trait ScheduleRemove {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScheduleId()
    abstract void setScheduleId(java.lang.Short a)
}
