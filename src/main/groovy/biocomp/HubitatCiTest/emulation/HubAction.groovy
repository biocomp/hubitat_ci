package biocomp.hubitatCiTest.emulation

/**
 * Real methods:
 *
 * Constructors:
 * public hubitat.device.HubAction(java.lang.String,hubitat.device.Protocol),
 * public hubitat.device.HubAction(java.util.Map,java.lang.String),
 * public hubitat.device.HubAction(java.util.Map),
 * public hubitat.device.HubAction(),
 * public hubitat.device.HubAction(java.lang.String,hubitat.device.Protocol,java.lang.String,java.util.Map),
 * public hubitat.device.HubAction(java.lang.String,hubitat.device.Protocol,java.util.Map),
 * public hubitat.device.HubAction(java.util.Map,java.lang.String,java.util.Map),
 * public hubitat.device.HubAction(java.lang.String),
 * public hubitat.device.HubAction(java.lang.String,hubitat.device.Protocol,java.lang.String)
 *
 * Rest:
 public java.lang.String hubitat.device.HubAction.getAction(),
 public java.lang.String hubitat.device.HubAction.getCallbackMethod(),
 public java.lang.String hubitat.device.HubAction.getDeviceNetworkId(),
 public groovy.lang.MetaClass hubitat.device.HubAction.getMetaClass(),
 public java.util.Map hubitat.device.HubAction.getOptions(),
 public java.lang.Object hubitat.device.HubAction.getProperty(java.lang.String),
 public java.net.URI hubitat.device.HubAction.getURI(),
 public java.lang.Object hubitat.device.HubAction.invokeMethod(java.lang.String,java.lang.Object),
 public void hubitat.device.HubAction.setDeviceNetworkId(java.lang.String),
 public void hubitat.device.HubAction.setMetaClass(groovy.lang.MetaClass),
 public void hubitat.device.HubAction.setOptions(java.util.Map),
 public void hubitat.device.HubAction.setProperty(java.lang.String,java.lang.Object),
 public java.lang.String hubitat.device.HubAction.toString()
 */
class HubAction
{
    /*
    String action - A string comprised of the request details targeted for the device.
    Protocol protocol - Specific protocol to be used. Default value is Protocol.LAN.
    String dni - Device Network ID of the device. Default value is null. For dni, we recommend using MAC address and not use IP and port numbers.
    Map options - Default value is null. Available options are:
        callback 	Name of the callback method
        type 	    Type of LAN request. Allowed values are: LAN_TYPE_CLIENT, LAN_TYPE_SERVER. Default value is LAN_TYPE_CLIENT.
        protocol 	Allowed values are LAN_PROTOCOL_TCP and LAN_PROTOCOL_UDP and default value is LAN_PROTOCOL_TCP. Note the difference in allowed values of this parameter when used in Maps params and Protocol protocol signatures.

    Map params - Available parameters are:
        Parameter 	Description
        path 	Allowed values are any string of the form "/somepath". Default value is "/".
        method 	Allowed values are "POST", "GET", "PUT" and "PATCH". Default value is "POST".
        protocol 	Allowed values are Protocol.LAN. Default value is also Protocol.LAN.
        headers 	A map of HTTP headers. The HOST should be the "IP":"port" string of the device.
            Default values are ['Accept': '*//*', 'User-Agent': 'Linux UPnP/1.0 SmartThings',]. If 'Content-Type' is not included, then it is set to 'application/json' if params:body is a Map; otherwise 'Content-Type' is set to 'text/xml; charset="utf-8"'.
        query 	A map of URL query parameters.
        body 	Request body.
     */

    HubAction(String action, Protocol protocol)
    {
        this.action = action
        this.protocol = protocol
    }

    HubAction(String action, Protocol protocol, String dni, Map options = null)
    {
        this.action = action
        this.protocol = protocol
        this.dni = dni
        this.options = options
    }

    HubAction(Map params, String dni = null, Map options = null)
    {
        this.params = params
        this.dni = dni
        this.options = options
    }

    String action
    Protocol protocol
    String dni
    Map options
    Map params
}