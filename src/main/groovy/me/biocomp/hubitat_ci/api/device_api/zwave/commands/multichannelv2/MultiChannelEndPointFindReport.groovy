package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv2

trait MultiChannelEndPointFindReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getEndPoints()
    abstract java.lang.Short getGenericDeviceClass()
    abstract java.util.List getPayload()
    abstract java.lang.Short getReportsToFollow()
    abstract java.lang.Short getSpecificDeviceClass()
    abstract void setEndPoints(java.util.List a)
    abstract void setGenericDeviceClass(java.lang.Short a)
    abstract void setReportsToFollow(java.lang.Short a)
    abstract void setSpecificDeviceClass(java.lang.Short a)
}
