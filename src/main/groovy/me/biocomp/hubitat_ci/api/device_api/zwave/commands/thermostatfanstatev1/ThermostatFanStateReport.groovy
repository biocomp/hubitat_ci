package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatfanstatev1

trait ThermostatFanStateReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getFAN_OPERATING_STATE_IDLE_OFF()
    abstract short getFAN_OPERATING_STATE_RUNNING()
    abstract short getFAN_OPERATING_STATE_RUNNING_HIGH()
    abstract java.lang.Short getFanOperatingState()
    abstract java.util.List getPayload()
    abstract void setFanOperatingState(java.lang.Short a)
}
