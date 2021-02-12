package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SwitchColorV3 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3.SwitchColorGet switchColorGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3.SwitchColorReport switchColorReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3.SwitchColorSet switchColorSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3.SwitchColorStartLevelChange switchColorStartLevelChange(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3.SwitchColorStopLevelChange switchColorStopLevelChange(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3.SwitchColorSupportedGet switchColorSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3.SwitchColorSupportedReport switchColorSupportedReport()
}
