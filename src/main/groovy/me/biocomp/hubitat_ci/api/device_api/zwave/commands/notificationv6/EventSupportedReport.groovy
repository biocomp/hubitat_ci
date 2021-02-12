package me.biocomp.hubitat_ci.api.device_api.zwave.commands.notificationv6

trait EventSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNotificationType()
    abstract java.util.List getPayload()
    abstract java.util.Map getSupportedEvents()
    abstract void setNotificationType(java.lang.Short a)
    abstract void setSupportedEvents(java.util.Map a)
}
