package me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4

trait MeterGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRateType()
    abstract java.lang.Short getScale()
    abstract java.lang.Short getScale2()
    abstract void setRateType(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScale2(java.lang.Short a)
}
