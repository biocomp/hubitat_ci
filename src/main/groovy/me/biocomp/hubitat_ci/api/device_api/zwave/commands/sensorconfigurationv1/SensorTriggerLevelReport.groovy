package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensorconfigurationv1

trait SensorTriggerLevelReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrecision()
    abstract java.lang.Short getScale()
    abstract java.math.BigDecimal getScaledTriggerValue()
    abstract java.lang.Short getSensorType()
    abstract java.lang.Short getSize()
    abstract java.util.List getTriggerValue()
    abstract void setPrecision(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScaledTriggerValue(java.math.BigDecimal a)
    abstract void setSensorType(java.lang.Short a)
    abstract void setSize(java.lang.Short a)
    abstract void setTriggerValue(java.util.List a)
}
