package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelzonesensorv1

trait SecurityPanelZoneSensorTypeGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSensorNumber()
    abstract java.lang.Short getZoneNumber()
    abstract void setSensorNumber(java.lang.Short a)
    abstract void setZoneNumber(java.lang.Short a)
}
