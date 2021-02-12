package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securityv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SecurityCommandsSupportedReport extends Command{
    abstract java.util.List getCommandClassControl() // Original: public java.util.List hubitat.zwave.commands.securityv1.SecurityCommandsSupportedReport.getCommandClassControl()
    abstract java.util.List getCommandClassSupport() // Original: public java.util.List hubitat.zwave.commands.securityv1.SecurityCommandsSupportedReport.getCommandClassSupport()
    abstract java.lang.Short getReportsToFollow() // Original: public java.lang.Short hubitat.zwave.commands.securityv1.SecurityCommandsSupportedReport.getReportsToFollow()
    abstract void setCommandClassControl(java.util.List a) // Original: public void hubitat.zwave.commands.securityv1.SecurityCommandsSupportedReport.setCommandClassControl(java.util.List)
    abstract void setCommandClassSupport(java.util.List a) // Original: public void hubitat.zwave.commands.securityv1.SecurityCommandsSupportedReport.setCommandClassSupport(java.util.List)
    abstract void setReportsToFollow(java.lang.Short a) // Original: public void hubitat.zwave.commands.securityv1.SecurityCommandsSupportedReport.setReportsToFollow(java.lang.Short)
}
