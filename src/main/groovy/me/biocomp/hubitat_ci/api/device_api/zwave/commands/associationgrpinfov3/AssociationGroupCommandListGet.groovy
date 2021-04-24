package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationgrpinfov3

trait AssociationGroupCommandListGet {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAllowCache()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.util.List getPayload()
    abstract void setAllowCache(java.lang.Boolean a)
    abstract void setGroupingIdentifier(java.lang.Short a)
}
