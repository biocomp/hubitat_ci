package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv4

trait MultiChannelCapabilityReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.util.List getCommandClass()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getDynamic()
    abstract java.lang.Short getEndPoint()
    abstract java.lang.Short getGenericDeviceClass()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSpecificDeviceClass()
    abstract void setCommandClass(java.util.List a)
    abstract void setDynamic(java.lang.Boolean a)
    abstract void setEndPoint(java.lang.Short a)
    abstract void setGenericDeviceClass(java.lang.Short a)
    abstract void setSpecificDeviceClass(java.lang.Short a)
}
