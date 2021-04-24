package me.biocomp.hubitat_ci.api.common_api

import me.biocomp.hubitat_ci.api.domain.Device


trait ChildDeviceWrapper extends DeviceWrapper
{
    /**
     * Not exported, but does exist
     * @return
     */
    abstract Device getDevice()
}
