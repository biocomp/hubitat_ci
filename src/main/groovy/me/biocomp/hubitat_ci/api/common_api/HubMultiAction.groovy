package me.biocomp.hubitat_ci.api.common_api

import me.biocomp.hubitat_ci.api.Protocol

/**
 * Real methods:
 *
 * Constructors:
 * public hubitat.device.HubMultiAction(java.util.List,hubitat.device.Protocol,java.lang.String),
 * public hubitat.device.HubMultiAction(java.util.List,hubitat.device.Protocol),
 * public hubitat.device.HubMultiAction(java.util.List),
 * public hubitat.device.HubMultiAction()
 *
 * Rest:
 * Meta:
 * public java.lang.Object hubitat.device.HubMultiAction.invokeMethod(java.lang.String,java.lang.Object),
 * public groovy.lang.MetaClass hubitat.device.HubMultiAction.getMetaClass(),
 * public java.lang.Object hubitat.device.HubMultiAction.getProperty(java.lang.String),
 * public void hubitat.device.HubMultiAction.setProperty(java.lang.String,java.lang.Object)
 * public void hubitat.device.HubMultiAction.setMetaClass(groovy.lang.MetaClass),
 *
 * Getters (most relevant for mock)
 * public java.util.List hubitat.device.HubMultiAction.getActionList(),
 * public void hubitat.device.HubMultiAction.setActionList(java.util.List),
 *
 * Other (Not implemented, don't appear to be used in normal app code)
 * public void hubitat.device.HubMultiAction.add(hubitat.device.HubAction),
 * public void hubitat.device.HubMultiAction.add(hubitat.device.HubMultiAction),
 * public void hubitat.device.HubMultiAction.add(java.lang.String),
 * public void hubitat.device.HubMultiAction.add(java.util.List),
 */
class HubMultiAction
{
    /*
    List<String> cmds - List of commands to perform
    Protocol protocol - Specific protocol to be used. Default value is Protocol.LAN.
    String dni - Device Network ID of the device. Default value is null. For dni, we recommend using MAC address and not use IP and port numbers.
    */

    HubMultiAction(List cmds = null, Protocol protocol = null, String deviceNetworkId = null) {
        this.cmds = cmds
        this.protocol = protocol
        this.deviceNetworkId = deviceNetworkId
    }
    
    List<String> cmds
    Protocol protocol
    String deviceNetworkId
}