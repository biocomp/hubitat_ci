package me.biocomp.hubitat_ci.api.device_api.zwave.commands.irrigationv1

trait IrrigationValveRun {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getDuration()
    abstract java.lang.Boolean getMasterValve()
    abstract java.util.List getPayload()
    abstract java.lang.Short getValveID()
    abstract void setDuration(java.lang.Integer a)
    abstract void setMasterValve(java.lang.Boolean a)
    abstract void setValveID(java.lang.Short a)
}
