package me.biocomp.hubitat_ci.api.device_api.zwave.commands.versionv3

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait VersionReport extends Command{
    abstract java.lang.Short getApplicationSubVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionReport.getApplicationSubVersion()
    abstract java.lang.Short getApplicationVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv1.VersionReport.getApplicationVersion()
    abstract java.lang.Short getFirmware0SubVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv2.VersionReport.getFirmware0SubVersion()
    abstract java.lang.Short getFirmware0Version() // Original: public java.lang.Short hubitat.zwave.commands.versionv2.VersionReport.getFirmware0Version()
    abstract java.lang.Short getFirmwareTargets() // Original: public java.lang.Short hubitat.zwave.commands.versionv2.VersionReport.getFirmwareTargets()
    abstract java.lang.Short getHardwareVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv2.VersionReport.getHardwareVersion()
    abstract java.util.List getTargetVersions() // Original: public java.util.List hubitat.zwave.commands.versionv2.VersionReport.getTargetVersions()
    abstract java.lang.Short getzWaveLibraryType() // Original: public java.lang.Short hubitat.zwave.commands.versionv2.VersionReport.getzWaveLibraryType()
    abstract java.lang.Short getzWaveProtocolSubVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv2.VersionReport.getzWaveProtocolSubVersion()
    abstract java.lang.Short getzWaveProtocolVersion() // Original: public java.lang.Short hubitat.zwave.commands.versionv2.VersionReport.getzWaveProtocolVersion()
    abstract void setApplicationSubVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionReport.setApplicationSubVersion(java.lang.Short)
    abstract void setApplicationVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv1.VersionReport.setApplicationVersion(java.lang.Short)
    abstract void setFirmware0SubVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setFirmware0SubVersion(java.lang.Short)
    abstract void setFirmware0Version(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setFirmware0Version(java.lang.Short)
    abstract void setFirmwareTargets(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setFirmwareTargets(java.lang.Short)
    abstract void setHardwareVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setHardwareVersion(java.lang.Short)
    abstract void setTargetVersions(java.util.List a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setTargetVersions(java.util.List)
    abstract void setzWaveLibraryType(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setzWaveLibraryType(java.lang.Short)
    abstract void setzWaveProtocolSubVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setzWaveProtocolSubVersion(java.lang.Short)
    abstract void setzWaveProtocolVersion(java.lang.Short a) // Original: public void hubitat.zwave.commands.versionv2.VersionReport.setzWaveProtocolVersion(java.lang.Short)
}
