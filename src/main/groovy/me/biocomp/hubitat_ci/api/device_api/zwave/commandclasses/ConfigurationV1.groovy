package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait ConfigurationV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1.ConfigurationGet configurationGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1.ConfigurationGet configurationGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1.ConfigurationReport configurationReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1.ConfigurationReport configurationReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1.ConfigurationSet configurationSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1.ConfigurationSet configurationSet(java.util.Map a)
}
