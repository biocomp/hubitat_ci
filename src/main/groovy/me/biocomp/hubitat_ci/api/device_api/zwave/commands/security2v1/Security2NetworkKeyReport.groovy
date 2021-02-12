package me.biocomp.hubitat_ci.api.device_api.zwave.commands.security2v1

trait Security2NetworkKeyReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGrantedKey()
    abstract java.util.List getNetworkKey()
    abstract java.util.List getPayload()
    abstract void setGrantedKey(java.lang.Short a)
    abstract void setNetworkKey(java.util.List a)
}
