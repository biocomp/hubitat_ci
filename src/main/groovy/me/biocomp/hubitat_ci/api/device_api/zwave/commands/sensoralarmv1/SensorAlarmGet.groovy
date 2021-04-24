package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1

trait SensorAlarmGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSensorType()
    abstract void setSensorType(java.lang.Short a)
}
