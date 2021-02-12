package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationcommandconfigurationv1

trait CommandRecordsSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getConfCmd()
    abstract java.lang.Integer getFreeCommandRecords()
    abstract java.lang.Short getMaxCommandLength()
    abstract java.lang.Integer getMaxCommandRecords()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getVc()
    abstract void setConfCmd(java.lang.Boolean a)
    abstract void setFreeCommandRecords(java.lang.Integer a)
    abstract void setMaxCommandLength(java.lang.Short a)
    abstract void setMaxCommandRecords(java.lang.Integer a)
    abstract void setVc(java.lang.Boolean a)
}
