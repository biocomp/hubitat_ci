package me.biocomp.hubitat_ci.api.device_api.zwave.commands.security2v1

trait Security2TransferEnd {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getKeyVerified()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getRequestComplete()
    abstract void setKeyVerified(java.lang.Boolean a)
    abstract void setRequestComplete(java.lang.Boolean a)
}
