package me.biocomp.hubitat_ci.api.device_api.zwave.commands.geographiclocationv1

trait GeographicLocationSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getLatSign()
    abstract java.lang.Short getLatitudeDegrees()
    abstract java.lang.Short getLatitudeMinutes()
    abstract java.lang.Boolean getLongSign()
    abstract java.lang.Short getLongitudeDegrees()
    abstract java.lang.Short getLongitudeMinutes()
    abstract java.util.List getPayload()
    abstract void setLatSign(java.lang.Boolean a)
    abstract void setLatitudeDegrees(java.lang.Short a)
    abstract void setLatitudeMinutes(java.lang.Short a)
    abstract void setLongSign(java.lang.Boolean a)
    abstract void setLongitudeDegrees(java.lang.Short a)
    abstract void setLongitudeMinutes(java.lang.Short a)
}
