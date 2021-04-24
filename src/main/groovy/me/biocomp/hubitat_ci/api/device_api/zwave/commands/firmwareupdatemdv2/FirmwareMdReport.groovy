package me.biocomp.hubitat_ci.api.device_api.zwave.commands.firmwareupdatemdv2

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait FirmwareMdReport extends Command{
    abstract java.lang.Integer checksum
    abstract java.lang.Integer firmwareId
    abstract java.lang.Integer manufacturerId
}
