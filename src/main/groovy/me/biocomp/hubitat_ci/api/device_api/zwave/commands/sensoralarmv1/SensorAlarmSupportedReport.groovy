package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1

trait SensorAlarmSupportedReport {
    abstract java.lang.String format()
    abstract java.util.List getBitMask()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNumberOfBitMasks()
    abstract java.util.List getPayload()
    abstract void setBitMask(java.util.List a)
    abstract void setNumberOfBitMasks(java.lang.Short a)
}
