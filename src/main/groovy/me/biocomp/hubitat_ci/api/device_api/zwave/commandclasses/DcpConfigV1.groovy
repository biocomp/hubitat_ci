package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait DcpConfigV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListRemove dcpListRemove()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListRemove dcpListRemove(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListSet dcpListSet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListSet dcpListSet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListSupportedGet dcpListSupportedGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListSupportedGet dcpListSupportedGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListSupportedReport dcpListSupportedReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpconfigv1.DcpListSupportedReport dcpListSupportedReport(java.util.Map a)
}
