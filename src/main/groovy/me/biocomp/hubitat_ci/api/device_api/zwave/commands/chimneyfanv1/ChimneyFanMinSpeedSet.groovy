package me.biocomp.hubitat_ci.api.device_api.zwave.commands.chimneyfanv1

trait ChimneyFanMinSpeedSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMinSpeed()
    abstract java.util.List getPayload()
    abstract void setMinSpeed(java.lang.Short a)
}
