package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv3

trait MultiChannelEndPointReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getDynamic()
    abstract java.lang.Short getEndPoints()
    abstract java.lang.Boolean getIdentical()
    abstract java.util.List getPayload()
    abstract void setDynamic(java.lang.Boolean a)
    abstract void setEndPoints(java.lang.Short a)
    abstract void setIdentical(java.lang.Boolean a)
}
