package me.biocomp.hubitat_ci.api.device_api.zwave.commands.indicatorv2

trait IndicatorGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getIndicatorId()
    abstract java.util.List getPayload()
    abstract void setIndicatorId(java.lang.Short a)
}
