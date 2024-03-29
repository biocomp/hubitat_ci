package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensormultilevelv3

trait SensorMultilevelReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrecision()
    abstract java.lang.Short getSENSOR_TYPE_AIR_FLOW_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_ATMOSPHERIC_PRESSURE_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_BAROMETRIC_PRESSURE_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_CO2_LEVEL_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_CURRENT_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_DEW_POINT_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_DIRECTION_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_DISTANCE_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_GENERAL_PURPOSE_VALUE_VERSION_1()
    abstract java.lang.Short getSENSOR_TYPE_LUMINANCE_VERSION_1()
    abstract java.lang.Short getSENSOR_TYPE_POWER_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_RAIN_RATE_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_RELATIVE_HUMIDITY_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_SOLAR_RADIATION_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_TANK_CAPACITY_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_TEMPERATURE_VERSION_1()
    abstract java.lang.Short getSENSOR_TYPE_TIDE_LEVEL_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_VELOCITY_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_VOLTAGE_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_WEIGHT_VERSION_3()
    abstract java.lang.Short getScale()
    abstract java.math.BigDecimal getScaledSensorValue()
    abstract java.lang.Short getSensorType()
    abstract java.util.List getSensorValue()
    abstract java.lang.Short getSize()
    abstract void setPrecision(java.lang.Short a)
    abstract void setSENSOR_TYPE_AIR_FLOW_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_ATMOSPHERIC_PRESSURE_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_BAROMETRIC_PRESSURE_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_CO2_LEVEL_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_CURRENT_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_DEW_POINT_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_DIRECTION_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_DISTANCE_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_GENERAL_PURPOSE_VALUE_VERSION_1(java.lang.Short a)
    abstract void setSENSOR_TYPE_LUMINANCE_VERSION_1(java.lang.Short a)
    abstract void setSENSOR_TYPE_POWER_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_RAIN_RATE_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_RELATIVE_HUMIDITY_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_SOLAR_RADIATION_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_TANK_CAPACITY_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_TEMPERATURE_VERSION_1(java.lang.Short a)
    abstract void setSENSOR_TYPE_TIDE_LEVEL_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_VELOCITY_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_VOLTAGE_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_WEIGHT_VERSION_3(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScaledSensorValue(java.math.BigDecimal a)
    abstract void setSensorType(java.lang.Short a)
    abstract void setSensorValue(java.util.List a)
    abstract void setSize(java.lang.Short a)
}
