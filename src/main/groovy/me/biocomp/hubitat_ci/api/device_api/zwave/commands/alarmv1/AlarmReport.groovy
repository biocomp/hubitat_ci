package me.biocomp.hubitat_ci.api.device_api.zwave.commands.alarmv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait AlarmReport extends Command {
    Short alarmLevel
    Short alarmType
}
