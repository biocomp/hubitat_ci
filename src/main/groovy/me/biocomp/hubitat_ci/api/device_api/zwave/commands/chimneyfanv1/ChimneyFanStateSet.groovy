package me.biocomp.hubitat_ci.api.device_api.zwave.commands.chimneyfanv1

trait ChimneyFanStateSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSTATE_NEXT_STATE()
    abstract java.lang.Short getState()
    abstract void setSTATE_NEXT_STATE(java.lang.Short a)
    abstract void setState(java.lang.Short a)
}
