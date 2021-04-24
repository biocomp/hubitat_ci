package me.biocomp.hubitat_ci.api.device_api.zwave.commands.chimneyfanv1

trait ChimneyFanAlarmLogSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMESSAGE_RESET_LOG()
    abstract java.lang.Short getMessage()
    abstract java.util.List getPayload()
    abstract void setMESSAGE_RESET_LOG(java.lang.Short a)
    abstract void setMessage(java.lang.Short a)
}
