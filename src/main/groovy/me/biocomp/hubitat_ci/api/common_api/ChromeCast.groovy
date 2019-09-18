package me.biocomp.hubitat_ci.api.common_api

// class com.hubitat.hub.controller.interfaces.ChromeCast
trait ChromeCast {
    abstract void connect(String a, int b)
    abstract void disconnect()
    abstract Long getDeviceId()
    abstract void getMediaStatus()
    abstract void getStatus()
    abstract Boolean isConnected()
    abstract su.litvak.chromecast.api.v2.Application launchApp(String name)
    abstract void loadURL(String)
    abstract void mute()
    abstract void pause()
    abstract void play()
    abstract void setDeviceId(Long id)
    abstract void setVolume(float volume)
    abstract void stop()
    abstract void unmute()
}
