package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SensorBinaryV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensorbinaryv1.SensorBinaryGet sensorBinaryGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensorbinaryv1.SensorBinaryGet sensorBinaryGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensorbinaryv1.SensorBinaryReport sensorBinaryReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensorbinaryv1.SensorBinaryReport sensorBinaryReport(java.util.Map a)
}
