package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchbinaryv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SwitchBinarySet extends Command{
    abstract java.lang.Short getSwitchValue() // Original: public java.lang.Short hubitat.zwave.commands.switchbinaryv1.SwitchBinarySet.getSwitchValue()
    abstract void setSwitchValue(java.lang.Short a) // Original: public void hubitat.zwave.commands.switchbinaryv1.SwitchBinarySet.setSwitchValue(java.lang.Short)
}
