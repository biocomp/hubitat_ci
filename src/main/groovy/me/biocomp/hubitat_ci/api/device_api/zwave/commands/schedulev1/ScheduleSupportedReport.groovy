package me.biocomp.hubitat_ci.api.device_api.zwave.commands.schedulev1

trait ScheduleSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getFallbackSupport()
    abstract java.lang.Short getNumberOfSupportedCc()
    abstract java.lang.Short getNumberOfSupportedScheduleId()
    abstract java.lang.Boolean getOverrideSupport()
    abstract java.util.List getPayload()
    abstract java.lang.Short getStartTimeSupport()
    abstract java.lang.Boolean getSupportEnabledisable()
    abstract java.lang.Short getSupportedOverrideTypes()
    abstract void setFallbackSupport(java.lang.Boolean a)
    abstract void setNumberOfSupportedCc(java.lang.Short a)
    abstract void setNumberOfSupportedScheduleId(java.lang.Short a)
    abstract void setOverrideSupport(java.lang.Boolean a)
    abstract void setStartTimeSupport(java.lang.Short a)
    abstract void setSupportEnabledisable(java.lang.Boolean a)
    abstract void setSupportedOverrideTypes(java.lang.Short a)
}
