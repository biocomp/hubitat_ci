package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SilenceAlarmV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.silencealarmv1.SensorAlarmSet sensorAlarmSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.silencealarmv1.SensorAlarmSet sensorAlarmSet(java.util.Map a)
}
