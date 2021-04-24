package me.biocomp.hubitat_ci.api.common_api

import me.biocomp.hubitat_ci.api.Protocol

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
 * Meta:
 * public java.lang.Object hubitat.device.HubAction.invokeMethod(java.lang.String,java.lang.Object),
 * public groovy.lang.MetaClass hubitat.device.HubAction.getMetaClass(),
 * public java.lang.Object hubitat.device.HubAction.getProperty(java.lang.String),
 * public void hubitat.device.HubAction.setProperty(java.lang.String,java.lang.Object),
 * public java.lang.String hubitat.device.HubAction.toString()
 * public void hubitat.device.HubAction.setMetaClass(groovy.lang.MetaClass),
 *
 * Getters (most relevant for mock)
 * public java.lang.String hubitat.device.HubAction.getAction(),
 * public java.lang.String hubitat.device.HubAction.getCallbackMethod(),
 * public java.lang.String hubitat.device.HubAction.getDeviceNetworkId(),
 * public java.util.Map hubitat.device.HubAction.getOptions(),
 * public java.net.URI hubitat.device.HubAction.getURI(),
 *
 * Setters:
 * public void hubitat.device.HubAction.setDeviceNetworkId(java.lang.String),
 * public void hubitat.device.HubAction.setOptions(java.util.Map),
 */
class HubAction
{
    /*
    String action - A string comprised of the request details targeted for the device.
    Protocol protocol - Specific protocol to be used. Default value is Protocol.LAN.
    String dni - Device Network ID of the device. Default value is null. For dni, we recommend using MAC address and not use IP and port numbers.
    Map options - Optional settings when sending the command. See examples for usage. Possible values:
        callback - A method name to pass the response from the HubAction back to. If not specified the response will be handed to the parse method of a Device or will be discarded if this HubAction was called from an App.
        destinationAddress - The destination address to use when sending LAN messages. In the format ip:port, if no port is specified it defaults to 80.
        destinationPort - The port number to use when sending UPNP discovery messages. Defaults to 1900.
        type - The type of message to send if its not a standard HTTP request.

            HubAction.LAN_TYPE_UDPCLIENT - Send message as a UDP package.
            HubAction.LAN_TYPE_RAW - Send message as a raw TCP message.

        secureCode - Used as part of a wake on lan request.
        encoding
        ignoreResponse - (true/false) Used as part of UDP messages, instructs the system to ignore any response from the device to this message.
        parseWarning - (true/false) Used as part of UDP messages, instructs the system to send any error message back to the parse method or callback method of the device. (Since 2.2.0)
        timeout - Used as part of UPD or TCP messages. Sets the timeout for response from the device, defaults to 10 seconds, acceptable range is 1 to 300.

    Map params - a list of parameters for sending a Lan message. Possible values:
        method - The http method to use, ie GET, POST, etc.
        path - The path to access on the http endpoint.
        query - Any query parameters to use when calling the path.
        body - The request body to send.
        headers - Additional headers to use in the http request.
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
        this.deviceNetworkId = dni
        this.options = options
    }

    HubAction(Map params, String dni = null, Map options = null)
    {
        this.params = params
        this.deviceNetworkId = dni
        this.options = options
    }

    HubAction() {} // Original: public hubitat.device.HubAction()
    HubAction(java.lang.String action) { this.action = action} // Original: public hubitat.device.HubAction(java.lang.String)
    HubAction(java.lang.String action, me.biocomp.hubitat_ci.api.Protocol protocol, java.util.Map options) {
        this.action = action
        this.protocol = protocol
        this.options = options
    } // Original: public hubitat.device.HubAction(java.lang.String,hubitat.device.Protocol,java.util.Map)

    java.lang.String getCallbackMethod() // Original: public java.lang.String hubitat.device.HubAction.getCallbackMethod()
    {
        return options?.callback
    }

    java.net.URI getURI() // Original: public java.net.URI hubitat.device.HubAction.getURI()
    {
        return new URI("https://hubitat.com/") // Not really sure how URI is constructed from parameters
    }

    final String action
    Protocol protocol
    String deviceNetworkId
    Map options
    private Map params
}