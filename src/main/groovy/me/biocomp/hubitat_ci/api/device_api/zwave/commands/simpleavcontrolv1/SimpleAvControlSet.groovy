package me.biocomp.hubitat_ci.api.device_api.zwave.commands.simpleavcontrolv1

trait SimpleAvControlSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getCommands()
    abstract java.lang.Integer getItemId()
    abstract java.lang.Short getKeyAttributes()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSequenceNumber()
    abstract void setCommands(java.util.List a)
    abstract void setItemId(java.lang.Integer a)
    abstract void setKeyAttributes(java.lang.Short a)
    abstract void setSequenceNumber(java.lang.Short a)
}
