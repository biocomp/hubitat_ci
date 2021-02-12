package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelv4

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiChannelAggregatedMembersReport extends Command{
    abstract java.lang.Short getAggregatedEndPoint() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv4.MultiChannelAggregatedMembersReport.getAggregatedEndPoint()
    abstract java.util.List getAggregatedMembers() // Original: public java.util.List hubitat.zwave.commands.multichannelv4.MultiChannelAggregatedMembersReport.getAggregatedMembers()
    abstract java.lang.Short getBitMasks() // Original: public java.lang.Short hubitat.zwave.commands.multichannelv4.MultiChannelAggregatedMembersReport.getBitMasks()
    abstract void setAggregatedEndPoint(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelAggregatedMembersReport.setAggregatedEndPoint(java.lang.Short)
    abstract void setAggregatedMembers(java.util.List a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelAggregatedMembersReport.setAggregatedMembers(java.util.List)
    abstract void setBitMasks(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelv4.MultiChannelAggregatedMembersReport.setBitMasks(java.lang.Short)
}
