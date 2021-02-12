package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait MeterV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv1.MeterGet meterGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv1.MeterGet meterGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv1.MeterReport meterReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv1.MeterReport meterReport(java.util.Map a)
}
