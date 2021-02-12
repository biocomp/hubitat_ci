package me.biocomp.hubitat_ci.api.device_api.zwave.commands.chimneyfanv1

trait ChimneyFanAlarmStatusSet {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAcknowledgeAlarmTemperatureExceeded()
    abstract java.lang.Boolean getAcknowledgeExternalAlarm()
    abstract java.lang.Boolean getAcknowledgeSensorError()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getNotUsed1()
    abstract java.lang.Short getNotUsed2()
    abstract java.util.List getPayload()
    abstract void setAcknowledgeAlarmTemperatureExceeded(java.lang.Boolean a)
    abstract void setAcknowledgeExternalAlarm(java.lang.Boolean a)
    abstract void setAcknowledgeSensorError(java.lang.Boolean a)
    abstract void setNotUsed1(java.lang.Boolean a)
    abstract void setNotUsed2(java.lang.Short a)
}
