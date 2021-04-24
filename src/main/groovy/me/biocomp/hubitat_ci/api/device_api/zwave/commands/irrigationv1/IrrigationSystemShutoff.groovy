package me.biocomp.hubitat_ci.api.device_api.zwave.commands.irrigationv1

trait IrrigationSystemShutoff {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDuration()
    abstract java.util.List getPayload()
    abstract void setDuration(java.lang.Short a)
}
