package me.biocomp.hubitat_ci.api.device_api.zwave.commands.indicatorv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait IndicatorReport extends Command{
    abstract java.lang.Short value
}
