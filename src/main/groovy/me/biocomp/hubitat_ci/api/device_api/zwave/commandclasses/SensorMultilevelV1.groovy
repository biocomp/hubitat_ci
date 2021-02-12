package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SensorMultilevelV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensormultilevelv1.SensorMultilevelGet sensorMultilevelGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensormultilevelv1.SensorMultilevelGet sensorMultilevelGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensormultilevelv1.SensorMultilevelReport sensorMultilevelReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensormultilevelv1.SensorMultilevelReport sensorMultilevelReport(java.util.Map a)
}
