package me.biocomp.hubitat_ci.api.device_api.zwave.commands.barrieroperatorv1

trait BarrierOperatorReport {
    abstract java.lang.String format()
    abstract java.lang.Short getBARRIER_STATE_CLOSED()
    abstract java.lang.Short getBARRIER_STATE_OPEN()
    abstract java.lang.Short getBARRIER_STATE_UNKNOWN_POSITION_MOVING_TO_CLOSE()
    abstract java.lang.Short getBARRIER_STATE_UNKNOWN_POSITION_MOVING_TO_OPEN()
    abstract java.lang.Short getBARRIER_STATE_UNKNOWN_POSITION_STOPPED()
    abstract java.lang.Short getBarrierState()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract void setBARRIER_STATE_CLOSED(java.lang.Short a)
    abstract void setBARRIER_STATE_OPEN(java.lang.Short a)
    abstract void setBARRIER_STATE_UNKNOWN_POSITION_MOVING_TO_CLOSE(java.lang.Short a)
    abstract void setBARRIER_STATE_UNKNOWN_POSITION_MOVING_TO_OPEN(java.lang.Short a)
    abstract void setBARRIER_STATE_UNKNOWN_POSITION_STOPPED(java.lang.Short a)
    abstract void setBarrierState(java.lang.Short a)
}
