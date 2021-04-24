package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationgrpinfov3

trait AssociationGroupNameReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.lang.Short getLengthOfName()
    abstract java.util.List getName()
    abstract java.util.List getPayload()
    abstract void setGroupingIdentifier(java.lang.Short a)
    abstract void setLengthOfName(java.lang.Short a)
    abstract void setName(java.util.List a)
}
