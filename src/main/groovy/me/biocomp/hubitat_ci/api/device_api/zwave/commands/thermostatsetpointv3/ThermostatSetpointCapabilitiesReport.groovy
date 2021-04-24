package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatsetpointv3

trait ThermostatSetpointCapabilitiesReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMaxPrecision()
    abstract java.lang.Short getMaxScale()
    abstract java.math.BigDecimal getMaxScaledValue()
    abstract java.lang.Short getMaxSize()
    abstract java.util.List getMaxValue()
    abstract java.lang.Short getMinPrecision()
    abstract java.lang.Short getMinScale()
    abstract java.math.BigDecimal getMinScaledValue()
    abstract java.lang.Short getMinSize()
    abstract java.util.List getMinValue()
    abstract java.util.List getPayload()
    abstract short getSETPOINT_TYPE_AUTO_CHANGEOVER()
    abstract short getSETPOINT_TYPE_AWAY_COOLING()
    abstract short getSETPOINT_TYPE_AWAY_HEATING()
    abstract short getSETPOINT_TYPE_COOLING_1()
    abstract short getSETPOINT_TYPE_DRY_AIR()
    abstract short getSETPOINT_TYPE_ENERGY_SAVE_COOLING()
    abstract short getSETPOINT_TYPE_ENERGY_SAVE_HEATING()
    abstract short getSETPOINT_TYPE_FULL_POWER()
    abstract short getSETPOINT_TYPE_FURNACE()
    abstract short getSETPOINT_TYPE_HEATING_1()
    abstract short getSETPOINT_TYPE_MOIST_AIR()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED1()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED2()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED3()
    abstract short getSETPOINT_TYPE_NOT_SUPPORTED4()
    abstract java.lang.Short getSetpointType()
    abstract void setMaxPrecision(java.lang.Short a)
    abstract void setMaxScale(java.lang.Short a)
    abstract void setMaxScaledValue(java.math.BigDecimal a)
    abstract void setMaxSize(java.lang.Short a)
    abstract void setMaxValue(java.util.List a)
    abstract void setMinPrecision(java.lang.Short a)
    abstract void setMinScale(java.lang.Short a)
    abstract void setMinScaledValue(java.math.BigDecimal a)
    abstract void setMinSize(java.lang.Short a)
    abstract void setMinValue(java.util.List a)
    abstract void setSetpointType(java.lang.Short a)
}
