package me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait VersionReport extends Command{
    abstract java.lang.Short getApplicationSubVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionReport.getApplicationSubVersion()
    abstract java.lang.Short getApplicationVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionReport.getApplicationVersion()
    abstract java.lang.Short getzWaveLibraryType() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionReport.getzWaveLibraryType()
    abstract java.lang.Short getzWaveProtocolSubVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionReport.getzWaveProtocolSubVersion()
    abstract java.lang.Short getzWaveProtocolVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionReport.getzWaveProtocolVersion()
    abstract void setApplicationSubVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionReport.setApplicationSubVersion(java.lang.Short)
    abstract void setApplicationVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionReport.setApplicationVersion(java.lang.Short)
    abstract void setzWaveLibraryType(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionReport.setzWaveLibraryType(java.lang.Short)
    abstract void setzWaveProtocolSubVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionReport.setzWaveProtocolSubVersion(java.lang.Short)
    abstract void setzWaveProtocolVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionReport.setzWaveProtocolVersion(java.lang.Short)
}
