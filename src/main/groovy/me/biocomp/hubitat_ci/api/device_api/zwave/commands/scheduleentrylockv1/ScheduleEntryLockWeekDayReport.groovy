package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scheduleentrylockv1

trait ScheduleEntryLockWeekDayReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDayOfWeek()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScheduleSlotId()
    abstract java.lang.Short getStartHour()
    abstract java.lang.Short getStartMinute()
    abstract java.lang.Short getStopHour()
    abstract java.lang.Short getStopMinute()
    abstract java.lang.Short getUserIdentifier()
    abstract void setDayOfWeek(java.lang.Short a)
    abstract void setScheduleSlotId(java.lang.Short a)
    abstract void setStartHour(java.lang.Short a)
    abstract void setStartMinute(java.lang.Short a)
    abstract void setStopHour(java.lang.Short a)
    abstract void setStopMinute(java.lang.Short a)
    abstract void setUserIdentifier(java.lang.Short a)
}
