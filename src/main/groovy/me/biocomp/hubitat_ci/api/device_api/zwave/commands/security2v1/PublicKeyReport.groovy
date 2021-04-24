package me.biocomp.hubitat_ci.api.device_api.zwave.commands.security2v1

trait PublicKeyReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getIncludingNode()
    abstract java.util.List getPayload()
    abstract java.util.List getPublicKey()
    abstract void setIncludingNode(java.lang.Boolean a)
    abstract void setPublicKey(java.util.List a)
}
