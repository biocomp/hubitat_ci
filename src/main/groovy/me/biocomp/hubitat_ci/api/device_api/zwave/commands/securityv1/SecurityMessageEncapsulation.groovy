package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securityv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SecurityMessageEncapsulation extends Command{
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.securityv1.SecurityMessageEncapsulation encapsulate(me.biocomp.hubitat_ci.api.device_api.zwave.Command a) // Original: public hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.encapsulate(hubitat.zwave.Command)
    abstract me.biocomp.hubitat_ci.api.device_api.zwave.Command encapsulatedCommand(java.util.Map a) // Original: public hubitat.zwave.Command hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.encapsulatedCommand(java.util.Map)
    abstract java.util.List getCommandByte() // Original: public java.util.List hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.getCommandByte()
    abstract java.lang.Short getCommandClassIdentifier() // Original: public java.lang.Short hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.getCommandClassIdentifier()
    abstract java.lang.Short getCommandIdentifier() // Original: public java.lang.Short hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.getCommandIdentifier()
    abstract java.lang.Boolean getSecondFrame() // Original: public java.lang.Boolean hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.getSecondFrame()
    abstract java.lang.Short getSequenceCounter() // Original: public java.lang.Short hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.getSequenceCounter()
    abstract java.lang.Boolean getSequenced() // Original: public java.lang.Boolean hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.getSequenced()
    abstract void setCommandByte(java.util.List a) // Original: public void hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.setCommandByte(java.util.List)
    abstract void setCommandClassIdentifier(java.lang.Short a) // Original: public void hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.setCommandClassIdentifier(java.lang.Short)
    abstract void setCommandIdentifier(java.lang.Short a) // Original: public void hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.setCommandIdentifier(java.lang.Short)
    abstract void setSecondFrame(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.setSecondFrame(java.lang.Boolean)
    abstract void setSequenceCounter(java.lang.Short a) // Original: public void hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.setSequenceCounter(java.lang.Short)
    abstract void setSequenced(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.securityv1.SecurityMessageEncapsulation.setSequenced(java.lang.Boolean)
}
