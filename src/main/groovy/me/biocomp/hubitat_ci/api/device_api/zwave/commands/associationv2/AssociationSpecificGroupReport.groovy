package me.biocomp.hubitat_ci.api.device_api.zwave.commands.associationv2

trait AssociationSpecificGroupReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getGroup()
    abstract java.util.List getPayload()
    abstract void setGroup(java.lang.Short a)
}
