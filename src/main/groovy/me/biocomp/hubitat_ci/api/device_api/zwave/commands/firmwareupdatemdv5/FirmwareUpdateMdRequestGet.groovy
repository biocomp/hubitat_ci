package me.biocomp.hubitat_ci.api.device_api.zwave.commands.firmwareupdatemdv5

trait FirmwareUpdateMdRequestGet {
    abstract java.lang.String format()
    abstract java.lang.Boolean getActivation()
    abstract java.lang.String getCMD()
    abstract java.lang.Integer getChecksum()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getFirmwareId()
    abstract java.lang.Short getFirmwareTarget()
    abstract java.lang.Integer getFragmentSize()
    abstract java.lang.Integer getManufacturerId()
    abstract java.util.List getPayload()
    abstract void setActivation(java.lang.Boolean a)
    abstract void setChecksum(java.lang.Integer a)
    abstract void setFirmwareId(java.lang.Integer a)
    abstract void setFirmwareTarget(java.lang.Short a)
    abstract void setFragmentSize(java.lang.Integer a)
    abstract void setManufacturerId(java.lang.Integer a)
}
