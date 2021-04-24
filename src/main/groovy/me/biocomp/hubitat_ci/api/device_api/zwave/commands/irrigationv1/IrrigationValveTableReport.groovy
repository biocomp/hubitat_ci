package me.biocomp.hubitat_ci.api.device_api.zwave.commands.irrigationv1

trait IrrigationValveTableReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.util.List getValveTable()
    abstract java.lang.Short getValveTableID()
    abstract void setValveTable(java.util.List a)
    abstract void setValveTableID(java.lang.Short a)
}
