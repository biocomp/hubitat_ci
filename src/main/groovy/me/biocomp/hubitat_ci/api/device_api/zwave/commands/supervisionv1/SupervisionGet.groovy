package me.biocomp.hubitat_ci.api.device_api.zwave.commands.supervisionv1

trait SupervisionGet {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.supervisionv1.SupervisionGet encapsulate(me.biocomp.hubitat_ci.api.device_api.zwave.Command a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.Command encapsulatedCommand(java.util.Map a)
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.util.List getCommandByte()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandClassIdentifier()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getCommandIdentifier()
    abstract java.lang.Short getCommandLength()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSessionID()
    abstract java.lang.Boolean getStatusUpdates()
    abstract void setCommandByte(java.util.List a)
    abstract void setCommandClassIdentifier(java.lang.Short a)
    abstract void setCommandIdentifier(java.lang.Short a)
    abstract void setCommandLength(java.lang.Short a)
    abstract void setSessionID(java.lang.Short a)
    abstract void setStatusUpdates(java.lang.Boolean a)
}
