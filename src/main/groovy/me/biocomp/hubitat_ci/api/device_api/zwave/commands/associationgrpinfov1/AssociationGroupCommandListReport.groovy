package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationgrpinfov1

trait AssociationGroupCommandListReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.util.List getCommand()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Short getListLength()
    abstract java.util.List getPayload()
    abstract void setCommand(java.util.List a)
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setListLength(java.lang.Short a)
}
