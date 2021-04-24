package me.biocomp.hubitat_ci.api.device_api.zwave.commands.applicationcapabilityv1

trait CommandCommandClassNotSupported {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getDynamic()
    abstract java.lang.Short getOffendingCommand()
    abstract java.lang.Short getOffendingCommandClass()
    abstract java.util.List getPayload()
    abstract void setDynamic(java.lang.Boolean a)
    abstract void setOffendingCommand(java.lang.Short a)
    abstract void setOffendingCommandClass(java.lang.Short a)
}
