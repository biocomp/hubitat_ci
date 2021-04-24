package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait MultiInstanceV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1.MultiInstanceCmdEncap multiInstanceCmdEncap()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1.MultiInstanceCmdEncap multiInstanceCmdEncap(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1.MultiInstanceGet multiInstanceGet()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1.MultiInstanceGet multiInstanceGet(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1.MultiInstanceReport multiInstanceReport()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1.MultiInstanceReport multiInstanceReport(java.util.Map a)
}
