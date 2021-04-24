package me.biocomp.hubitat_ci.api.device_api.zwave.commands.screenmdv1

trait ScreenMdReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCharPresentation()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getMoreData()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScreenSettings()
    abstract void setCharPresentation(java.lang.Short a)
    abstract void setMoreData(java.lang.Boolean a)
    abstract void setScreenSettings(java.lang.Short a)
}
