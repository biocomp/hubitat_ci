package me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv1

trait AlarmGet {
    abstract java.lang.String format()
    abstract java.lang.Short getAlarmType()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setAlarmType(java.lang.Short a)
}
