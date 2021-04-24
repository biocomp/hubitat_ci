package me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1

trait DmxAddressReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getChannelId()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getPageId()
    abstract java.util.List getPayload()
    abstract void setChannelId(java.lang.Short a)
    abstract void setPageId(java.lang.Short a)
}
