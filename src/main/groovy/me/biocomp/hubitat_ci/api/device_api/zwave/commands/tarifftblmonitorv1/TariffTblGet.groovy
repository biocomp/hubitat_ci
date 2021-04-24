package me.biocomp.hubitat_ci.api.device_api.zwave.commands.tarifftblmonitorv1

trait TariffTblGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRateParameterSetId()
    abstract void setRateParameterSetId(java.lang.Short a)
}
