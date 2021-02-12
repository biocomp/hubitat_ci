package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait AlarmV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv1.AlarmGet alarmGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv1.AlarmGet alarmGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv1.AlarmReport alarmReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv1.AlarmReport alarmReport(java.util.Map a)
}
