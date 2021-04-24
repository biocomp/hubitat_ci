package me.biocomp.hubitat_ci.api.device_api.zwave.commands.securityv1

trait SecuritySchemeInherit {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSupportedSecuritySchemes()
    abstract void setSupportedSecuritySchemes(java.lang.Short a)
}
