package me.biocomp.hubitat_ci.api.device_api.zwave.commands.metertblmonitorv2

trait MeterTblStatusDepthGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getStatusEventLogDepth()
    abstract void setStatusEventLogDepth(java.lang.Short a)
}
