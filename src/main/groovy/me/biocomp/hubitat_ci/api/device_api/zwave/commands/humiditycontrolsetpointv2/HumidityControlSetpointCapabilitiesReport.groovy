package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontrolsetpointv2

trait HumidityControlSetpointCapabilitiesReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMaximumPrecision()
    abstract java.lang.Short getMaximumScale()
    abstract java.math.BigDecimal getMaximumScaledValue()
    abstract java.lang.Short getMaximumSize()
    abstract java.util.List getMaximumValue()
    abstract java.lang.Short getMinimumPrecision()
    abstract java.lang.Short getMinimumScale()
    abstract java.math.BigDecimal getMinimumScaledValue()
    abstract java.lang.Short getMinimumSize()
    abstract java.util.List getMinimumValue()
    abstract java.util.List getPayload()
    abstract short getSCALE_ABSOLUTE()
    abstract short getSCALE_PERCENTAGE()
    abstract short getSETPOINT_TYPE_AUTO()
    abstract short getSETPOINT_TYPE_DEHUMIDIFIER()
    abstract short getSETPOINT_TYPE_HUMIDIFIER()
    abstract java.lang.Short getSetpointType()
    abstract void setMaximumPrecision(java.lang.Short a)
    abstract void setMaximumScale(java.lang.Short a)
    abstract void setMaximumScaledValue(java.math.BigDecimal a)
    abstract void setMaximumSize(java.lang.Short a)
    abstract void setMaximumValue(java.util.List a)
    abstract void setMinimumPrecision(java.lang.Short a)
    abstract void setMinimumScale(java.lang.Short a)
    abstract void setMinimumScaledValue(java.math.BigDecimal a)
    abstract void setMinimumSize(java.lang.Short a)
    abstract void setMinimumValue(java.util.List a)
    abstract void setSetpointType(java.lang.Short a)
}
