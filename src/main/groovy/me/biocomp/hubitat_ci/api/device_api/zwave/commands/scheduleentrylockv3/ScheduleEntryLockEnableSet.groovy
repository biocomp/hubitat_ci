package me.biocomp.hubitat_ci.api.device_api.zwave.commands.scheduleentrylockv3

trait ScheduleEntryLockEnableSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getEnabled()
    abstract java.util.List getPayload()
    abstract java.lang.Short getUserIdentifier()
    abstract void setEnabled(java.lang.Short a)
    abstract void setUserIdentifier(java.lang.Short a)
}
