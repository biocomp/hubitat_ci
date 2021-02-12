package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scheduleentrylockv2

trait ScheduleEntryLockTimeOffsetReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getHourTzo()
    abstract java.lang.Short getMinuteOffsetDst()
    abstract java.lang.Short getMinuteTzo()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSignOffsetDst()
    abstract java.lang.Short getSignTzo()
    abstract void setHourTzo(java.lang.Short a)
    abstract void setMinuteOffsetDst(java.lang.Short a)
    abstract void setMinuteTzo(java.lang.Short a)
    abstract void setSignOffsetDst(java.lang.Short a)
    abstract void setSignTzo(java.lang.Short a)
}
