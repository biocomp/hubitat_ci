package me.biocomp.hubitat_ci.api.device_api.zwave.commands.usercodev1

trait UsersNumberReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getSupportedUsers()
    abstract void setSupportedUsers(java.lang.Short a)
}
