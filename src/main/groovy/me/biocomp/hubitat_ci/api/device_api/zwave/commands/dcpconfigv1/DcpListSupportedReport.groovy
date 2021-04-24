package me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1

trait DcpListSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDcpListSize()
    abstract java.lang.Short getFreeDcpListEntries()
    abstract java.util.List getPayload()
    abstract void setDcpListSize(java.lang.Short a)
    abstract void setFreeDcpListEntries(java.lang.Short a)
}
