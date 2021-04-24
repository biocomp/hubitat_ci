package me.biocomp.hubitat_ci.api.device_api.zwave.commands.climatecontrolschedulev1

trait ScheduleOverrideReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getOVERRIDE_STATE_NO_OVERRIDE()
    abstract java.lang.Short getOVERRIDE_STATE_PERMANENT_OVERRIDE()
    abstract java.lang.Short getOVERRIDE_STATE_RESERVED3()
    abstract java.lang.Short getOVERRIDE_STATE_TEMPORARY_OVERRIDE()
    abstract java.lang.Short getOverrideState()
    abstract java.lang.Short getOverrideType()
    abstract java.util.List getPayload()
    abstract void setOVERRIDE_STATE_NO_OVERRIDE(java.lang.Short a)
    abstract void setOVERRIDE_STATE_PERMANENT_OVERRIDE(java.lang.Short a)
    abstract void setOVERRIDE_STATE_RESERVED3(java.lang.Short a)
    abstract void setOVERRIDE_STATE_TEMPORARY_OVERRIDE(java.lang.Short a)
    abstract void setOverrideState(java.lang.Short a)
    abstract void setOverrideType(java.lang.Short a)
}
