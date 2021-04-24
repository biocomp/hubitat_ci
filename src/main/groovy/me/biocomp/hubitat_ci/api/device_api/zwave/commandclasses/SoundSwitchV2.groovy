package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SoundSwitchV2 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ConfigurationGet configurationGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ConfigurationReport configurationReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ConfigurationReport configurationReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ConfigurationSet configurationSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ConfigurationSet configurationSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ToneInfoGet toneInfoGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ToneInfoGet toneInfoGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ToneInfoReport toneInfoReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.ToneInfoReport toneInfoReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonePlayGet tonePlayGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonePlayReport tonePlayReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonePlayReport tonePlayReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonePlaySet tonePlaySet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonePlaySet tonePlaySet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonesNumberGet tonesNumberGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonesNumberReport tonesNumberReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv2.TonesNumberReport tonesNumberReport(java.util.Map a)
}
