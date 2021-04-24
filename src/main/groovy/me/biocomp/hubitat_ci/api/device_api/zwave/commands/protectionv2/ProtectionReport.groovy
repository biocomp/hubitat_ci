package me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv2

trait ProtectionReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getLocalProtectionState()
    abstract java.lang.Short getPROTECTION_STATE_NO_OPERATION_POSSIBLE()
    abstract java.lang.Short getPROTECTION_STATE_PROTECTION_BY_SEQUENCE()
    abstract java.lang.Short getPROTECTION_STATE_UNPROTECTED()
    abstract java.util.List getPayload()
    abstract java.lang.Short getProtectionState()
    abstract java.lang.Short getRfProtectionState()
    abstract void setLocalProtectionState(java.lang.Short a)
    abstract void setPROTECTION_STATE_NO_OPERATION_POSSIBLE(java.lang.Short a)
    abstract void setPROTECTION_STATE_PROTECTION_BY_SEQUENCE(java.lang.Short a)
    abstract void setPROTECTION_STATE_UNPROTECTED(java.lang.Short a)
    abstract void setProtectionState(java.lang.Short a)
    abstract void setRfProtectionState(java.lang.Short a)
}
