package me.biocomp.hubitat_ci.api.device_api.zwave.commands.notificationv8

trait NotificationSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.Boolean getAccessControl()
    abstract java.lang.Boolean getAppliance()
    abstract java.lang.Boolean getBurglar()
    abstract java.lang.String getCMD()
    abstract java.lang.Boolean getClock()
    abstract java.lang.Boolean getCo()
    abstract java.lang.Boolean getCo2()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getEmergency()
    abstract java.lang.Boolean getFirst()
    abstract java.lang.Boolean getGasAlarm()
    abstract java.lang.Boolean getHeat()
    abstract java.lang.Boolean getHomeHealth()
    abstract java.lang.Boolean getHomeMonitoring()
    abstract java.lang.Boolean getIrrigation()
    abstract java.lang.Boolean getLightSensor()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getPestControl()
    abstract java.lang.Boolean getPowerManagement()
    abstract java.lang.Boolean getSiren()
    abstract java.lang.Boolean getSmoke()
    abstract java.lang.Boolean getSystem()
    abstract java.lang.Boolean getWater()
    abstract java.lang.Boolean getWaterQuality()
    abstract java.lang.Boolean getWaterValve()
    abstract java.lang.Boolean getWeatherAlarm()
    abstract void setAccessControl(java.lang.Boolean a)
    abstract void setAppliance(java.lang.Boolean a)
    abstract void setBurglar(java.lang.Boolean a)
    abstract void setClock(java.lang.Boolean a)
    abstract void setCo(java.lang.Boolean a)
    abstract void setCo2(java.lang.Boolean a)
    abstract void setEmergency(java.lang.Boolean a)
    abstract void setFirst(java.lang.Boolean a)
    abstract void setGasAlarm(java.lang.Boolean a)
    abstract void setHeat(java.lang.Boolean a)
    abstract void setHomeHealth(java.lang.Boolean a)
    abstract void setHomeMonitoring(java.lang.Boolean a)
    abstract void setIrrigation(java.lang.Boolean a)
    abstract void setLightSensor(java.lang.Boolean a)
    abstract void setPestControl(java.lang.Boolean a)
    abstract void setPowerManagement(java.lang.Boolean a)
    abstract void setSiren(java.lang.Boolean a)
    abstract void setSmoke(java.lang.Boolean a)
    abstract void setSystem(java.lang.Boolean a)
    abstract void setWater(java.lang.Boolean a)
    abstract void setWaterQuality(java.lang.Boolean a)
    abstract void setWaterValve(java.lang.Boolean a)
    abstract void setWeatherAlarm(java.lang.Boolean a)
}
