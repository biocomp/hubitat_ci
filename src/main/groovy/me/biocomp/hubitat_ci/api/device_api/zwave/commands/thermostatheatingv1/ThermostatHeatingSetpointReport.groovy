package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatheatingv1

trait ThermostatHeatingSetpointReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrecision()
    abstract java.lang.Short getScale()
    abstract java.math.BigDecimal getScaledValue()
    abstract java.lang.Short getSetpointNr()
    abstract java.lang.Short getSize()
    abstract java.util.List getValue()
    abstract void setPrecision(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScaledValue(java.math.BigDecimal a)
    abstract void setSetpointNr(java.lang.Short a)
    abstract void setSize(java.lang.Short a)
    abstract void setValue(java.util.List a)
}
