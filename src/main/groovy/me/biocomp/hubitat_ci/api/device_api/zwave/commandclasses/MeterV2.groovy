package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait MeterV2 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterGet meterGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterGet meterGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterReport meterReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterReport meterReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterReset meterReset()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterReset meterReset(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterSupportedGet meterSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterSupportedGet meterSupportedGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterSupportedReport meterSupportedReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2.MeterSupportedReport meterSupportedReport(java.util.Map a)
}
