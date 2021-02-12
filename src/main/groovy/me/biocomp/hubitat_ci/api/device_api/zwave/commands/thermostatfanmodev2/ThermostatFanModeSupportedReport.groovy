package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatfanmodev2

trait ThermostatFanModeSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAuto()
    abstract java.lang.Boolean getAutoHigh()
    abstract java.lang.Boolean getAutoMedium()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getHigh()
    abstract java.lang.Boolean getLow()
    abstract java.lang.Boolean getMedium()
    abstract java.util.List getPayload()
    abstract void setAuto(java.lang.Boolean a)
    abstract void setAutoHigh(java.lang.Boolean a)
    abstract void setAutoMedium(java.lang.Boolean a)
    abstract void setHigh(java.lang.Boolean a)
    abstract void setLow(java.lang.Boolean a)
    abstract void setMedium(java.lang.Boolean a)
}
