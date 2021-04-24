package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv4

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiChannelCapabilityGet extends Command{
    abstract java.lang.Short getEndPoint() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityGet.getEndPoint()
    abstract void setEndPoint(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityGet.setEndPoint(java.lang.Short)
}
