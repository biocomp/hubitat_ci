package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait DcpMonitorV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpEventStatusGet dcpEventStatusGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpEventStatusGet dcpEventStatusGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpEventStatusReport dcpEventStatusReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpEventStatusReport dcpEventStatusReport(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpListGet dcpListGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpListGet dcpListGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpListReport dcpListReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.dcpmonitorv1.DcpListReport dcpListReport(java.util.Map a)
}
