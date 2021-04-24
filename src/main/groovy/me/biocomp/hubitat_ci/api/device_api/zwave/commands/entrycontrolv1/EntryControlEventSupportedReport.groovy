package me.biocomp.hubitat_ci.api.device_api.zwave.commands.entrycontrolv1

trait EntryControlEventSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getDataTypeSupportedBitMask()
    abstract java.util.List getEventTypeSupportedBitMask()
    abstract java.lang.Integer getKeyCachedSizeMax()
    abstract java.lang.Integer getKeyCachedTimeoutMax()
    abstract java.util.List getPayload()
    abstract void setDataTypeSupportedBitMask(java.util.List a)
    abstract void setEventTypeSupportedBitMask(java.util.List a)
    abstract void setKeyCachedSizeMax(java.lang.Integer a)
    abstract void setKeyCachedTimeoutMax(java.lang.Integer a)
}
