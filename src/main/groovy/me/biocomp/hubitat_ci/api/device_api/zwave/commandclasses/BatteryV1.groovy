package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait BatteryV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv1.BatteryGet batteryGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv1.BatteryGet batteryGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv1.BatteryReport batteryReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv1.BatteryReport batteryReport(java.util.Map a)
}
