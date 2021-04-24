package me.biocomp.hubitat_ci.api.device_api.zwave.commands.basicv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait BasicReport extends Command {
    abstract Short value
}
