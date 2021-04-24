package me.biocomp.hubitat_ci.api.device_api.zwave.commands.wakeupv2

trait WakeUpIntervalCapabilitiesReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getDefaultWakeUpIntervalSeconds()
    abstract java.lang.Integer getMaximumWakeUpIntervalSeconds()
    abstract java.lang.Integer getMinimumWakeUpIntervalSeconds()
    abstract java.util.List getPayload()
    abstract java.lang.Integer getWakeUpIntervalStepSeconds()
    abstract void setDefaultWakeUpIntervalSeconds(java.lang.Integer a)
    abstract void setMaximumWakeUpIntervalSeconds(java.lang.Integer a)
    abstract void setMinimumWakeUpIntervalSeconds(java.lang.Integer a)
    abstract void setWakeUpIntervalStepSeconds(java.lang.Integer a)
}
