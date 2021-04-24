package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait WakeUpV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpIntervalGet wakeUpIntervalGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpIntervalGet wakeUpIntervalGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpIntervalReport wakeUpIntervalReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpIntervalReport wakeUpIntervalReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpIntervalSet wakeUpIntervalSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpIntervalSet wakeUpIntervalSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpNoMoreInformation wakeUpNoMoreInformation()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpNoMoreInformation wakeUpNoMoreInformation(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpNotification wakeUpNotification()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv1.WakeUpNotification wakeUpNotification(java.util.Map a)
}
