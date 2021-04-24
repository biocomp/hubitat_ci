package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait SupervisionV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.supervisionv1.SupervisionGet supervisionGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.supervisionv1.SupervisionGet supervisionGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.supervisionv1.SupervisionReport supervisionReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.supervisionv1.SupervisionReport supervisionReport(java.util.Map a)
}
