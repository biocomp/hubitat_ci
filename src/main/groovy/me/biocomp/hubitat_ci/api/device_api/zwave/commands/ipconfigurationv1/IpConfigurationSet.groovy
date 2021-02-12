package me.biocomp.hubitat_ci.api.device_api.zwave.commands.ipconfigurationv1

trait IpConfigurationSet {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAutoDns()
    abstract java.lang.Boolean getAutoIp()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getDns1()
    abstract java.lang.Integer getDns2()
    abstract java.lang.Integer getGateway()
    abstract java.lang.Integer getIpAddress()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getSubnetMask()
    abstract void setAutoDns(java.lang.Boolean a)
    abstract void setAutoIp(java.lang.Boolean a)
    abstract void setDns1(java.lang.Integer a)
    abstract void setDns2(java.lang.Integer a)
    abstract void setGateway(java.lang.Integer a)
    abstract void setIpAddress(java.lang.Integer a)
    abstract void setSubnetMask(java.lang.Integer a)
}
