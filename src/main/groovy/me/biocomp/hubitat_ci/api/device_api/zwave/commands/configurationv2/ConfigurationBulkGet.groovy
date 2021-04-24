package me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv2

trait ConfigurationBulkGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNumberOfParameters()
    abstract java.lang.Integer getParameterOffset()
    abstract java.util.List getPayload()
    abstract void setNumberOfParameters(java.lang.Short a)
    abstract void setParameterOffset(java.lang.Integer a)
}
