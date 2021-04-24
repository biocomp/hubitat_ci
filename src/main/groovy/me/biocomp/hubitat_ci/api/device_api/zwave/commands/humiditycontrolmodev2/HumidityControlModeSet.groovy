package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontrolmodev2

trait HumidityControlModeSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getMODE_AUTO()
    abstract short getMODE_DEHUMIDIFY()
    abstract short getMODE_HUMIDIFY()
    abstract short getMODE_OFF()
    abstract java.lang.Short getMode()
    abstract java.util.List getPayload()
    abstract java.lang.Short setMode(java.lang.Short a)
}
