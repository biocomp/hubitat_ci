package me.biocomp.hubitat_ci.api.device_api.zwave.commands.irrigationv1

trait IrrigationSystemInfoReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getMasterValve()
    abstract java.util.List getPayload()
    abstract java.lang.Short getTotalNumberOfValveTables()
    abstract java.lang.Short getTotalNumberOfValves()
    abstract java.lang.Short getValveTableMaxSize()
    abstract void setMasterValve(java.lang.Boolean a)
    abstract void setTotalNumberOfValveTables(java.lang.Short a)
    abstract void setTotalNumberOfValves(java.lang.Short a)
    abstract void setValveTableMaxSize(java.lang.Short a)
}
