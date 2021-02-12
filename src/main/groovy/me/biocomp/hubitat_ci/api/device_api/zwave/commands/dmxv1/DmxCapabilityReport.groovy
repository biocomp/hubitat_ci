package me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1

trait DmxCapabilityReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getChannelId()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDeviceChannels()
    abstract java.lang.Short getMaxChannels()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getPropertyId()
    abstract void setChannelId(java.lang.Short a)
    abstract void setDeviceChannels(java.lang.Short a)
    abstract void setMaxChannels(java.lang.Short a)
    abstract void setPropertyId(java.lang.Integer a)
}
