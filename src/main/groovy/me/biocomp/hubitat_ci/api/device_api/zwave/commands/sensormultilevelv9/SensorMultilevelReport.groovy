package me.biocomp.hubitat_ci.api.device_api.zwave.commands.sensormultilevelv9

trait SensorMultilevelReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.util.List getPayload()
    abstract java.lang.Short getPrecision()
    abstract java.lang.Short getSENSOR_TYPE_ACCELERATION_X_AXIS_V8()
    abstract java.lang.Short getSENSOR_TYPE_ACCELERATION_Y_AXIS_V8()
    abstract java.lang.Short getSENSOR_TYPE_ACCELERATION_Z_AXIS_V8()
    abstract java.lang.Short getSENSOR_TYPE_AIR_FLOW_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_ANGLE_POSITION_VERSION_4()
    abstract java.lang.Short getSENSOR_TYPE_ATMOSPHERIC_PRESSURE_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_BAROMETRIC_PRESSURE_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_BASIC_METABOLIC_RATE_BMR_V7()
    abstract java.lang.Short getSENSOR_TYPE_BLOOD_PRESSURE_V7()
    abstract java.lang.Short getSENSOR_TYPE_BODY_MASS_INDEX_BMI_V7()
    abstract java.lang.Short getSENSOR_TYPE_BONE_MASS_V7()
    abstract java.lang.Short getSENSOR_TYPE_CARBON_MONOXIDE_CO_LEVEL_V7()
    abstract java.lang.Short getSENSOR_TYPE_CO2_LEVEL_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_CURRENT_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_DEW_POINT_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_DIRECTION_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_DISTANCE_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_ELECTRICAL_CONDUCTIVITY_V5()
    abstract java.lang.Short getSENSOR_TYPE_ELECTRICAL_RESISTIVITY_V5()
    abstract java.lang.Short getSENSOR_TYPE_FAT_MASS_V7()
    abstract java.lang.Short getSENSOR_TYPE_FORMALDEHYDE_CH2O_LEVEL_V7()
    abstract java.lang.Short getSENSOR_TYPE_GENERAL_PURPOSE_VALUE_VERSION_1()
    abstract java.lang.Short getSENSOR_TYPE_HEART_RATE_V7()
    abstract java.lang.Short getSENSOR_TYPE_LOUDNESS_V5()
    abstract java.lang.Short getSENSOR_TYPE_LUMINANCE_VERSION_1()
    abstract java.lang.Short getSENSOR_TYPE_METHANE_DENSITY_V7()
    abstract java.lang.Short getSENSOR_TYPE_MOISTURE_V5()
    abstract java.lang.Short getSENSOR_TYPE_MUSCLE_MASS_V7()
    abstract java.lang.Short getSENSOR_TYPE_PARTICULATE_MATTER_V7()
    abstract java.lang.Short getSENSOR_TYPE_POWER_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_RADON_CONCENTRATION_V7()
    abstract java.lang.Short getSENSOR_TYPE_RAIN_RATE_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_RELATIVE_HUMIDITY_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_REPORT_FREQUENCY_V6()
    abstract java.lang.Short getSENSOR_TYPE_RF_SIGNAL_STRENGTH_V9()
    abstract java.lang.Short getSENSOR_TYPE_ROTATION_V5()
    abstract java.lang.Short getSENSOR_TYPE_SEISMIC_INTENSITY_V5()
    abstract java.lang.Short getSENSOR_TYPE_SEISMIC_MAGNITUDE_V5()
    abstract java.lang.Short getSENSOR_TYPE_SMOKE_DENSITY_V8()
    abstract java.lang.Short getSENSOR_TYPE_SOIL_HUMIDITY_V7()
    abstract java.lang.Short getSENSOR_TYPE_SOIL_REACTIVITY_V7()
    abstract java.lang.Short getSENSOR_TYPE_SOIL_SALINITY_V7()
    abstract java.lang.Short getSENSOR_TYPE_SOIL_TEMPERATURE_V5()
    abstract java.lang.Short getSENSOR_TYPE_SOLAR_RADIATION_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_TANK_CAPACITY_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_TARGET_TEMPERATURE_V6()
    abstract java.lang.Short getSENSOR_TYPE_TEMPERATURE_VERSION_1()
    abstract java.lang.Short getSENSOR_TYPE_TIDE_LEVEL_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_TOTAL_BODY_WATER_TBW_V7()
    abstract java.lang.Short getSENSOR_TYPE_ULTRAVIOLET_V5()
    abstract java.lang.Short getSENSOR_TYPE_VELOCITY_VERSION_2()
    abstract java.lang.Short getSENSOR_TYPE_VOLATILE_ORGANIC_COMPOUND_V7()
    abstract java.lang.Short getSENSOR_TYPE_VOLTAGE_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE_WATER_FLOW_V9()
    abstract java.lang.Short getSENSOR_TYPE_WATER_PRESSURE_V9()
    abstract java.lang.Short getSENSOR_TYPE_WATER_TEMPERATURE_V5()
    abstract java.lang.Short getSENSOR_TYPE_WEIGHT_VERSION_3()
    abstract java.lang.Short getSENSOR_TYPE__TIME_V6()
    abstract java.lang.Short getScale()
    abstract java.math.BigDecimal getScaledSensorValue()
    abstract java.lang.Short getSensorType()
    abstract java.util.List getSensorValue()
    abstract java.lang.Short getSize()
    abstract void setPrecision(java.lang.Short a)
    abstract void setSENSOR_TYPE_ACCELERATION_X_AXIS_V8(java.lang.Short a)
    abstract void setSENSOR_TYPE_ACCELERATION_Y_AXIS_V8(java.lang.Short a)
    abstract void setSENSOR_TYPE_ACCELERATION_Z_AXIS_V8(java.lang.Short a)
    abstract void setSENSOR_TYPE_AIR_FLOW_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_ANGLE_POSITION_VERSION_4(java.lang.Short a)
    abstract void setSENSOR_TYPE_ATMOSPHERIC_PRESSURE_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_BAROMETRIC_PRESSURE_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_BASIC_METABOLIC_RATE_BMR_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_BLOOD_PRESSURE_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_BODY_MASS_INDEX_BMI_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_BONE_MASS_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_CARBON_MONOXIDE_CO_LEVEL_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_CO2_LEVEL_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_CURRENT_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_DEW_POINT_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_DIRECTION_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_DISTANCE_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_ELECTRICAL_CONDUCTIVITY_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_ELECTRICAL_RESISTIVITY_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_FAT_MASS_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_FORMALDEHYDE_CH2O_LEVEL_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_GENERAL_PURPOSE_VALUE_VERSION_1(java.lang.Short a)
    abstract void setSENSOR_TYPE_HEART_RATE_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_LOUDNESS_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_LUMINANCE_VERSION_1(java.lang.Short a)
    abstract void setSENSOR_TYPE_METHANE_DENSITY_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_MOISTURE_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_MUSCLE_MASS_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_PARTICULATE_MATTER_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_POWER_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_RADON_CONCENTRATION_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_RAIN_RATE_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_RELATIVE_HUMIDITY_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_REPORT_FREQUENCY_V6(java.lang.Short a)
    abstract void setSENSOR_TYPE_RF_SIGNAL_STRENGTH_V9(java.lang.Short a)
    abstract void setSENSOR_TYPE_ROTATION_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_SEISMIC_INTENSITY_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_SEISMIC_MAGNITUDE_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_SMOKE_DENSITY_V8(java.lang.Short a)
    abstract void setSENSOR_TYPE_SOIL_HUMIDITY_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_SOIL_REACTIVITY_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_SOIL_SALINITY_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_SOIL_TEMPERATURE_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_SOLAR_RADIATION_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_TANK_CAPACITY_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_TARGET_TEMPERATURE_V6(java.lang.Short a)
    abstract void setSENSOR_TYPE_TEMPERATURE_VERSION_1(java.lang.Short a)
    abstract void setSENSOR_TYPE_TIDE_LEVEL_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_TOTAL_BODY_WATER_TBW_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_ULTRAVIOLET_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_VELOCITY_VERSION_2(java.lang.Short a)
    abstract void setSENSOR_TYPE_VOLATILE_ORGANIC_COMPOUND_V7(java.lang.Short a)
    abstract void setSENSOR_TYPE_VOLTAGE_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE_WATER_FLOW_V9(java.lang.Short a)
    abstract void setSENSOR_TYPE_WATER_PRESSURE_V9(java.lang.Short a)
    abstract void setSENSOR_TYPE_WATER_TEMPERATURE_V5(java.lang.Short a)
    abstract void setSENSOR_TYPE_WEIGHT_VERSION_3(java.lang.Short a)
    abstract void setSENSOR_TYPE__TIME_V6(java.lang.Short a)
    abstract void setScale(java.lang.Short a)
    abstract void setScaledSensorValue(java.math.BigDecimal a)
    abstract void setSensorType(java.lang.Short a)
    abstract void setSensorValue(java.util.List a)
    abstract void setSize(java.lang.Short a)
}
