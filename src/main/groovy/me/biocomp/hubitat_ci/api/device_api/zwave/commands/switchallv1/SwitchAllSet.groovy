package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchallv1

trait SwitchAllSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMODE_EXCLUDED_FROM_THE_ALL_OFF_FUNCTIONALITY_BUT_NOT_ALL_ON()
    abstract java.lang.Short getMODE_EXCLUDED_FROM_THE_ALL_ON_ALL_OFF_FUNCTIONALITY()
    abstract java.lang.Short getMODE_EXCLUDED_FROM_THE_ALL_ON_FUNCTIONALITY_BUT_NOT_ALL_OFF()
    abstract java.lang.Short getMODE_INCLUDED_IN_THE_ALL_ON_ALL_OFF_FUNCTIONALITY()
    abstract java.lang.Short getMode()
    abstract java.util.List getPayload()
    abstract void setMODE_EXCLUDED_FROM_THE_ALL_OFF_FUNCTIONALITY_BUT_NOT_ALL_ON(java.lang.Short a)
    abstract void setMODE_EXCLUDED_FROM_THE_ALL_ON_ALL_OFF_FUNCTIONALITY(java.lang.Short a)
    abstract void setMODE_EXCLUDED_FROM_THE_ALL_ON_FUNCTIONALITY_BUT_NOT_ALL_OFF(java.lang.Short a)
    abstract void setMODE_INCLUDED_IN_THE_ALL_ON_ALL_OFF_FUNCTIONALITY(java.lang.Short a)
    abstract void setMode(java.lang.Short a)
}
