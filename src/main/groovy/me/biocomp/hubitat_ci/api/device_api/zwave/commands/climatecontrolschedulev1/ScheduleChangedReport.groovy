package me.biocomp.hubitat_ci.api.device_api.zwave.commands.climatecontrolschedulev1

trait ScheduleChangedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getChangecounter()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setChangecounter(java.lang.Short a)
}
