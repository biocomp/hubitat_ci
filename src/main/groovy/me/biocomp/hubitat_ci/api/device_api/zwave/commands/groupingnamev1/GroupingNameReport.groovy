package me.biocomp.hubitat_ci.api.device_api.zwave.commands.groupingnamev1

trait GroupingNameReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCharPresentation()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getGrouping()
    abstract java.lang.Short getGroupingIdentifier()
    abstract java.util.List getPayload()
    abstract void setCharPresentation(java.lang.Short a)
    abstract void setGrouping(java.util.List a)
    abstract void setGroupingIdentifier(java.lang.Short a)
}
