package me.biocomp.hubitat_ci.api.device_api.zwave.commands.usercodev1

trait UserCodeReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getUSER_ID_STATUS_AVAILABLE_NOT_SET()
    abstract java.lang.Short getUSER_ID_STATUS_OCCUPIED()
    abstract java.lang.Short getUSER_ID_STATUS_RESERVED_BY_ADMINISTRATOR()
    abstract java.lang.Short getUSER_ID_STATUS_STATUS_NOT_AVAILABLE()
    abstract java.lang.String getUserCode()
    abstract java.lang.Short getUserIdStatus()
    abstract java.lang.Short getUserIdentifier()
    abstract void setUSER_ID_STATUS_AVAILABLE_NOT_SET(java.lang.Short a)
    abstract void setUSER_ID_STATUS_OCCUPIED(java.lang.Short a)
    abstract void setUSER_ID_STATUS_RESERVED_BY_ADMINISTRATOR(java.lang.Short a)
    abstract void setUSER_ID_STATUS_STATUS_NOT_AVAILABLE(java.lang.Short a)
    abstract void setUserCode(java.lang.String a)
    abstract void setUserIdStatus(java.lang.Short a)
    abstract void setUserIdentifier(java.lang.Short a)
}
