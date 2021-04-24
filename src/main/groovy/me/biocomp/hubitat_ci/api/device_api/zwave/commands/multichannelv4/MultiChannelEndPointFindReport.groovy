package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv4

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiChannelEndPointFindReport extends Command{
    abstract java.util.List getEndPoints() // Original: public java.util.List hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.getEndPoints()
    abstract java.lang.Short getGenericDeviceClass() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.getGenericDeviceClass()
    abstract java.lang.Short getReportsToFollow() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.getReportsToFollow()
    abstract java.lang.Short getSpecificDeviceClass() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.getSpecificDeviceClass()
    abstract void setEndPoints(java.util.List a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.setEndPoints(java.util.List)
    abstract void setGenericDeviceClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.setGenericDeviceClass(java.lang.Short)
    abstract void setReportsToFollow(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.setReportsToFollow(java.lang.Short)
    abstract void setSpecificDeviceClass(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv2.MultiChannelEndPointFindReport.setSpecificDeviceClass(java.lang.Short)
}
