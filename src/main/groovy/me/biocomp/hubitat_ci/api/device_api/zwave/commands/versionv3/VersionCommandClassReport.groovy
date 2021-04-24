package me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv3

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait VersionCommandClassReport extends Command{
    abstract java.lang.Short getCommandClassVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionCommandClassReport.getCommandClassVersion()
    abstract java.lang.Short getRequestedCommandClass() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionCommandClassReport.getRequestedCommandClass()
    abstract void setCommandClassVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionCommandClassReport.setCommandClassVersion(java.lang.Short)
    abstract void setRequestedCommandClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionCommandClassReport.setRequestedCommandClass(java.lang.Short)
}
