package me.biocomp.hubitat_ci.api.device_api.zwave.commands.firmwareupdatemdv3

trait FirmwareUpdateMdReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Integer getChecksum()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getData()
    abstract java.lang.Boolean getLast()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getReportNumber()
    abstract void setChecksum(java.lang.Integer a)
    abstract void setData(java.util.List a)
    abstract void setLast(java.lang.Boolean a)
    abstract void setReportNumber(java.lang.Integer a)
}
