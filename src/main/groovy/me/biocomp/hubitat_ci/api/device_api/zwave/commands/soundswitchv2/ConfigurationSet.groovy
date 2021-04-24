package me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2

trait ConfigurationSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDefaultToneIdentifier()
    abstract java.util.List getPayload()
    abstract java.lang.Short getVolume()
    abstract void setDefaultToneIdentifier(java.lang.Short a)
    abstract void setVolume(java.lang.Short a)
}
