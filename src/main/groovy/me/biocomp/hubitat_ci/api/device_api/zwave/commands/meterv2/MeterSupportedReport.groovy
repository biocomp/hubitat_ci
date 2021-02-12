package me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2

trait MeterSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getMeterReset()
    abstract java.lang.Short getMeterType()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScaleSupported()
    abstract void setMeterReset(java.lang.Boolean a)
    abstract void setMeterType(java.lang.Short a)
    abstract void setScaleSupported(java.lang.Short a)
}
