package me.biocomp.hubitat_ci.api.device_api.zwave

import me.biocomp.hubitat_ci.api.device_api.zwave.commandclasses.VersionV1

trait Zwave {
    abstract VersionV1 getVersionV1()
}

