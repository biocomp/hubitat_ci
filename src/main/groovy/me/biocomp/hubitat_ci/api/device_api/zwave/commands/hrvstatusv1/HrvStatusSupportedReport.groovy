package me.biocomp.hubitat_ci.api.device_api.zwave.commands.hrvstatusv1

trait HrvStatusSupportedReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getDischargeAirTemperature()
    abstract java.lang.Boolean getExhaustAirTemperature()
    abstract java.lang.Boolean getOutdoorAirTemperature()
    abstract java.util.List getPayload()
    abstract java.lang.Boolean getRelativeHumidityInRoom()
    abstract java.lang.Boolean getRemainingFilterLife()
    abstract java.lang.Boolean getRoomTemperature()
    abstract java.lang.Boolean getSupplyAirTemperature()
    abstract void setDischargeAirTemperature(java.lang.Boolean a)
    abstract void setExhaustAirTemperature(java.lang.Boolean a)
    abstract void setOutdoorAirTemperature(java.lang.Boolean a)
    abstract void setRelativeHumidityInRoom(java.lang.Boolean a)
    abstract void setRemainingFilterLife(java.lang.Boolean a)
    abstract void setRoomTemperature(java.lang.Boolean a)
    abstract void setSupplyAirTemperature(java.lang.Boolean a)
}
