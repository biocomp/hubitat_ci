package me.biocomp.hubitat_ci.api.device_api.zwave.commands.energyproductionv1

trait EnergyProductionGet {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Short getPARAMETER_NUMBER_ENERGY_PRODUCTION_TODAY()
    abstract java.lang.Short getPARAMETER_NUMBER_INSTANT_ENERGY_PRODUCTION()
    abstract java.lang.Short getPARAMETER_NUMBER_TOTAL_ENERGY_PRODUCTION()
    abstract java.lang.Short getPARAMETER_NUMBER_TOTAL_PRODUCTION_TIME()
    abstract java.lang.Short getParameterNumber()
    abstract java.util.List getPayload()
    abstract void setPARAMETER_NUMBER_ENERGY_PRODUCTION_TODAY(java.lang.Short a)
    abstract void setPARAMETER_NUMBER_INSTANT_ENERGY_PRODUCTION(java.lang.Short a)
    abstract void setPARAMETER_NUMBER_TOTAL_ENERGY_PRODUCTION(java.lang.Short a)
    abstract void setPARAMETER_NUMBER_TOTAL_PRODUCTION_TIME(java.lang.Short a)
    abstract void setParameterNumber(java.lang.Short a)
}
