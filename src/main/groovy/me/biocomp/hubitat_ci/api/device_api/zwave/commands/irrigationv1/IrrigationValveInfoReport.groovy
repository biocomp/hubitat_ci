package me.biocomp.hubitat_ci.api.device_api.zwave.commands.irrigationv1

trait IrrigationValveInfoReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getConnected()
    abstract java.lang.Boolean getMaster()
    abstract java.lang.Short getNominalCurrent()
    abstract java.util.List getPayload()
    abstract short getVALVE_ERROR_CURRENT_HIGH_THRESHOLD()
    abstract short getVALVE_ERROR_CURRENT_LOW_THRESHOLD()
    abstract short getVALVE_ERROR_FLOW_HIGH_THRESHOLD()
    abstract short getVALVE_ERROR_FLOW_LOW_THRESHOLD()
    abstract short getVALVE_ERROR_MAXIMUM_FLOW()
    abstract short getVALVE_ERROR_SHORT_CIRCUIT()
    abstract java.lang.Short getValveErrorStatus()
    abstract java.lang.Short getValveID()
    abstract void setConnected(java.lang.Boolean a)
    abstract void setMaster(java.lang.Boolean a)
    abstract void setNominalCurrent(java.lang.Short a)
    abstract void setValveErrorStatus(java.lang.Short a)
    abstract void setValveID(java.lang.Short a)
}