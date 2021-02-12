package me.biocomp.hubitat_ci.api.device_api.zwave.commands.simpleavcontrolv1

trait SimpleAvControlSupportedReport {
    abstract java.lang.String format()
    abstract java.util.List getBitMasks()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getReportNo()
    abstract void setBitMasks(java.util.List a)
    abstract void setReportNo(java.lang.Short a)
}
