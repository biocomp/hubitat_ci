package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontrolsetpointv2

trait HumidityControlSetpointReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrecision()
    abstract short getSCALE_ABSOLUTE()
    abstract short getSCALE_PERCENTAGE()
    abstract short getSETPOINT_TYPE_AUTO()
    abstract short getSETPOINT_TYPE_DEHUMIDIFIER()
    abstract short getSETPOINT_TYPE_HUMIDIFIER()
    abstract java.lang.Short getScale()
    abstract java.math.BigDecimal getScaledValue()
    abstract java.lang.Short getSetpointType()
    abstract java.lang.Short getSize()
    abstract java.util.List getValue()
    abstract void setPrecision(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScaledValue(java.math.BigDecimal a)
    abstract void setSetpointType(java.lang.Short a)
    abstract void setSize(java.lang.Short a)
    abstract void setValue(java.util.List a)
}
