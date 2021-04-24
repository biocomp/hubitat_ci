package me.biocomp.hubitat_ci.api.device_api.zwave.commands.crc16encapv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait Crc16Encap extends Command{
    abstract java.lang.Integer checksum
    abstract java.lang.Short command
    abstract java.lang.Short commandClass
    abstract java.util.List data
}
