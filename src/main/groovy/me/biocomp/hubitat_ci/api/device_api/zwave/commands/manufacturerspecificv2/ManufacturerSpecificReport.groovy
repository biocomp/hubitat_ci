package me.biocomp.hubitat_ci.api.device_api.zwave.commands.manufacturerspecificv2

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait ManufacturerSpecificReport extends Command{
    abstract java.lang.String getManufacturerName() // Original: public java.lang.String hubitat.zwave.commands.manufacturerspecificv1.ManufacturerSpecificReport.getManufacturerName()

    abstract java.lang.Integer manufacturerId
    abstract java.lang.Integer productId
    abstract java.lang.Integer productTypeId
}
