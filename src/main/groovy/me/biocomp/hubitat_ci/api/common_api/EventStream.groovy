package me.biocomp.hubitat_ci.api.common_api

// hubitat.helper.interfaces.EventStream
interface EventStream
{
    abstract void close()
    abstract void connect(String)
    abstract void connect(String, Map)
    abstract void eventStreamClose()
    abstract void eventStreamConnect(String a, String b)
}