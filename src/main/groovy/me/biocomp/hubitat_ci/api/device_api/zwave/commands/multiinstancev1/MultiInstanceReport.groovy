package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multiinstancev1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiInstanceReport extends Command{
    abstract java.lang.Short getCommandId() // Original: public java.lang.Short hubitat.zwave.Command.getCommandId()
    abstract java.lang.Short getInstances() // Original: public java.lang.Short hubitat.zwave.commands.multiinstancev1.MultiInstanceReport.getInstances()
    abstract void setCommandClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.multiinstancev1.MultiInstanceReport.setCommandClass(java.lang.Short)
    abstract void setInstances(java.lang.Short a) // Original: public void hubitat.zwave.commands.multiinstancev1.MultiInstanceReport.setInstances(java.lang.Short)
    abstract java.lang.Short getCommandClass() // Original: public java.lang.Short hubitat.zwave.commands.multiinstancev1.MultiInstanceReport.getCommandClass()
}
