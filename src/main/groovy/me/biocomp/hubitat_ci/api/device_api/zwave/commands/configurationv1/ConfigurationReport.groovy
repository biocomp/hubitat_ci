package me.biocomp.hubitat_ci.api.device_api.zwave.commands.configurationv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait ConfigurationReport extends Command{
    abstract java.util.List configurationValue
    abstract java.lang.Short parameterNumber
    abstract java.lang.Short size

    abstract java.math.BigInteger getScaledConfigurationValue() // Original: public java.math.BigInteger hubitat.zwave.commands.configurationv1.ConfigurationReport.getScaledConfigurationValue()
}
