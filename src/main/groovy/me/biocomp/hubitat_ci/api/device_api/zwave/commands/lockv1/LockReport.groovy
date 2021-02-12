package me.biocomp.hubitat_ci.api.device_api.zwave.commands.lockv1

trait LockReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getLockState()
    abstract java.util.List getPayload()
    abstract void setLockState(java.lang.Short a)
}
