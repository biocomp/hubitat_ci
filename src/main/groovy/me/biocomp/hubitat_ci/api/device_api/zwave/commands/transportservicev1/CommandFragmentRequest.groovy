package me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1

trait CommandFragmentRequest {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNumberOfOffsets()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSequenceNo()
    abstract void setNumberOfOffsets(java.lang.Short a)
    abstract void setSequenceNo(java.lang.Short a)
}
