package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontrolmodev1

trait HumidityControlModeSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.Short getBitmask()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getModeDehumidify()
    abstract java.lang.Boolean getModeHumidify()
    abstract java.lang.Boolean getModeOff()
    abstract java.util.List getPayload()
    abstract void setBitmask(java.lang.Short a)
    abstract void setModeDehumidify(java.lang.Boolean a)
    abstract void setModeHumidify(java.lang.Boolean a)
    abstract void setModeOff(java.lang.Boolean a)
}
