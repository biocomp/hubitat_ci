package me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1

trait CommandFragmentWait {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPendingFragments()
    abstract void setPendingFragments(java.lang.Short a)
}
