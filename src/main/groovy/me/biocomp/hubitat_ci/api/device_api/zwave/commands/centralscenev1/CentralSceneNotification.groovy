package me.biocomp.hubitat_ci.api.device_api.zwave.commands.centralscenev1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait CentralSceneNotification extends Command {
    abstract java.lang.Short KEY_HELD_DOWN
    abstract java.lang.Short KEY_PRESSED_1_TIME
    abstract java.lang.Short KEY_RELEASED
    abstract java.lang.Short keyAttributes
    abstract java.lang.Short sceneNumber
    abstract java.lang.Short sequenceNumber
}
