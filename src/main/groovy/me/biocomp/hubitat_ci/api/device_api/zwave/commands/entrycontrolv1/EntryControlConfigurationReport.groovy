package me.biocomp.hubitat_ci.api.device_api.zwave.commands.entrycontrolv1

trait EntryControlConfigurationReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getKeyCacheSize()
    abstract java.lang.Short getKeyCacheTimeout()
    abstract java.util.List getPayload()
    abstract void setKeyCacheSize(java.lang.Short a)
    abstract void setKeyCacheTimeout(java.lang.Short a)
}
