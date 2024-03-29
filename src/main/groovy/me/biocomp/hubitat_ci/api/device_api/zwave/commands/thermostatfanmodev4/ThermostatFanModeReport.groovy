package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatfanmodev4

trait ThermostatFanModeReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getFAN_MODE_AUTO_HIGH()
    abstract java.lang.Short getFAN_MODE_AUTO_LOW()
    abstract java.lang.Short getFAN_MODE_AUTO_MEDIUM()
    abstract java.lang.Short getFAN_MODE_CIRCULATION()
    abstract java.lang.Short getFAN_MODE_HIGH()
    abstract java.lang.Short getFAN_MODE_HUMIDITY()
    abstract short getFAN_MODE_LEFT_RIGHT()
    abstract java.lang.Short getFAN_MODE_LOW()
    abstract java.lang.Short getFAN_MODE_MEDIUM()
    abstract short getFAN_MODE_QUIET()
    abstract short getFAN_MODE_UP_DOWN()
    abstract java.lang.Short getFanMode()
    abstract java.lang.Boolean getOff()
    abstract java.util.List getPayload()
    abstract void setFAN_MODE_AUTO_HIGH(java.lang.Short a)
    abstract void setFAN_MODE_AUTO_LOW(java.lang.Short a)
    abstract void setFAN_MODE_AUTO_MEDIUM(java.lang.Short a)
    abstract void setFAN_MODE_CIRCULATION(java.lang.Short a)
    abstract void setFAN_MODE_HIGH(java.lang.Short a)
    abstract void setFAN_MODE_HUMIDITY(java.lang.Short a)
    abstract void setFAN_MODE_LOW(java.lang.Short a)
    abstract void setFAN_MODE_MEDIUM(java.lang.Short a)
    abstract void setFanMode(java.lang.Short a)
    abstract void setOff(java.lang.Boolean a)
}
