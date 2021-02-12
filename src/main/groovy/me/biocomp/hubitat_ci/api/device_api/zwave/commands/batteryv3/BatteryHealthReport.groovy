package me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3

trait BatteryHealthReport {
    abstract java.lang.String format()
    abstract java.util.List getBatteryTemperature()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMaximumCapacity()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrecision()
    abstract java.lang.Short getScale()
    abstract java.math.BigDecimal getScaledBatteryTemperature()
    abstract java.lang.Short getSize()
    abstract void setBatteryTemperature(java.util.List a)
    abstract void setMaximumCapacity(java.lang.Short a)
    abstract void setPrecision(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScaledBatteryTemperature(java.math.BigDecimal a)
    abstract void setSize(java.lang.Short a)
}
