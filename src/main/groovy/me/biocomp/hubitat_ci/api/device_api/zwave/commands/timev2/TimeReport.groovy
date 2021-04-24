package me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev2

trait TimeReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getHourLocalTime()
    abstract java.lang.Short getMinuteLocalTime()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getRtcFailure()
    abstract java.lang.Short getSecondLocalTime()
    abstract void setHourLocalTime(java.lang.Short a)
    abstract void setMinuteLocalTime(java.lang.Short a)
    abstract void setRtcFailure(java.lang.Boolean a)
    abstract void setSecondLocalTime(java.lang.Short a)
}
