package me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterpulsev1

trait MeterPulseReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getPulseCount()
    abstract void setPulseCount(java.lang.Integer a)
}
