package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait AlarmV2 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmGet alarmGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmGet alarmGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmReport alarmReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmReport alarmReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmSet alarmSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmSet alarmSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmTypeSupportedGet alarmTypeSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmTypeSupportedGet alarmTypeSupportedGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmTypeSupportedReport alarmTypeSupportedReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv2.AlarmTypeSupportedReport alarmTypeSupportedReport(java.util.Map a)
}
