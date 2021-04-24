package me.biocomp.hubitat_ci.api.device_api.zwave.commands.batteryv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait BatteryReport extends Command{
    abstract short getBATTERY_LOW_WARNING() // Original: public static short hubitat.zwave.commands.batteryv1.BatteryReport.getBATTERY_LOW_WARNING()
    abstract java.lang.Short  batteryLevel
    abstract java.lang.String getCMD() // Original: public java.lang.String hubitat.zwave.commands.batteryv1.BatteryReport.getCMD()
    abstract java.util.List getPayload() // Original: public java.util.List hubitat.zwave.commands.batteryv1.BatteryReport.getPayload()
}