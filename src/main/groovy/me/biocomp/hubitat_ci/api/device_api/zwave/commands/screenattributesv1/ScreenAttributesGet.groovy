package me.biocomp.hubitat_ci.api.device_api.zwave.commands.screenattributesv1

trait ScreenAttributesGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
}
