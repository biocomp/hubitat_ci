package me.biocomp.hubitat_ci.api.device_api.zwave.commands.chimneyfanv1

trait ChimneyFanModeSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMODE_OFF()
    abstract java.lang.Short getMODE_ON()
    abstract java.lang.Short getMode()
    abstract java.util.List getPayload()
    abstract void setMODE_OFF(java.lang.Short a)
    abstract void setMODE_ON(java.lang.Short a)
    abstract void setMode(java.lang.Short a)
}
