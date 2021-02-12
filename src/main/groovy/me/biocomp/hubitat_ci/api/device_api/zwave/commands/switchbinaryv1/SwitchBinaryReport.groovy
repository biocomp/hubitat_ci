package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchbinaryv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SwitchBinaryReport extends Command {
    abstract java.lang.Short getValue() // Original: public java.lang.Short hubitat.zwave.commands.switchbinaryv1.SwitchBinaryReport.getValue()
    abstract void setValue(java.lang.Short a) // Original: public void hubitat.zwave.commands.switchbinaryv1.SwitchBinaryReport.setValue(java.lang.Short)
}
