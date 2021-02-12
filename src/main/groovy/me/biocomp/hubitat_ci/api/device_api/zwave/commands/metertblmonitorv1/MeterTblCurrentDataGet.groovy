package me.biocomp.hubitat_ci.api.device_api.zwave.commands.metertblmonitorv1

trait MeterTblCurrentDataGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getDatasetRequested()
    abstract java.util.List getPayload()
    abstract void setDatasetRequested(java.lang.Integer a)
}
