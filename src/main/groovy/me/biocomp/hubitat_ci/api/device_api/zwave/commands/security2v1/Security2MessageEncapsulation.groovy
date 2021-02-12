package me.biocomp.hubitat_ci.api.device_api.zwave.commands.security2v1

trait Security2MessageEncapsulation {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getEncryptedExtension()
    abstract java.util.List getEncryptedExtensions()
    abstract java.lang.Boolean getExtension()
    abstract java.util.List getExtensions()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSequenceNumber()
    abstract void setEncryptedExtension(java.lang.Boolean a)
    abstract void setEncryptedExtensions(java.util.List a)
    abstract void setExtension(java.lang.Boolean a)
    abstract void setExtensions(java.util.List a)
    abstract void setSequenceNumber(java.lang.Short a)
}
