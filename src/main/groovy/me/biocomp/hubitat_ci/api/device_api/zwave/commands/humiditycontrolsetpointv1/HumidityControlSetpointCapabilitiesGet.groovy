package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontrolsetpointv1

trait HumidityControlSetpointCapabilitiesGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract short getSETPOINT_TYPE_DEHUMIDIFIER()
    abstract short getSETPOINT_TYPE_HUMIDIFIER()
    abstract java.lang.Short getSetpointType()
    abstract void setSetpointType(java.lang.Short a)
}
