package me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait VersionCommandClassGet extends Command{
    abstract java.lang.Short getRequestedCommandClass() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionCommandClassGet.getRequestedCommandClass()
    abstract void setRequestedCommandClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionCommandClassGet.setRequestedCommandClass(java.lang.Short)
}
