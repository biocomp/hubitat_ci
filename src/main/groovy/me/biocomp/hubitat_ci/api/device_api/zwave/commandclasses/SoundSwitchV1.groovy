package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SoundSwitchV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ConfigurationGet configurationGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ConfigurationReport configurationReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ConfigurationReport configurationReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ConfigurationSet configurationSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ConfigurationSet configurationSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ToneInfoGet toneInfoGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ToneInfoGet toneInfoGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ToneInfoReport toneInfoReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.ToneInfoReport toneInfoReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonePlayGet tonePlayGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonePlayReport tonePlayReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonePlayReport tonePlayReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonePlaySet tonePlaySet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonePlaySet tonePlaySet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonesNumberGet tonesNumberGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonesNumberReport tonesNumberReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.soundswitchv1.TonesNumberReport tonesNumberReport(java.util.Map a)
}
