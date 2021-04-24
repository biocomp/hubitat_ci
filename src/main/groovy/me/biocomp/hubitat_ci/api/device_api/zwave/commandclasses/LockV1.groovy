package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait LockV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.lockv1.LockGet lockGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.lockv1.LockGet lockGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.lockv1.LockReport lockReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.lockv1.LockReport lockReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.lockv1.LockSet lockSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.lockv1.LockSet lockSet(java.util.Map a)
}
