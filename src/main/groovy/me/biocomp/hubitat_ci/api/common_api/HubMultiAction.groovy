package me.biocomp.hubitat_ci.api.common_api

import me.biocomp.hubitat_ci.api.Protocol

class HubMultiAction {
    /**
        List<String> cmds - List of commands to perform
        Protocol protocol - Specific protocol to be used. Default value is Protocol.LAN.
        String deviceNetworkId - Device Network ID of the device. Default value is null. For dni, we recommend using MAC address and not use IP and port numbers.
    */
    HubMultiAction(List cmds = null, Protocol protocol = Protocol.LAN, String deviceNetworkId = null) {
        actionList.addAll(cmds.collect{new HubAction(it, protocol, deviceNetworkId)}) // This implementation is just a guess
    }

    void add(me.biocomp.hubitat_ci.api.common_api.HubAction a) // Original: public void hubitat.device.HubMultiAction.add(hubitat.device.HubAction)
    {
        actionList << a // This implementation is just a guess
    }

    void add(me.biocomp.hubitat_ci.api.common_api.HubMultiAction a) // Original: public void hubitat.device.HubMultiAction.add(hubitat.device.HubMultiAction)
    {
        actionList.addAll(a.actionList) // This implementation is just a guess
    }

    void add(java.lang.String a) // Original: public void hubitat.device.HubMultiAction.add(java.lang.String)
    {
        actionList << new HubAction(a) // This implementation is just a guess
    }

    void add(java.util.List a) // Original: public void hubitat.device.HubMultiAction.add(java.util.List)
    {
        actionList.addAll(a) // This implementation is just a guess
    }

    java.util.List actionList = [] as List
}