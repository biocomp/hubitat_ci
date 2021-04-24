package me.biocomp.hubitat_ci.api.device_api.zwave.commands.nodenamingv1

trait NodeNamingNodeNameReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCharPresentation()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getNodeName()
    abstract java.util.List getPayload()
    abstract void setCharPresentation(java.lang.Short a)
    abstract void setNodeName(java.util.List a)
}
