package me.biocomp.hubitat_ci.api.device_api.zwave.commands.firmwareupdatemdv3

trait FirmwareUpdateMdStatusReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSTATUS_IINVALID_FRAGMENT_SIZE()
    abstract java.lang.Short getSTATUS_NOT_DOWNLOADABLE()
    abstract java.lang.Short getSTATUS_SUCCESSFULLY()
    abstract java.lang.Short getSTATUS_UNABLE_TO_RECEIVE()
    abstract java.lang.Short getSTATUS_UNABLE_TO_RECEIVE_WITHOUT_CHECKSUM_ERROR()
    abstract java.lang.Short getStatus()
    abstract void setSTATUS_IINVALID_FRAGMENT_SIZE(java.lang.Short a)
    abstract void setSTATUS_NOT_DOWNLOADABLE(java.lang.Short a)
    abstract void setSTATUS_SUCCESSFULLY(java.lang.Short a)
    abstract void setSTATUS_UNABLE_TO_RECEIVE(java.lang.Short a)
    abstract void setSTATUS_UNABLE_TO_RECEIVE_WITHOUT_CHECKSUM_ERROR(java.lang.Short a)
    abstract void setStatus(java.lang.Short a)
}
