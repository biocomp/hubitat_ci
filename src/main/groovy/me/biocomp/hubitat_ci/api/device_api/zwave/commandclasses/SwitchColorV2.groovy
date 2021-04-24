package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SwitchColorV2 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2.SwitchColorGet switchColorGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2.SwitchColorReport switchColorReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2.SwitchColorSet switchColorSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2.SwitchColorStartLevelChange switchColorStartLevelChange(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2.SwitchColorStopLevelChange switchColorStopLevelChange(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2.SwitchColorSupportedGet switchColorSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv2.SwitchColorSupportedReport switchColorSupportedReport()
}
