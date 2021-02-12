package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scheduleentrylockv3

trait ScheduleEntryTypeSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNumberOfSlotsDailyRepeating()
    abstract java.lang.Short getNumberOfSlotsWeekDay()
    abstract java.lang.Short getNumberOfSlotsYearDay()
    abstract java.util.List getPayload()
    abstract void setNumberOfSlotsDailyRepeating(java.lang.Short a)
    abstract void setNumberOfSlotsWeekDay(java.lang.Short a)
    abstract void setNumberOfSlotsYearDay(java.lang.Short a)
}
