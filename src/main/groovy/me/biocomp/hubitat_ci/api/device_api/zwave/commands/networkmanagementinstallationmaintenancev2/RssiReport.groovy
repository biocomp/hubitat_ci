package me.biocomp.hubitat_ci.api.device_api.zwave.commands.networkmanagementinstallationmaintenancev2

trait RssiReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getChannel1()
    abstract java.lang.Short getChannel2()
    abstract java.lang.Short getChannel3()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRSSI_BELOW_SENSITIVITY()
    abstract java.lang.Short getRSSI_MAX_POWER_SATURATED()
    abstract java.lang.Short getRSSI_NOT_AVAILABLE()
    abstract void setChannel1(java.lang.Short a)
    abstract void setChannel2(java.lang.Short a)
    abstract void setChannel3(java.lang.Short a)
    abstract void setRSSI_BELOW_SENSITIVITY(java.lang.Short a)
    abstract void setRSSI_MAX_POWER_SATURATED(java.lang.Short a)
    abstract void setRSSI_NOT_AVAILABLE(java.lang.Short a)
}
