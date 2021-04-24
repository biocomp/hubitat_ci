package me.biocomp.hubitat_ci.api.device_api.zwave.commands.languagev1

trait LanguageSet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getCountry()
    abstract java.lang.Integer getLanguage()
    abstract java.util.List getPayload()
    abstract void setCountry(java.lang.Integer a)
    abstract void setLanguage(java.lang.Integer a)
}
