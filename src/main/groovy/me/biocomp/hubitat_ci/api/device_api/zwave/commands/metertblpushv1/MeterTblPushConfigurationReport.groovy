package me.biocomp.hubitat_ci.api.device_api.zwave.commands.metertblpushv1

trait MeterTblPushConfigurationReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getIntervalDays()
    abstract java.lang.Short getIntervalHours()
    abstract java.lang.Short getIntervalMinutes()
    abstract java.lang.Short getIntervalMonths()
    abstract java.lang.Short getOperatingStatusPushMode()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getPs()
    abstract java.lang.Integer getPushDataset()
    abstract java.lang.Short getPushNodeId()
    abstract void setIntervalDays(java.lang.Short a)
    abstract void setIntervalHours(java.lang.Short a)
    abstract void setIntervalMinutes(java.lang.Short a)
    abstract void setIntervalMonths(java.lang.Short a)
    abstract void setOperatingStatusPushMode(java.lang.Short a)
    abstract void setPs(java.lang.Boolean a)
    abstract void setPushDataset(java.lang.Integer a)
    abstract void setPushNodeId(java.lang.Short a)
}
