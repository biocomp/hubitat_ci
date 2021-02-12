package me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv2

trait ConfigurationBulkSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getDefaultValue()
    abstract java.lang.Boolean getHandshake()
    abstract java.lang.Short getNumberOfParameters()
    abstract java.lang.Integer getParameterOffset()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSize()
    abstract void setDefaultValue(java.lang.Boolean a)
    abstract void setHandshake(java.lang.Boolean a)
    abstract void setNumberOfParameters(java.lang.Short a)
    abstract void setParameterOffset(java.lang.Integer a)
    abstract void setSize(java.lang.Short a)
}
