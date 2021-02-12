package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

trait TransportServiceV1 {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFirstFragment commandFirstFragment()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFirstFragment commandFirstFragment(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFragmentComplete commandFragmentComplete()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFragmentComplete commandFragmentComplete(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFragmentRequest commandFragmentRequest()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFragmentRequest commandFragmentRequest(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFragmentWait commandFragmentWait()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandFragmentWait commandFragmentWait(java.util.Map a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandSubsequentFragment commandSubsequentFragment()
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1.CommandSubsequentFragment commandSubsequentFragment(java.util.Map a)
}
