package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1

trait MultiInstanceCmdEncap {
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1.MultiInstanceCmdEncap encapsulate(me.biocomp.hubitat_ci.api.device_api.zwave.Command a)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.Command encapsulatedCommand(java.util.Map a)
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommand()
    abstract java.lang.Short getCommandClass()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getInstance()
    abstract java.util.List getParameter()
    abstract java.util.List getPayload()
    abstract void setCommand(java.lang.Short a)
    abstract void setCommandClass(java.lang.Short a)
    abstract void setInstance(java.lang.Short a)
    abstract void setParameter(java.util.List a)
}
