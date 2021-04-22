package me.biocomp.hubitat_ci.api.device_api.zwave

trait Command {
    abstract java.lang.String format() // Original: public java.lang.String hubitat.zwave.Command.format()
    abstract java.lang.String getCMD() // Original: public abstract java.lang.String hubitat.zwave.Command.getCMD()
    abstract java.lang.Short getCommandClassId() // Original: public java.lang.Short hubitat.zwave.Command.getCommandClassId()
    abstract java.lang.Short getCommandId() // Original: public java.lang.Short hubitat.zwave.Command.getCommandId()
    abstract java.util.List getPayload() // Original: public java.util.List hubitat.zwave.Command.getPayload()
}

