package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensorbinaryv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SensorBinaryReport extends Command {
    abstract java.lang.Short getSENSOR_VALUE_DETECTED_AN_EVENT() // Original: public static java.lang.Short hubitat.zwave.commands.sensorbinaryv1.SensorBinaryReport.getSENSOR_VALUE_DETECTED_AN_EVENT()
    abstract java.lang.Short getSENSOR_VALUE_IDLE() // Original: public static java.lang.Short hubitat.zwave.commands.sensorbinaryv1.SensorBinaryReport.getSENSOR_VALUE_IDLE()
    abstract java.lang.Short getSensorValue() // Original: public java.lang.Short hubitat.zwave.commands.sensorbinaryv1.SensorBinaryReport.getSensorValue()
    abstract void setSENSOR_VALUE_DETECTED_AN_EVENT(java.lang.Short a) // Original: public static void hubitat.zwave.commands.sensorbinaryv1.SensorBinaryReport.setSENSOR_VALUE_DETECTED_AN_EVENT(java.lang.Short)
    abstract void setSENSOR_VALUE_IDLE(java.lang.Short a) // Original: public static void hubitat.zwave.commands.sensorbinaryv1.SensorBinaryReport.setSENSOR_VALUE_IDLE(java.lang.Short)
    abstract void setSensorValue(java.lang.Short a) // Original: public void hubitat.zwave.commands.sensorbinaryv1.SensorBinaryReport.setSensorValue(java.lang.Short)
}