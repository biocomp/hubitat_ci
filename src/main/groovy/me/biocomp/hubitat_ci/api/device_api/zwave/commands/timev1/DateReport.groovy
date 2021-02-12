package me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1

trait DateReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDay()
    abstract java.lang.Short getMonth()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getYear()
    abstract void setDay(java.lang.Short a)
    abstract void setMonth(java.lang.Short a)
    abstract void setYear(java.lang.Integer a)
}
