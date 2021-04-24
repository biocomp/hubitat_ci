package me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1

trait TonesNumberReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSupportedTones()
    abstract void setSupportedTones(java.lang.Short a)
}
