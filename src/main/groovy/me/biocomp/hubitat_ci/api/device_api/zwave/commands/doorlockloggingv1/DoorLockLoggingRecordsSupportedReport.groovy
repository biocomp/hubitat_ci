package me.biocomp.hubitat_ci.api.device_api.zwave.commands.doorlockloggingv1

trait DoorLockLoggingRecordsSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMaxRecordsStored()
    abstract java.util.List getPayload()
    abstract void setMaxRecordsStored(java.lang.Short a)
}
