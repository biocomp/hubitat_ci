package me.biocomp.hubitat_ci.api.device_api.zwave.commands.centralscenev3

trait CentralSceneConfigurationReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getSlowRefresh()
    abstract void setSlowRefresh(java.lang.Boolean a)
}
