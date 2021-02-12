package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait DmxV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxAddressGet dmxAddressGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxAddressGet dmxAddressGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxAddressReport dmxAddressReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxAddressReport dmxAddressReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxAddressSet dmxAddressSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxAddressSet dmxAddressSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxCapabilityGet dmxCapabilityGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxCapabilityGet dmxCapabilityGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxCapabilityReport dmxCapabilityReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxCapabilityReport dmxCapabilityReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxData dmxData()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dmxv1.DmxData dmxData(java.util.Map a)
}
