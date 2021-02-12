package me.biocomp.hubitat_ci.api.device_api.zwave.commands.meterv2

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MeterReport extends Command{
    abstract java.lang.Integer getDeltaTime() // Original: public java.lang.Integer hubitat.zwave.commands.meterv2.MeterReport.getDeltaTime()
    abstract java.lang.Short getMETER_TYPE_ELECTRIC_METER() // Original: public static java.lang.Short hubitat.zwave.commands.meterv1.MeterReport.getMETER_TYPE_ELECTRIC_METER()
    abstract java.lang.Short getMETER_TYPE_GAS_METER() // Original: public static java.lang.Short hubitat.zwave.commands.meterv1.MeterReport.getMETER_TYPE_GAS_METER()
    abstract java.lang.Short getMETER_TYPE_WATER_METER() // Original: public static java.lang.Short hubitat.zwave.commands.meterv1.MeterReport.getMETER_TYPE_WATER_METER()
    abstract java.lang.Short getMeterType() // Original: public java.lang.Short hubitat.zwave.commands.meterv1.MeterReport.getMeterType()
    abstract java.util.List getMeterValue() // Original: public java.util.List hubitat.zwave.commands.meterv1.MeterReport.getMeterValue()
    abstract java.lang.Short getPrecision() // Original: public java.lang.Short hubitat.zwave.commands.meterv1.MeterReport.getPrecision()
    abstract java.util.List getPreviousMeterValue() // Original: public java.util.List hubitat.zwave.commands.meterv2.MeterReport.getPreviousMeterValue()
    abstract java.lang.Short getRateType() // Original: public java.lang.Short hubitat.zwave.commands.meterv2.MeterReport.getRateType()
    abstract java.lang.Short getScale() // Original: public java.lang.Short hubitat.zwave.commands.meterv1.MeterReport.getScale()
    abstract java.math.BigDecimal getScaledMeterValue() // Original: public java.math.BigDecimal hubitat.zwave.commands.meterv1.MeterReport.getScaledMeterValue()
    abstract java.math.BigDecimal getScaledPreviousMeterValue() // Original: public java.math.BigDecimal hubitat.zwave.commands.meterv2.MeterReport.getScaledPreviousMeterValue()
    abstract java.lang.Short getSize() // Original: public java.lang.Short hubitat.zwave.commands.meterv1.MeterReport.getSize()
    abstract void setDeltaTime(java.lang.Integer a) // Original: public void hubitat.zwave.commands.meterv2.MeterReport.setDeltaTime(java.lang.Integer)
    abstract void setMETER_TYPE_ELECTRIC_METER(java.lang.Short a) // Original: public static void hubitat.zwave.commands.meterv1.MeterReport.setMETER_TYPE_ELECTRIC_METER(java.lang.Short)
    abstract void setMETER_TYPE_GAS_METER(java.lang.Short a) // Original: public static void hubitat.zwave.commands.meterv1.MeterReport.setMETER_TYPE_GAS_METER(java.lang.Short)
    abstract void setMETER_TYPE_WATER_METER(java.lang.Short a) // Original: public static void hubitat.zwave.commands.meterv1.MeterReport.setMETER_TYPE_WATER_METER(java.lang.Short)
    abstract void setMeterType(java.lang.Short a) // Original: public void hubitat.zwave.commands.meterv1.MeterReport.setMeterType(java.lang.Short)
    abstract void setMeterValue(java.util.List a) // Original: public void hubitat.zwave.commands.meterv1.MeterReport.setMeterValue(java.util.List)
    abstract void setPrecision(java.lang.Short a) // Original: public void hubitat.zwave.commands.meterv1.MeterReport.setPrecision(java.lang.Short)
    abstract void setPreviousMeterValue(java.util.List a) // Original: public void hubitat.zwave.commands.meterv2.MeterReport.setPreviousMeterValue(java.util.List)
    abstract void setRateType(java.lang.Short a) // Original: public void hubitat.zwave.commands.meterv2.MeterReport.setRateType(java.lang.Short)
    abstract void setScale(java.lang.Short a) // Original: public void hubitat.zwave.commands.meterv1.MeterReport.setScale(java.lang.Short)
    abstract void setSize(java.lang.Short a) // Original: public void hubitat.zwave.commands.meterv1.MeterReport.setSize(java.lang.Short)
}
