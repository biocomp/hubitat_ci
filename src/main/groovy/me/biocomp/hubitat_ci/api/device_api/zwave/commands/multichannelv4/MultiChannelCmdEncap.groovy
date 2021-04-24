package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv4

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiChannelCmdEncap extends Command{
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv4.MultiChannelCmdEncap encapsulate(me.biocomp.hubitat_ci.api.device_api.zwave.Command a) // Original: public hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.encapsulate(hubitat.zwave.Command)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.Command encapsulatedCommand(java.util.Map a) // Original: public hubitat.zwave.Command hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.encapsulatedCommand(java.util.Map)
    abstract java.lang.Short getCommandClass() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.getCommandClass()
    abstract java.lang.Boolean getBitAddress() // Original: public java.lang.Boolean hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.getBitAddress()
    abstract java.lang.Short getCommand() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.getCommand()
    abstract java.lang.Short getCommandId() // Original: public java.lang.Short hubitat.zwave.Command.getCommandId()
    abstract java.lang.Short getDestinationEndPoint() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.getDestinationEndPoint()
    abstract java.util.List getParameter() // Original: public java.util.List hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.getParameter()
    abstract java.lang.Boolean getRes01() // Original: public java.lang.Boolean hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.getRes01()
    abstract java.lang.Short getSourceEndPoint() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.getSourceEndPoint()
    abstract void setBitAddress(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.setBitAddress(java.lang.Boolean)
    abstract void setCommand(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.setCommand(java.lang.Short)
    abstract void setCommandClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.setCommandClass(java.lang.Short)
    abstract void setDestinationEndPoint(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.setDestinationEndPoint(java.lang.Short)
    abstract void setParameter(java.util.List a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.setParameter(java.util.List)
    abstract void setRes01(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.setRes01(java.lang.Boolean)
    abstract void setSourceEndPoint(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelCmdEncap.setSourceEndPoint(java.lang.Short)
}