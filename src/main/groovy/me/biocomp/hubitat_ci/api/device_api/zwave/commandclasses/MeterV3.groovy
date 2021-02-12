package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait MeterV3 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterGet meterGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterGet meterGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterReport meterReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterReport meterReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterReset meterReset()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterReset meterReset(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterSupportedGet meterSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterSupportedGet meterSupportedGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterSupportedReport meterSupportedReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv3.MeterSupportedReport meterSupportedReport(java.util.Map a)
}
