package me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1

trait IpConfigurationGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
}
