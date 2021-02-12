package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scheduleentrylockv3

trait ScheduleEntryLockDailyRepeatingGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScheduleSlotId()
    abstract java.lang.Short getUserIdentifier()
    abstract void setScheduleSlotId(java.lang.Short a)
    abstract void setUserIdentifier(java.lang.Short a)
}
