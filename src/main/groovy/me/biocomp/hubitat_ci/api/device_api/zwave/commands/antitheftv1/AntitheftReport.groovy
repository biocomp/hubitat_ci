package me.biocomp.hubitat_ci.api.device_api.zwave.commands.antitheftv1

trait AntitheftReport {
    abstract java.lang.String format()
    abstract java.util.List getAntitheftHintByte()
    abstract java.lang.Short getAntitheftProtectionStatus()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getManufacturerID()
    abstract java.lang.Short getNumberOfAntitheftHintBytes()
    abstract java.util.List getPayload()
    abstract void setAntitheftHintByte(java.util.List a)
    abstract void setAntitheftProtectionStatus(java.lang.Short a)
    abstract void setManufacturerID(java.lang.Integer a)
    abstract void setNumberOfAntitheftHintBytes(java.lang.Short a)
}
