package me.biocomp.hubitat_ci.api.device_api.zwave.commands.transportservicev1

trait CommandFirstFragment {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Integer getChecksum()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDatagramSize1()
    abstract java.lang.Short getDatagramSize2()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSequenceNo()
    abstract void setChecksum(java.lang.Integer a)
    abstract void setDatagramSize1(java.lang.Short a)
    abstract void setDatagramSize2(java.lang.Short a)
    abstract void setSequenceNo(java.lang.Short a)
}
