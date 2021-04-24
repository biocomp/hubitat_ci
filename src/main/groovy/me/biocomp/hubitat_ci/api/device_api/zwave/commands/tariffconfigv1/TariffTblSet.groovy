package me.biocomp.hubitat_ci.api.device_api.zwave.commands.tariffconfigv1

trait TariffTblSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRateParameterSetId()
    abstract java.lang.Short getTariffPrecision()
    abstract java.lang.Integer getTariffValue()
    abstract void setRateParameterSetId(java.lang.Short a)
    abstract void setTariffPrecision(java.lang.Short a)
    abstract void setTariffValue(java.lang.Integer a)
}
