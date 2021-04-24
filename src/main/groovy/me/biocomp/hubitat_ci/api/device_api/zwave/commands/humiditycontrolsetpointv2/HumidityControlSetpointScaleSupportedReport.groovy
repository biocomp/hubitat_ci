package me.biocomp.hubitat_ci.api.device_api.zwave.commands.humiditycontrolsetpointv2

trait HumidityControlSetpointScaleSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAbsolute()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getPercentage()
    abstract short getSETPOINT_TYPE_AUTO()
    abstract java.lang.Short getScaleBitmask()
    abstract void setAbsolute(java.lang.Boolean a)
    abstract void setPercentage(java.lang.Boolean a)
    abstract java.lang.Short setScaleBitmask(java.lang.Short a)
}
