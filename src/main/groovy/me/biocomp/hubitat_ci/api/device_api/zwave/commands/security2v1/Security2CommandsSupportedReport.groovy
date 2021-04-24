package me.biocomp.hubitat_ci.api.device_api.zwave.commands.security2v1

trait Security2CommandsSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.util.List getCommandClasses()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setCommandClasses(java.util.List a)
}
