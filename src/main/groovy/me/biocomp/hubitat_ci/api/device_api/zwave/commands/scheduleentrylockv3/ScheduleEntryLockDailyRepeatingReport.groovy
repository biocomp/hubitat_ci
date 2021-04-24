package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scheduleentrylockv3

trait ScheduleEntryLockDailyRepeatingReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDurationHour()
    abstract java.lang.Short getDurationMinute()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScheduleSlotId()
    abstract java.lang.Short getStartHour()
    abstract java.lang.Short getStartMinute()
    abstract java.lang.Short getUserIdentifier()
    abstract java.lang.Short getWeekDayBitmask()
    abstract void setDurationHour(java.lang.Short a)
    abstract void setDurationMinute(java.lang.Short a)
    abstract void setScheduleSlotId(java.lang.Short a)
    abstract void setStartHour(java.lang.Short a)
    abstract void setStartMinute(java.lang.Short a)
    abstract void setUserIdentifier(java.lang.Short a)
    abstract void setWeekDayBitmask(java.lang.Short a)
}
