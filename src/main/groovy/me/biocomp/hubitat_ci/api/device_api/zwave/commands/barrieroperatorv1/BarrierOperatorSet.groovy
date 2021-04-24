package me.biocomp.hubitat_ci.api.device_api.zwave.commands.barrieroperatorv1

trait BarrierOperatorSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getREQUESTED_BARRIER_STATE_CLOSE()
    abstract java.lang.Short getREQUESTED_BARRIER_STATE_OPEN()
    abstract java.lang.Short getRequestedBarrierState()
    abstract void setREQUESTED_BARRIER_STATE_CLOSE(java.lang.Short a)
    abstract void setREQUESTED_BARRIER_STATE_OPEN(java.lang.Short a)
    abstract void setRequestedBarrierState(java.lang.Short a)
}
