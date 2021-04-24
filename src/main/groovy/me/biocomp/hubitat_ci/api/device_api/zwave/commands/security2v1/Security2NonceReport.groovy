package me.biocomp.hubitat_ci.api.device_api.zwave.commands.security2v1

trait Security2NonceReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getMOS()
    abstract java.util.List getPayload()
    abstract java.util.List getREI()
    abstract java.lang.Boolean getSOS()
    abstract java.lang.Short getSequenceNumber()
    abstract void setMOS(java.lang.Boolean a)
    abstract void setREI(java.util.List a)
    abstract void setSOS(java.lang.Boolean a)
    abstract void setSequenceNumber(java.lang.Short a)
}
