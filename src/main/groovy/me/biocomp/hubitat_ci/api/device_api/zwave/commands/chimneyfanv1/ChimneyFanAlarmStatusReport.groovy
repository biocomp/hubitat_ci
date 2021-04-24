package me.biocomp.hubitat_ci.api.device_api.zwave.commands.chimneyfanv1

trait ChimneyFanAlarmStatusReport {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAlarmTemperatureExceeded()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getExternalAlarm()
    abstract java.lang.Short getNotUsed()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getSensorError()
    abstract java.lang.Boolean getService()
    abstract java.lang.Boolean getSpeedChangeEnable()
    abstract java.lang.Boolean getStartTemperatureExceeded()
    abstract void setAlarmTemperatureExceeded(java.lang.Boolean a)
    abstract void setExternalAlarm(java.lang.Boolean a)
    abstract void setNotUsed(java.lang.Short a)
    abstract void setSensorError(java.lang.Boolean a)
    abstract void setService(java.lang.Boolean a)
    abstract void setSpeedChangeEnable(java.lang.Boolean a)
    abstract void setStartTemperatureExceeded(java.lang.Boolean a)
}
