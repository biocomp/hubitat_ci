package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatfanmodev1

trait ThermostatFanModeSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getFAN_MODE_AUTO_HIGH()
    abstract short getFAN_MODE_AUTO_LOW()
    abstract short getFAN_MODE_HIGH()
    abstract short getFAN_MODE_LOW()
    abstract java.lang.Short getFanMode()
    abstract java.util.List getPayload()
    abstract void setFanMode(java.lang.Short a)
}
