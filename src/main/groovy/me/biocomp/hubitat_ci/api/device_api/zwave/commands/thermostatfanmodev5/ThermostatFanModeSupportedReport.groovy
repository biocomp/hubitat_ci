package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatfanmodev5

trait ThermostatFanModeSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAuto()
    abstract java.lang.Boolean getAutoHigh()
    abstract java.lang.Boolean getAutoMedium()
    abstract java.lang.String getCMD()
    abstract java.lang.Boolean getCirculation()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getExternalCirculation()
    abstract java.lang.Boolean getHigh()
    abstract java.lang.Boolean getHumidityCirculation()
    abstract java.lang.Boolean getLeftRight()
    abstract java.lang.Boolean getLow()
    abstract java.lang.Boolean getMedium()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getQuiet()
    abstract java.lang.Boolean getUpDown()
    abstract void setAuto(java.lang.Boolean a)
    abstract void setAutoHigh(java.lang.Boolean a)
    abstract void setAutoMedium(java.lang.Boolean a)
    abstract void setCirculation(java.lang.Boolean a)
    abstract void setExternalCirculation(java.lang.Boolean a)
    abstract void setHigh(java.lang.Boolean a)
    abstract void setHumidityCirculation(java.lang.Boolean a)
    abstract void setLeftRight(java.lang.Boolean a)
    abstract void setLow(java.lang.Boolean a)
    abstract void setMedium(java.lang.Boolean a)
    abstract void setQuiet(java.lang.Boolean a)
    abstract void setUpDown(java.lang.Boolean a)
}
