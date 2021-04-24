package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait IpConfigurationV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationGet ipConfigurationGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationGet ipConfigurationGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationRelease ipConfigurationRelease()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationRelease ipConfigurationRelease(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationRenew ipConfigurationRenew()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationRenew ipConfigurationRenew(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationReport ipConfigurationReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationReport ipConfigurationReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationSet ipConfigurationSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1.IpConfigurationSet ipConfigurationSet(java.util.Map a)
}
