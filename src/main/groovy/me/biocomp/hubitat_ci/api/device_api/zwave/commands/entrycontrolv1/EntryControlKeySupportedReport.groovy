package me.biocomp.hubitat_ci.api.device_api.zwave.commands.entrycontrolv1

trait EntryControlKeySupportedReport {
    abstract java.lang.String format()
    abstract java.lang.Short getBitMaskLength()
    abstract java.util.List getBitmask()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setBitMaskLength(java.lang.Short a)
    abstract void setBitmask(java.util.List a)
}
