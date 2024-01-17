package me.biocomp.hubitat_ci.api.device_api.zwave.commands.ratetblconfigv1

trait RateTblSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getConsumptionPrecision()
    abstract java.lang.Short getConsumptionScale()
    abstract java.lang.Short getDcpRateId()
    abstract java.lang.Integer getDurationMinute()
    abstract java.lang.Integer getMaxConsumptionValue()
    abstract java.lang.Short getMaxDemandPrecision()
    abstract java.lang.Short getMaxDemandScale()
    abstract java.lang.Integer getMaxDemandValue()
    abstract java.lang.Integer getMinConsumptionValue()
    abstract java.lang.Short getNumberOfRateChar()
    abstract java.util.List getPayload()
    abstract java.util.List getRateCharacter()
    abstract java.lang.Short getRateParameterSetId()
    abstract java.lang.Short getRateType()
    abstract java.lang.Short getStartHourLocalTime()
    abstract java.lang.Short getStartMinuteLocalTime()
    abstract void setConsumptionPrecision(java.lang.Short a)
    abstract void setConsumptionScale(java.lang.Short a)
    abstract void setDcpRateId(java.lang.Short a)
    abstract void setDurationMinute(java.lang.Integer a)
    abstract void setMaxConsumptionValue(java.lang.Integer a)
    abstract void setMaxDemandPrecision(java.lang.Short a)
    abstract void setMaxDemandScale(java.lang.Short a)
    abstract void setMaxDemandValue(java.lang.Integer a)
    abstract void setMinConsumptionValue(java.lang.Integer a)
    abstract void setNumberOfRateChar(java.lang.Short a)
    abstract void setRateCharacter(java.util.List a)
    abstract void setRateParameterSetId(java.lang.Short a)
    abstract void setRateType(java.lang.Short a)
    abstract void setStartHourLocalTime(java.lang.Short a)
    abstract void setStartMinuteLocalTime(java.lang.Short a)
}