package me.biocomp.hubitat_ci.api.device_api.zwave.commands.basicwindowcoveringv1

trait BasicWindowCoveringStartLevelChange {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getOpenClose()
    abstract java.util.List getPayload()
    abstract void setOpenClose(java.lang.Boolean a)
}
