package me.biocomp.hubitat_ci.api.device_api.zwave.commands.simpleavcontrolv1

trait SimpleAvControlReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNumberOfReports()
    abstract java.util.List getPayload()
    abstract void setNumberOfReports(java.lang.Short a)
}
