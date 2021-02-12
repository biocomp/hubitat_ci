package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationgrpinfov2

trait AssociationGroupInfoReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getDynamicInfo()
    abstract java.lang.Short getGroupCount()
    abstract java.util.List getGroupInfo()
    abstract java.lang.Boolean getListMode()
    abstract java.util.List getPayload()
    abstract void setDynamicInfo(java.lang.Boolean a)
    abstract void setGroupCount(java.lang.Short a)
    abstract void setGroupInfo(java.util.List a)
    abstract void setListMode(java.lang.Boolean a)
}
