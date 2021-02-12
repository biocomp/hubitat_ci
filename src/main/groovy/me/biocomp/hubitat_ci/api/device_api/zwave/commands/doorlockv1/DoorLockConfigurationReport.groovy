package me.biocomp.hubitat_ci.api.device_api.zwave.commands.doorlockv1

trait DoorLockConfigurationReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getInsideDoorHandlesState()
    abstract java.lang.Short getLockTimeoutMinutes()
    abstract java.lang.Short getLockTimeoutSeconds()
    abstract java.lang.Short getOPERATION_TYPE_CONSTANT_OPERATION()
    abstract java.lang.Short getOPERATION_TYPE_TIMED_OPERATION()
    abstract java.lang.Short getOperationType()
    abstract java.lang.Short getOutsideDoorHandlesState()
    abstract java.util.List getPayload()
    abstract void setInsideDoorHandlesState(java.lang.Short a)
    abstract void setLockTimeoutMinutes(java.lang.Short a)
    abstract void setLockTimeoutSeconds(java.lang.Short a)
    abstract void setOPERATION_TYPE_CONSTANT_OPERATION(java.lang.Short a)
    abstract void setOPERATION_TYPE_TIMED_OPERATION(java.lang.Short a)
    abstract void setOperationType(java.lang.Short a)
    abstract void setOutsideDoorHandlesState(java.lang.Short a)
}
