package me.biocomp.hubitat_ci.api.device_api.zwave.commands.metertblmonitorv2

trait MeterTblStatusReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getCurrentOperatingStatus()
    abstract java.util.List getPayload()
    abstract java.lang.Short getReportsToFollow()
    abstract void setCurrentOperatingStatus(java.lang.Integer a)
    abstract void setReportsToFollow(java.lang.Short a)
}
