package me.biocomp.hubitat_ci.api.device_api.zwave.commands.irrigationv1

trait IrrigationValveTableRun {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.util.List getValveTableID()
    abstract void setValveTableID(java.util.List a)
}
