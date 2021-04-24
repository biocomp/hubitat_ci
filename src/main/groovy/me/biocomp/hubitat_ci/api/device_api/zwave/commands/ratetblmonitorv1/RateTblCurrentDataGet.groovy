package me.biocomp.hubitat_ci.api.device_api.zwave.commands.ratetblmonitorv1

trait RateTblCurrentDataGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getDatasetRequested()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRateParameterSetId()
    abstract void setDatasetRequested(java.lang.Integer a)
    abstract void setRateParameterSetId(java.lang.Short a)
}
