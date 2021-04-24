package me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1

trait TonePlaySet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getToneIdentifier()
    abstract void setToneIdentifier(java.lang.Short a)
}
