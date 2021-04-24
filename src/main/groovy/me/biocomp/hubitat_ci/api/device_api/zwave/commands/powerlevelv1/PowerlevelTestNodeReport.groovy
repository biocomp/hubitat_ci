package me.biocomp.hubitat_ci.api.device_api.zwave.commands.powerlevelv1

trait PowerlevelTestNodeReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSTATUS_OF_OPERATION_ZW_TEST_FAILED()
    abstract java.lang.Short getSTATUS_OF_OPERATION_ZW_TEST_INPROGRESS()
    abstract java.lang.Short getSTATUS_OF_OPERATION_ZW_TEST_SUCCES()
    abstract java.lang.Short getStatusOfOperation()
    abstract java.lang.Integer getTestFrameCount()
    abstract java.lang.Short getTestNodeid()
    abstract void setSTATUS_OF_OPERATION_ZW_TEST_FAILED(java.lang.Short a)
    abstract void setSTATUS_OF_OPERATION_ZW_TEST_INPROGRESS(java.lang.Short a)
    abstract void setSTATUS_OF_OPERATION_ZW_TEST_SUCCES(java.lang.Short a)
    abstract void setStatusOfOperation(java.lang.Short a)
    abstract void setTestFrameCount(java.lang.Integer a)
    abstract void setTestNodeid(java.lang.Short a)
}
