package me.biocomp.hubitat_ci.api.device_api.zwave.commands.prepaymentv1

trait PrepaymentSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getTypesSupported()
    abstract void setTypesSupported(java.lang.Short a)
}
