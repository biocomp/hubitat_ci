package me.biocomp.hubitat_ci.api.device_api.zwave.commands.prepaymentv1

trait PrepaymentBalanceGet {
    abstract java.lang.String format()
    abstract java.lang.Short getBALANCE_TYPE_MONETARY()
    abstract java.lang.Short getBALANCE_TYPE_UTILITY()
    abstract java.lang.Short getBalanceType()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setBALANCE_TYPE_MONETARY(java.lang.Short a)
    abstract void setBALANCE_TYPE_UTILITY(java.lang.Short a)
    abstract void setBalanceType(java.lang.Short a)
}
