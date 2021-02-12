package me.biocomp.hubitat_ci.api.device_api.zwave.commands.security2v1

trait KexReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getEcho()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getRequestCSA()
    abstract java.lang.Short getRequestedKeys()
    abstract java.lang.Short getSupportedProfiles()
    abstract java.lang.Short getSupportedSchemes()
    abstract void setEcho(java.lang.Boolean a)
    abstract void setRequestCSA(java.lang.Boolean a)
    abstract void setRequestedKeys(java.lang.Short a)
    abstract void setSupportedProfiles(java.lang.Short a)
    abstract void setSupportedSchemes(java.lang.Short a)
}
