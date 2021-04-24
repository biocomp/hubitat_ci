package me.biocomp.hubitat_ci.api.device_api.zwave.commands.schedulev1

trait ScheduleStateReport {
    abstract java.lang.String format()
    abstract java.lang.Short getActiveId1()
    abstract java.lang.Short getActiveId2()
    abstract java.lang.Short getActiveId3()
    abstract java.lang.Short getActiveIdN()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNumberOfSupportedScheduleId()
    abstract java.lang.Boolean getOverride()
    abstract java.util.List getPayload()
    abstract java.lang.Short getReportsToFollow()
    abstract void setActiveId1(java.lang.Short a)
    abstract void setActiveId2(java.lang.Short a)
    abstract void setActiveId3(java.lang.Short a)
    abstract void setActiveIdN(java.lang.Short a)
    abstract void setNumberOfSupportedScheduleId(java.lang.Short a)
    abstract void setOverride(java.lang.Boolean a)
    abstract void setReportsToFollow(java.lang.Short a)
}
