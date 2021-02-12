package me.biocomp.hubitat_ci.api.device_api.zwave.commands.ratetblconfigv1

trait RateTblRemove {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.util.List getRateParameterSetId()
    abstract java.lang.Short getRateParameterSetIds()
    abstract void setRateParameterSetId(java.util.List a)
    abstract void setRateParameterSetIds(java.lang.Short a)
}
