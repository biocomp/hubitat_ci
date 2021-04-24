package me.biocomp.hubitat_ci.api.device_api.zwave.commands.metertblmonitorv1

trait MeterTblCurrentDataReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Integer getDataset()
    abstract java.lang.Short getDay()
    abstract java.lang.Short getHourLocalTime()
    abstract java.lang.Short getMinuteLocalTime()
    abstract java.lang.Short getMonth()
    abstract java.util.List getPayload()
    abstract java.lang.Short getRateType()
    abstract java.lang.Short getReportsToFollow()
    abstract java.lang.Short getSecondLocalTime()
    abstract java.lang.Integer getYear()
    abstract void setDataset(java.lang.Integer a)
    abstract void setDay(java.lang.Short a)
    abstract void setHourLocalTime(java.lang.Short a)
    abstract void setMinuteLocalTime(java.lang.Short a)
    abstract void setMonth(java.lang.Short a)
    abstract void setRateType(java.lang.Short a)
    abstract void setReportsToFollow(java.lang.Short a)
    abstract void setSecondLocalTime(java.lang.Short a)
    abstract void setYear(java.lang.Integer a)
}
