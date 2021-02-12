package me.biocomp.hubitat_ci.api.device_api.zwave.commands.thermostatsetbackv1

trait ThermostatSetbackReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSETBACK_TYPE_NO_OVERRIDE()
    abstract java.lang.Short getSETBACK_TYPE_PERMANENT_OVERRIDE()
    abstract java.lang.Short getSETBACK_TYPE_RESERVED3()
    abstract java.lang.Short getSETBACK_TYPE_TEMPORARY_OVERRIDE()
    abstract java.lang.Short getSetbackState()
    abstract java.lang.Short getSetbackType()
    abstract void setSETBACK_TYPE_NO_OVERRIDE(java.lang.Short a)
    abstract void setSETBACK_TYPE_PERMANENT_OVERRIDE(java.lang.Short a)
    abstract void setSETBACK_TYPE_RESERVED3(java.lang.Short a)
    abstract void setSETBACK_TYPE_TEMPORARY_OVERRIDE(java.lang.Short a)
    abstract void setSetbackState(java.lang.Short a)
    abstract void setSetbackType(java.lang.Short a)
}
