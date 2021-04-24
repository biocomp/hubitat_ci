package me.biocomp.hubitat_ci.api.device_api.zwave.commands.hrvcontrolv1

trait HrvControlModeSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getMODE_DEMAND_AUTOMATIC()
    abstract java.lang.Short getMODE_ENERGY_SAVINGS_MODE()
    abstract java.lang.Short getMODE_MANUAL()
    abstract java.lang.Short getMODE_OFF()
    abstract java.lang.Short getMODE_SCHEDULE()
    abstract java.lang.Short getMode()
    abstract java.util.List getPayload()
    abstract void setMODE_DEMAND_AUTOMATIC(java.lang.Short a)
    abstract void setMODE_ENERGY_SAVINGS_MODE(java.lang.Short a)
    abstract void setMODE_MANUAL(java.lang.Short a)
    abstract void setMODE_OFF(java.lang.Short a)
    abstract void setMODE_SCHEDULE(java.lang.Short a)
    abstract void setMode(java.lang.Short a)
}
