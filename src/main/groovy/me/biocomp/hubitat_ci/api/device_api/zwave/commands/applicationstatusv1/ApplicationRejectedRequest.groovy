package me.biocomp.hubitat_ci.api.device_api.zwave.commands.applicationstatusv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait ApplicationRejectedRequest extends Command {
    Short status
}
