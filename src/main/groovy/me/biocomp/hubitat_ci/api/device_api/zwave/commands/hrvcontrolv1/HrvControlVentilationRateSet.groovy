package me.biocomp.hubitat_ci.api.device_api.zwave.commands.hrvcontrolv1

trait HrvControlVentilationRateSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getVentilationRate()
    abstract void setVentilationRate(java.lang.Short a)
}
