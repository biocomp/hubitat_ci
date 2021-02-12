package me.biocomp.hubitat_ci.api.device_api.zwave.commands.ratetblmonitorv1

trait RateTblSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getParameterSetSupportedBitMask()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRatesSupported()
    abstract void setParameterSetSupportedBitMask(java.lang.Integer a)
    abstract void setRatesSupported(java.lang.Short a)
}
