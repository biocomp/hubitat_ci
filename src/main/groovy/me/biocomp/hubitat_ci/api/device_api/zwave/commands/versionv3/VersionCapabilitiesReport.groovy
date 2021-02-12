package me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv3

trait VersionCapabilitiesReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Boolean getCommandClass()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getVersion()
    abstract java.lang.Boolean getZwaveSoftware()
    abstract void setCommandClass(java.lang.Boolean a)
    abstract void setVersion(java.lang.Boolean a)
    abstract void setZwaveSoftware(java.lang.Boolean a)
}
