package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait ApplicationStatusV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.applicationstatusv1.ApplicationBusy applicationBusy()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.applicationstatusv1.ApplicationBusy applicationBusy(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.applicationstatusv1.ApplicationRejectedRequest applicationRejectedRequest()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.applicationstatusv1.ApplicationRejectedRequest applicationRejectedRequest(java.util.Map a)
}
