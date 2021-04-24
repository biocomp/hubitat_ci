package me.biocomp.hubitat_ci.api.common_api

// hubitat.helper.interfaces.Mqtt
trait Mqtt {
    abstract void connect(String a, String b, String c, String d)
    abstract void connect(Map a, String b, String c, String d, String e)
    abstract void disconnect()
    abstract boolean isConnected()
    abstract Map parseMessage(String a)
    abstract void publish(String a, String b)
    abstract void publish(String a, String b, int c)
    abstract void publish(String a, String b, int c, boolean d)
    abstract void publishMqttMessage(String a, String b, int c, String d, String e, String f, String g)
    abstract void subscribe(String a)
    abstract void subscribe(String a, int b)
    abstract void unsubscribe(String a)
}
