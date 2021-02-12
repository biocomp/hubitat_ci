package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationgrpinfov2

trait AssociationGroupInfoGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Boolean getListMode()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getRefreshCache()
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setListMode(java.lang.Boolean a)
    abstract void setRefreshCache(java.lang.Boolean a)
}
