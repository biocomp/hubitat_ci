package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv3

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiChannelCapabilityReport extends Command{
    abstract java.util.List getCommandClass() // Original: public java.util.List hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.getCommandClass()
    abstract java.lang.Short getCommandId() // Original: public java.lang.Short hubitat.zwave.Command.getCommandId()
    abstract java.lang.Boolean getDynamic() // Original: public java.lang.Boolean hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.getDynamic()
    abstract java.lang.Short getEndPoint() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.getEndPoint()
    abstract java.lang.Short getGenericDeviceClass() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.getGenericDeviceClass()
    abstract java.lang.Short getSpecificDeviceClass() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.getSpecificDeviceClass()
    abstract void setCommandClass(java.util.List a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.setCommandClass(java.util.List)
    abstract void setDynamic(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.setDynamic(java.lang.Boolean)
    abstract void setEndPoint(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.setEndPoint(java.lang.Short)
    abstract void setGenericDeviceClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.setGenericDeviceClass(java.lang.Short)
    abstract void setSpecificDeviceClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelCapabilityReport.setSpecificDeviceClass(java.lang.Short)
}
