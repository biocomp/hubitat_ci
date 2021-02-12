package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multicmdv1

trait MultiCmdEncap {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multicmdv1.MultiCmdEncap encapsulate(java.util.List a)
    abstract java.util.List encapsulatedCommands(java.util.Map a)
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getData()
    abstract java.lang.Short getNumberOfCommands()
    abstract java.util.List getPayload()
    abstract void setData(java.util.List a)
    abstract void setNumberOfCommands(java.lang.Short a)
}
