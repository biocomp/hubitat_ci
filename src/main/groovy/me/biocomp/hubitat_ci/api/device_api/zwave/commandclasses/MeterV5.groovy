package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait MeterV5 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterGet meterGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterGet meterGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterReport meterReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterReport meterReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterReset meterReset()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterReset meterReset(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterSupportedGet meterSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterSupportedGet meterSupportedGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterSupportedReport meterSupportedReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv5.MeterSupportedReport meterSupportedReport(java.util.Map a)
}
