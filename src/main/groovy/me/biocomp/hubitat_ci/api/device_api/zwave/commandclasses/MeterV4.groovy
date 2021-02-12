package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait MeterV4 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterGet meterGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterGet meterGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterReport meterReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterReport meterReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterReset meterReset()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterReset meterReset(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterSupportedGet meterSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterSupportedGet meterSupportedGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterSupportedReport meterSupportedReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv4.MeterSupportedReport meterSupportedReport(java.util.Map a)
}
