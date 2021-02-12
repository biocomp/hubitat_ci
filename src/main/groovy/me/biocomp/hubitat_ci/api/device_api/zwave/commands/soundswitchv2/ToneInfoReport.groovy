package me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2

trait ToneInfoReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNameLength()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getToneDuration()
    abstract java.lang.Short getToneIdentifier()
    abstract java.lang.String getToneName()
    abstract void setNameLength(java.lang.Short a)
    abstract void setToneDuration(java.lang.Integer a)
    abstract void setToneIdentifier(java.lang.Short a)
    abstract void setToneName(java.lang.String a)
}
