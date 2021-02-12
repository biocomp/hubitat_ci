package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SensorAlarmV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmGet sensorAlarmGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmGet sensorAlarmGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmReport sensorAlarmReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmReport sensorAlarmReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmSupportedGet sensorAlarmSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmSupportedGet sensorAlarmSupportedGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmSupportedReport sensorAlarmSupportedReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1.SensorAlarmSupportedReport sensorAlarmSupportedReport(java.util.Map a)
}
