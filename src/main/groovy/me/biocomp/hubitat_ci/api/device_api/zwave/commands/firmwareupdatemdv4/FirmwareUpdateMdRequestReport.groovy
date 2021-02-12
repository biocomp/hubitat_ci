package me.biocomp.hubitat_ci.api.device_api.zwave.commands.firmwareupdatemdv4

trait FirmwareUpdateMdRequestReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSTATUS_IINVALID_FRAGMENT_SIZE()
    abstract java.lang.Short getSTATUS_INVALID_COMBINATION()
    abstract java.lang.Short getSTATUS_NOT_DOWNLOADABLE()
    abstract java.lang.Short getSTATUS_REQUIRES_AUTHENTICATION()
    abstract java.lang.Short getSTATUS_VALID_COMBINATION()
    abstract java.lang.Short getStatus()
    abstract void setSTATUS_IINVALID_FRAGMENT_SIZE(java.lang.Short a)
    abstract void setSTATUS_INVALID_COMBINATION(java.lang.Short a)
    abstract void setSTATUS_NOT_DOWNLOADABLE(java.lang.Short a)
    abstract void setSTATUS_REQUIRES_AUTHENTICATION(java.lang.Short a)
    abstract void setSTATUS_VALID_COMBINATION(java.lang.Short a)
    abstract void setStatus(java.lang.Short a)
}
