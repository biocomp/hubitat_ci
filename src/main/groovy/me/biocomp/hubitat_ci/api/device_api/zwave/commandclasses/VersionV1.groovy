package me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses

import me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionCommandClassGet
import me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionCommandClassReport

trait VersionV1 {
    public abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionCommandClassGet versionCommandClassGet()
    public abstract VersionCommandClassGet versionCommandClassGet(java.util.Map options)
    public abstract VersionCommandClassReport versionCommandClassReport()
    public abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionCommandClassReport versionCommandClassReport(java.util.Map options)
    public abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionGet versionGet()
    public abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionGet versionGet(java.util.Map options)
    public abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionReport versionReport()
    public abstract me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1.VersionReport versionReport(java.util.Map options)
}
