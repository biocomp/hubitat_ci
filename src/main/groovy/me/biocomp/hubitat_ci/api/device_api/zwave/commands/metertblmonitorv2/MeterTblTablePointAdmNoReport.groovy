package me.biocomp.hubitat_ci.api.device_api.zwave.commands.metertblmonitorv2

trait MeterTblTablePointAdmNoReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getMeterPointAdmNumberCharacter()
    abstract java.lang.Short getNumberOfCharacters()
    abstract java.util.List getPayload()
    abstract void setMeterPointAdmNumberCharacter(java.util.List a)
    abstract void setNumberOfCharacters(java.lang.Short a)
}
