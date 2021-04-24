package me.biocomp.hubitat_ci.api.device_api.zwave.commands.firmwareupdatemdv4

trait FirmwareMdReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Integer getChecksum()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getFirmwareId()
    abstract java.util.List getFirmwareIds()
    abstract java.lang.Boolean getFirmwareUpgradable()
    abstract java.lang.Integer getManufacturerId()
    abstract java.lang.Integer getMaxFragmentSize()
    abstract java.lang.Short getNumberOfTargets()
    abstract java.util.List getPayload()
    abstract void setChecksum(java.lang.Integer a)
    abstract void setFirmwareId(java.lang.Integer a)
    abstract void setFirmwareIds(java.util.List a)
    abstract void setFirmwareUpgradable(java.lang.Boolean a)
    abstract void setManufacturerId(java.lang.Integer a)
    abstract void setMaxFragmentSize(java.lang.Integer a)
    abstract void setNumberOfTargets(java.lang.Short a)
}
