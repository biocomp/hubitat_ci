package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontrolsetpointv2

trait HumidityControlSetpointSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAuto()
    abstract java.lang.Short getBitmask()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getDehumidifier()
    abstract java.lang.Boolean getHumidifier()
    abstract java.util.List getPayload()
    abstract void setAuto(java.lang.Boolean a)
    abstract void setBitmask(java.lang.Short a)
    abstract void setDehumidifier(java.lang.Boolean a)
    abstract void setHumidifier(java.lang.Boolean a)
}
