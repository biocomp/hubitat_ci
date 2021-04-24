package me.biocomp.hubitat_ci.api.device_api.zwave.commands.protectionv2

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait ProtectionSupportedReport extends Command{
    abstract java.lang.Boolean getExclusiveControl() // Original: public java.lang.Boolean hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.getExclusiveControl()
    abstract java.lang.Integer getLocalProtectionState() // Original: public java.lang.Integer hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.getLocalProtectionState()
    abstract java.lang.Integer getRfProtectionState() // Original: public java.lang.Integer hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.getRfProtectionState()
    abstract java.lang.Boolean getTimeout() // Original: public java.lang.Boolean hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.getTimeout()
    abstract void setExclusiveControl(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.setExclusiveControl(java.lang.Boolean)
    abstract void setLocalProtectionState(java.lang.Integer a) // Original: public void hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.setLocalProtectionState(java.lang.Integer)
    abstract void setRfProtectionState(java.lang.Integer a) // Original: public void hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.setRfProtectionState(java.lang.Integer)
    abstract void setTimeout(java.lang.Boolean a) // Original: public void hubitat.zwave.commands.protectionv2.ProtectionSupportedReport.setTimeout(java.lang.Boolean)
}
