package me.biocomp.hubitat_ci.api.device_api.zwave.commands.antitheftunlockv1

trait AntitheftUnlockSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getMagicCode()
    abstract java.lang.Short getMagicCodeLength()
    abstract java.util.List getPayload()
    abstract void setMagicCode(java.util.List a)
    abstract void setMagicCodeLength(java.lang.Short a)
}
