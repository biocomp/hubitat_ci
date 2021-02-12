package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensoralarmv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SensorAlarmReport extends Command{
    abstract java.lang.Integer getSeconds() // Original: public java.lang.Integer hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.getSeconds()
    abstract java.lang.Short getSensorState() // Original: public java.lang.Short hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.getSensorState()
    abstract java.lang.Short getSensorType() // Original: public java.lang.Short hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.getSensorType()
    abstract java.lang.Short getSourceNodeId() // Original: public java.lang.Short hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.getSourceNodeId()
    abstract void setSeconds(java.lang.Integer a) // Original: public void hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.setSeconds(java.lang.Integer)
    abstract void setSensorState(java.lang.Short a) // Original: public void hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.setSensorState(java.lang.Short)
    abstract void setSensorType(java.lang.Short a) // Original: public void hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.setSensorType(java.lang.Short)
    abstract void setSourceNodeId(java.lang.Short a) // Original: public void hubitat.zwave.commands.sensoralarmv1.SensorAlarmReport.setSourceNodeId(java.lang.Short)
}