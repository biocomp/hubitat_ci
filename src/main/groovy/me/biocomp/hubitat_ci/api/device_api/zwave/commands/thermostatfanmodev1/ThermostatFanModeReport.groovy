package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatfanmodev1

trait ThermostatFanModeReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getFAN_MODE_AUTO_HIGH()
    abstract java.lang.Short getFAN_MODE_AUTO_LOW()
    abstract java.lang.Short getFAN_MODE_HIGH()
    abstract java.lang.Short getFAN_MODE_LOW()
    abstract java.lang.Short getFanMode()
    abstract java.util.List getPayload()
    abstract void setFAN_MODE_AUTO_HIGH(java.lang.Short a)
    abstract void setFAN_MODE_AUTO_LOW(java.lang.Short a)
    abstract void setFAN_MODE_HIGH(java.lang.Short a)
    abstract void setFAN_MODE_LOW(java.lang.Short a)
    abstract void setFanMode(java.lang.Short a)
}
