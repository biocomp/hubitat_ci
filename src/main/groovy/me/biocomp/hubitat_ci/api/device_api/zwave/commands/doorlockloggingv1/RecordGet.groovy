package me.biocomp.hubitat_ci.api.device_api.zwave.commands.doorlockloggingv1

trait RecordGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRecordNumber()
    abstract void setRecordNumber(java.lang.Short a)
}
