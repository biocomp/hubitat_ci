package me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv2

trait WakeUpIntervalSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNodeid()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getSeconds()
    abstract void setNodeid(java.lang.Short a)
    abstract void setSeconds(java.lang.Integer a)
}
