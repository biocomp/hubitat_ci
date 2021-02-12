package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scheduleentrylockv2

trait ScheduleEntryLockYearDayReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScheduleSlotId()
    abstract java.lang.Short getStartDay()
    abstract java.lang.Short getStartHour()
    abstract java.lang.Short getStartMinute()
    abstract java.lang.Short getStartMonth()
    abstract java.lang.Short getStartYear()
    abstract java.lang.Short getStopDay()
    abstract java.lang.Short getStopHour()
    abstract java.lang.Short getStopMinute()
    abstract java.lang.Short getStopMonth()
    abstract java.lang.Short getStopYear()
    abstract java.lang.Short getUserIdentifier()
    abstract void setScheduleSlotId(java.lang.Short a)
    abstract void setStartDay(java.lang.Short a)
    abstract void setStartHour(java.lang.Short a)
    abstract void setStartMinute(java.lang.Short a)
    abstract void setStartMonth(java.lang.Short a)
    abstract void setStartYear(java.lang.Short a)
    abstract void setStopDay(java.lang.Short a)
    abstract void setStopHour(java.lang.Short a)
    abstract void setStopMinute(java.lang.Short a)
    abstract void setStopMonth(java.lang.Short a)
    abstract void setStopYear(java.lang.Short a)
    abstract void setUserIdentifier(java.lang.Short a)
}
