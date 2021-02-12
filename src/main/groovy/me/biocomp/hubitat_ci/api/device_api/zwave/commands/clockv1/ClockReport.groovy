package me.biocomp.hubitat_ci.api.device_api.zwave.commands.clockv1

trait ClockReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getHour()
    abstract java.lang.Short getMinute()
    abstract java.util.List getPayload()
    abstract java.lang.Short getWeekday()
    abstract void setHour(java.lang.Short a)
    abstract void setMinute(java.lang.Short a)
    abstract void setWeekday(java.lang.Short a)
}
