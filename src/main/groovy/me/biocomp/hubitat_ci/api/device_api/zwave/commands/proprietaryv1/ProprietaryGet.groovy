package me.biocomp.hubitat_ci.api.device_api.zwave.commands.proprietaryv1

trait ProprietaryGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getData()
    abstract java.util.List getPayload()
    abstract void setData(java.util.List a)
}
