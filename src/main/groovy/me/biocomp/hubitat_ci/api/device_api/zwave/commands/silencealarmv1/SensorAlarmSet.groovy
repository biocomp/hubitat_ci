package me.biocomp.hubitat_ci.api.device_api.zwave.commands.silencealarmv1

trait SensorAlarmSet {
    abstract java.lang.String format()
    abstract java.util.List getBitMask()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMode()
    abstract java.lang.Short getNumberOfBitMasks()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getSeconds()
    abstract void setBitMask(java.util.List a)
    abstract void setMode(java.lang.Short a)
    abstract void setNumberOfBitMasks(java.lang.Short a)
    abstract void setSeconds(java.lang.Integer a)
}
