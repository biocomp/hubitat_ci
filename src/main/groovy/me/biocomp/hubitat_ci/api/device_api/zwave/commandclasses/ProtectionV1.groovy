package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait ProtectionV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv1.ProtectionGet protectionGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv1.ProtectionGet protectionGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv1.ProtectionReport protectionReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv1.ProtectionReport protectionReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv1.ProtectionSet protectionSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv1.ProtectionSet protectionSet(java.util.Map a)
}
