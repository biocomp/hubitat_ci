package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait TimeV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.DateGet dateGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.DateGet dateGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.DateReport dateReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.DateReport dateReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.TimeGet timeGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.TimeGet timeGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.TimeReport timeReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.timev1.TimeReport timeReport(java.util.Map a)
}
