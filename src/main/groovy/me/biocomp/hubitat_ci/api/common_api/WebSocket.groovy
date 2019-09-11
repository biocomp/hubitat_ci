package me.biocomp.hubitat_ci.api.common_api

// hubitat.helper.interfaces.WebSocket
interface WebSocket
{
    abstract void close()
    abstract void connect(String a)
    abstract void connect(Map a, String b)
    abstract void sendMessage(String a)
}
