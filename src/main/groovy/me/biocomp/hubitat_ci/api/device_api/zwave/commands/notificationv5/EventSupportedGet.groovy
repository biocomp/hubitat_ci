package me.biocomp.hubitat_ci.api.device_api.zwave.commands.notificationv5

trait EventSupportedGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getNOTIFICATION_TYPE_ACCESS_CONTROL()
    abstract java.lang.Short getNOTIFICATION_TYPE_APPLIANCE()
    abstract java.lang.Short getNOTIFICATION_TYPE_BURGLAR()
    abstract java.lang.Short getNOTIFICATION_TYPE_CLOCK()
    abstract java.lang.Short getNOTIFICATION_TYPE_CO()
    abstract java.lang.Short getNOTIFICATION_TYPE_CO2()
    abstract java.lang.Short getNOTIFICATION_TYPE_EMERGENCY()
    abstract java.lang.Short getNOTIFICATION_TYPE_FIRST()
    abstract java.lang.Short getNOTIFICATION_TYPE_HEAT()
    abstract java.lang.Short getNOTIFICATION_TYPE_HOME_HEALTH()
    abstract java.lang.Short getNOTIFICATION_TYPE_POWER_MANAGEMENT()
    abstract java.lang.Short getNOTIFICATION_TYPE_RESERVED0()
    abstract java.lang.Short getNOTIFICATION_TYPE_SMOKE()
    abstract java.lang.Short getNOTIFICATION_TYPE_SYSTEM()
    abstract java.lang.Short getNOTIFICATION_TYPE_WATER()
    abstract java.lang.Short getNotificationType()
    abstract java.util.List getPayload()
    abstract void setNOTIFICATION_TYPE_ACCESS_CONTROL(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_APPLIANCE(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_BURGLAR(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_CLOCK(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_CO(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_CO2(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_EMERGENCY(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_FIRST(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_HEAT(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_HOME_HEALTH(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_POWER_MANAGEMENT(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_RESERVED0(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_SMOKE(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_SYSTEM(java.lang.Short a)
    abstract void setNOTIFICATION_TYPE_WATER(java.lang.Short a)
    abstract void setNotificationType(java.lang.Short a)
}
