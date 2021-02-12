package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait ClockV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.clockv1.ClockGet clockGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.clockv1.ClockGet clockGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.clockv1.ClockReport clockReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.clockv1.ClockReport clockReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.clockv1.ClockSet clockSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.clockv1.ClockSet clockSet(java.util.Map a)
}
