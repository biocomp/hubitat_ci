package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontroloperatingstatev1

trait HumidityControlOperatingStateReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getOPERATING_STATE_DEHUMIDIFYING()
    abstract short getOPERATING_STATE_HUMIDIFYING()
    abstract short getOPERATING_STATE_IDLE()
    abstract java.lang.Short getOperatingState()
    abstract java.util.List getPayload()
    abstract void setOperatingState(java.lang.Short a)
}
