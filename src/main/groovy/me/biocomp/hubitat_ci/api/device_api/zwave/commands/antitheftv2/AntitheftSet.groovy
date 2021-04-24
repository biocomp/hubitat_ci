package me.biocomp.hubitat_ci.api.device_api.zwave.commands.antitheftv2

trait AntitheftSet {
    abstract java.lang.String format()
    abstract java.util.List getAntitheftHintByte()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getEnable()
    abstract java.util.List getMagicCode()
    abstract java.lang.Integer getManufacturerID()
    abstract java.lang.Short getNumberOfAntitheftHintBytes()
    abstract java.lang.Short getNumberOfMagicCodeBytes()
    abstract java.util.List getPayload()
    abstract void setAntitheftHintByte(java.util.List a)
    abstract void setEnable(java.lang.Short a)
    abstract void setMagicCode(java.util.List a)
    abstract void setManufacturerID(java.lang.Integer a)
    abstract void setNumberOfAntitheftHintBytes(java.lang.Short a)
    abstract void setNumberOfMagicCodeBytes(java.lang.Short a)
}
