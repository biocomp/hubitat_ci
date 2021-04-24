package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv3

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiChannelCmdEncap extends Command{
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv3.MultiChannelCmdEncap encapsulate(me.biocomp.hubitat_ci.api.device_api.zwave.Command a) // Original: public hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.encapsulate(hubitat.zwave.Command)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.Command encapsulatedCommand(java.util.Map a) // Original: public hubitat.zwave.Command hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.encapsulatedCommand(java.util.Map)
    abstract java.lang.Boolean getBitAddress() // Original: public java.lang.Boolean hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.getBitAddress()
    abstract java.lang.Short getCommand() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.getCommand()
    abstract java.lang.Short getCommandId() // Original: public java.lang.Short hubitat.zwave.Command.getCommandId()
    abstract java.lang.Short getDestinationEndPoint() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.getDestinationEndPoint()
    abstract java.util.List getParameter() // Original: public java.util.List hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.getParameter()
    abstract java.lang.Boolean getRes01() // Original: public java.lang.Boolean hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.getRes01()
    abstract java.lang.Short getSourceEndPoint() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.getSourceEndPoint()
    abstract void setBitAddress(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.setBitAddress(java.lang.Boolean)
    abstract void setCommand(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.setCommand(java.lang.Short)
    abstract void setCommandClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.setCommandClass(java.lang.Short)
    abstract void setDestinationEndPoint(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.setDestinationEndPoint(java.lang.Short)
    abstract void setParameter(java.util.List a) // Original: public void hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.setParameter(java.util.List)
    abstract void setRes01(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.setRes01(java.lang.Boolean)
    abstract void setSourceEndPoint(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.setSourceEndPoint(java.lang.Short)
    abstract java.lang.Short getCommandClass() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv3.MultiChannelCmdEncap.getCommandClass()
}
