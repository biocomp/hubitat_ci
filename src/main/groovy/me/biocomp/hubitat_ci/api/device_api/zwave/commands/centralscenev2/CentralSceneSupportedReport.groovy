package me.biocomp.hubitat_ci.api.device_api.zwave.commands.centralscenev2

trait CentralSceneSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getIdentical()
    abstract java.util.List getPayload()
    abstract java.util.List getSupportedKeyAttributes()
    abstract java.lang.Short getSupportedScenes()
    abstract void setIdentical(java.lang.Boolean a)
    abstract void setSupportedKeyAttributes(java.util.List a)
    abstract void setSupportedScenes(java.lang.Short a)
}
