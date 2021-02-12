package me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1

trait DmxData {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getDmxChannel()
    abstract java.lang.Short getPage()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSequenceNo()
    abstract java.lang.Short getSource()
    abstract void setDmxChannel(java.util.List a)
    abstract void setPage(java.lang.Short a)
    abstract void setSequenceNo(java.lang.Short a)
    abstract void setSource(java.lang.Short a)
}
