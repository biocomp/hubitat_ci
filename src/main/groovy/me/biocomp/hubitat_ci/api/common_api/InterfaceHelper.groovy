package me.biocomp.hubitat_ci.api.common_api

// hubitat.helper.InterfaceHelper
trait InterfaceHelper {
    abstract EventStream getEventStream()
    abstract Mqtt getMqtt()
    abstract RawSocket getRawSocket()
    abstract WebSocket getWebSocket()
    boolean isSystemTypeOrHubDeveloper(Long a) { false }
}