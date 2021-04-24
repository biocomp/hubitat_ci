package me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5

trait MeterReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getDeltaTime()
    abstract java.lang.Short getMETER_TYPE_ELECTRIC_METER()
    abstract java.lang.Short getMETER_TYPE_GAS_METER()
    abstract java.lang.Short getMETER_TYPE_WATER_METER()
    abstract java.lang.Short getMeterType()
    abstract java.util.List getMeterValue()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrecision()
    abstract java.util.List getPreviousMeterValue()
    abstract java.lang.Short getRateType()
    abstract java.lang.Short getScale()
    abstract java.lang.Short getScale2()
    abstract java.math.BigDecimal getScaledMeterValue()
    abstract java.math.BigDecimal getScaledPreviousMeterValue()
    abstract java.lang.Short getSize()
    abstract void setDeltaTime(java.lang.Integer a)
    abstract void setMETER_TYPE_ELECTRIC_METER(java.lang.Short a)
    abstract void setMETER_TYPE_GAS_METER(java.lang.Short a)
    abstract void setMETER_TYPE_WATER_METER(java.lang.Short a)
    abstract void setMeterType(java.lang.Short a)
    abstract void setMeterValue(java.util.List a)
    abstract void setPrecision(java.lang.Short a)
    abstract void setPreviousMeterValue(java.util.List a)
    abstract void setRateType(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScale2(java.lang.Short a)
    abstract void setSize(java.lang.Short a)
}
