package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1

trait MultiInstanceGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClass()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setCommandClass(java.lang.Short a)
}
