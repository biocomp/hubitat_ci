package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait BatteryV2 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryGet batteryGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryGet batteryGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryHealthGet batteryHealthGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryHealthGet batteryHealthGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryHealthReport batteryHealthReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryHealthReport batteryHealthReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryReport batteryReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv2.BatteryReport batteryReport(java.util.Map a)
}
