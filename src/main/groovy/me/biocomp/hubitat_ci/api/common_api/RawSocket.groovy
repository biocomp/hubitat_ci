package me.biocomp.hubitat_ci.api.common_api

// hubitat.helper.interfaces.RawSocket
interface RawSocket
{
    abstract void close()
    abstract void connect(String a,int b)
    abstract void connect(Map a, String b, int c)
    abstract void sendMessage(String a)
    abstract void disconnect() // Original: public void hubitat.helper.interfaces.RawSocket.disconnect()
    abstract boolean isConnected() // Original: public boolean hubitat.helper.interfaces.RawSocket.isConnected()
}
