package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait MeterPulseV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterpulsev1.MeterPulseGet meterPulseGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterpulsev1.MeterPulseGet meterPulseGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterpulsev1.MeterPulseReport meterPulseReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterpulsev1.MeterPulseReport meterPulseReport(java.util.Map a)
}
