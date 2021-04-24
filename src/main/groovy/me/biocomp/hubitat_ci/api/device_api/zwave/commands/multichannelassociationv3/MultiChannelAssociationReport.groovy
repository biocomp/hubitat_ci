package me.biocomp.hubitat_ci.api.device_api.zwave.commands.multichannelassociationv3

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait MultiChannelAssociationReport extends Command{
    abstract java.lang.Short getGroupingIdentifier() // Original: public java.lang.Short hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.getGroupingIdentifier()
    abstract java.lang.Short getMaxNodesSupported() // Original: public java.lang.Short hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.getMaxNodesSupported()
    abstract java.util.List getMultiChannelNodeIds() // Original: public java.util.List hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.getMultiChannelNodeIds()
    abstract java.util.List getNodeId() // Original: public java.util.List hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.getNodeId()
    abstract java.lang.Short getReportsToFollow() // Original: public java.lang.Short hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.getReportsToFollow()
    abstract void setGroupingIdentifier(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.setGroupingIdentifier(java.lang.Short)
    abstract void setMaxNodesSupported(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.setMaxNodesSupported(java.lang.Short)
    abstract void setMultiChannelNodeIds(java.util.List a) // Original: public void hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.setMultiChannelNodeIds(java.util.List)
    abstract void setNodeId(java.util.List a) // Original: public void hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.setNodeId(java.util.List)
    abstract void setReportsToFollow(java.lang.Short a) // Original: public void hubitat.zwave.commands.multichannelassociationv2.MultiChannelAssociationReport.setReportsToFollow(java.lang.Short)
}
