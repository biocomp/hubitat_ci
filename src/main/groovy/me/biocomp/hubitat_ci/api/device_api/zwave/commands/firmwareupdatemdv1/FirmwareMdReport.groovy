package me.biocomp.hubitat_ci.api.device_api.zwave.commands.firmwareupdatemdv1

trait FirmwareMdReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Integer getChecksum()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getFirmwareId()
    abstract java.lang.Integer getManufacturerId()
    abstract java.util.List getPayload()
    abstract void setChecksum(java.lang.Integer a)
    abstract void setFirmwareId(java.lang.Integer a)
    abstract void setManufacturerId(java.lang.Integer a)
}
