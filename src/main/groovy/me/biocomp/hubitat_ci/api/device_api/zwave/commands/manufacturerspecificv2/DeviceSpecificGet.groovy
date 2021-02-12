package me.biocomp.hubitat_ci.api.device_api.zwave.commands.manufacturerspecificv2

trait DeviceSpecificGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getDEVICE_ID_TYPE_FACTORY_DEFAULT()
    abstract java.lang.Short getDEVICE_ID_TYPE_PSEUDO_RANDOM()
    abstract java.lang.Short getDEVICE_ID_TYPE_SERIAL_NUMBER()
    abstract java.lang.Short getDeviceIdType()
    abstract java.util.List getPayload()
    abstract void setDEVICE_ID_TYPE_FACTORY_DEFAULT(java.lang.Short a)
    abstract void setDEVICE_ID_TYPE_PSEUDO_RANDOM(java.lang.Short a)
    abstract void setDEVICE_ID_TYPE_SERIAL_NUMBER(java.lang.Short a)
    abstract void setDeviceIdType(java.lang.Short a)
}
