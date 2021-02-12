package me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1

trait ConfigurationSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getConfigurationValue()
    abstract java.lang.Boolean getDefaultValue()
    abstract java.lang.Short getParameterNumber()
    abstract java.util.List getPayload()
    abstract java.lang.Short getReserved11()
    abstract java.math.BigInteger getScaledConfigurationValue()
    abstract java.lang.Short getSize()
    abstract void setConfigurationValue(java.util.List a)
    abstract void setDefaultValue(java.lang.Boolean a)
    abstract void setParameterNumber(java.lang.Short a)
    abstract void setReserved11(java.lang.Short a)
    abstract void setScaledConfigurationValue(java.math.BigInteger a)
    abstract void setSize(java.lang.Short a)
}
