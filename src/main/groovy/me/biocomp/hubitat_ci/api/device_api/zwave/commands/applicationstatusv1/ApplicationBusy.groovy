package me.biocomp.hubitat_ci.api.device_api.zwave.commands.applicationstatusv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait ApplicationBusy extends Command {
    abstract java.lang.Short STATUS_REQUEST_QUEUED_EXECUTED_LATER
    abstract java.lang.Short STATUS_TRY_AGAIN_IN_WAIT_TIME_SECONDS
    abstract java.lang.Short STATUS_TRY_AGAIN_LATER
    abstract java.lang.Short status
    abstract java.lang.Short waitTime
}
