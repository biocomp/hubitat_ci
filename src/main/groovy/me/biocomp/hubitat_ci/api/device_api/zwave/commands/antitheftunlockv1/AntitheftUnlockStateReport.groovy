package me.biocomp.hubitat_ci.api.device_api.zwave.commands.antitheftunlockv1

trait AntitheftUnlockStateReport {
    abstract java.lang.String format()
    abstract java.util.List getAntitheftHint()
    abstract java.lang.Short getAntitheftHintLength()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getManufacturerID()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getRestricted()
    abstract java.lang.Boolean getState()
    abstract java.lang.Integer getZwaveAllianceLockingEntityID()
    abstract void setAntitheftHint(java.util.List a)
    abstract void setAntitheftHintLength(java.lang.Short a)
    abstract void setManufacturerID(java.lang.Integer a)
    abstract void setRestricted(java.lang.Boolean a)
    abstract void setState(java.lang.Boolean a)
    abstract void setZwaveAllianceLockingEntityID(java.lang.Integer a)
}
