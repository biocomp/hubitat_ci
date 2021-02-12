package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait BatteryV3 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryGet batteryGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryGet batteryGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryHealthGet batteryHealthGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryHealthGet batteryHealthGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryHealthReport batteryHealthReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryHealthReport batteryHealthReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryReport batteryReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv3.BatteryReport batteryReport(java.util.Map a)
}
