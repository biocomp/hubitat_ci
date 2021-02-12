package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SwitchColorV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1.SwitchColorGet switchColorGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1.SwitchColorReport switchColorReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1.SwitchColorSet switchColorSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1.SwitchColorStartLevelChange switchColorStartLevelChange(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1.SwitchColorStopLevelChange switchColorStopLevelChange(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1.SwitchColorSupportedGet switchColorSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1.SwitchColorSupportedReport switchColorSupportedReport()
}
