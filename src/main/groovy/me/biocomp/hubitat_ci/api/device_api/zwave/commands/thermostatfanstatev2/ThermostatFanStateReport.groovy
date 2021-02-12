package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatfanstatev2

trait ThermostatFanStateReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getFAN_OPERATING_STATE_CIRCULATION_MODE()
    abstract short getFAN_OPERATING_STATE_HUMIDITY_CIRCULATION_MODE()
    abstract short getFAN_OPERATING_STATE_IDLE_OFF()
    abstract short getFAN_OPERATING_STATE_QUIET_CIRCULATION_MODE()
    abstract short getFAN_OPERATING_STATE_RIGHT_LEFT_CIRCULATION_MODE()
    abstract short getFAN_OPERATING_STATE_RUNNING()
    abstract short getFAN_OPERATING_STATE_RUNNING_HIGH()
    abstract short getFAN_OPERATING_STATE_RUNNING_MEDIUM()
    abstract short getFAN_OPERATING_STATE_UP_DOWN_CIRCULATION_MODE()
    abstract java.lang.Short getFanOperatingState()
    abstract java.util.List getPayload()
    abstract void setFanOperatingState(java.lang.Short a)
}
