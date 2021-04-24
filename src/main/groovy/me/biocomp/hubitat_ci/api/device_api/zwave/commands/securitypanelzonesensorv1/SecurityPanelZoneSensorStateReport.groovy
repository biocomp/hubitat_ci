package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securitypanelzonesensorv1

trait SecurityPanelZoneSensorStateReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getEventParameters()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSensorNumber()
    abstract java.lang.Short getZoneNumber()
    abstract java.lang.Short getZwaveAlarmEvent()
    abstract java.lang.Short getZwaveAlarmType()
    abstract void setEventParameters(java.lang.Short a)
    abstract void setSensorNumber(java.lang.Short a)
    abstract void setZoneNumber(java.lang.Short a)
    abstract void setZwaveAlarmEvent(java.lang.Short a)
    abstract void setZwaveAlarmType(java.lang.Short a)
}
