package me.biocomp.hubitat_ci.api.domain

trait Device {
    abstract boolean canEqual(java.lang.Object a)
    abstract me.biocomp.hubitat_ci.api.State currentState(java.lang.String a)
    abstract me.biocomp.hubitat_ci.api.State currentState(java.lang.String a, boolean b)
    abstract java.lang.Object currentValue(java.lang.String a)
    abstract java.lang.Object currentValue(java.lang.String a, boolean b)
    abstract boolean equals(java.lang.Object a)
    abstract java.lang.String getControllerType()
    abstract java.util.Date getCreateTime()
    abstract java.util.Map getCurrentStates()
    abstract java.util.Map getCurrentStatesSorted()
    abstract java.util.Map getData()
    abstract java.lang.String getDataJson()
    abstract java.lang.String getDataValue(java.lang.String a)
    abstract java.lang.String getDeviceDataByName(java.lang.String a)
    abstract java.lang.Long getDeviceId()
    abstract java.lang.String getDeviceNetworkId()
    abstract java.lang.Long getDeviceTypeId()
    abstract java.lang.String getDeviceTypeName()
    abstract boolean getDisabled()
    abstract java.lang.Boolean getDisplayAsChild()
    abstract java.lang.String getDisplayAttributes()
    abstract java.lang.String getDisplayName()
    abstract java.lang.String getDriverType()
    abstract java.lang.String getEndpointId()
    abstract java.lang.Long getGroupId()
    abstract java.lang.String getGroupName()
    abstract me.biocomp.hubitat_ci.api.common_api.Hub getHub()
    abstract java.lang.Long getHubId()
    abstract java.lang.String getHubName()
    abstract java.lang.Long getId()
    abstract java.lang.Boolean getIsComponent()
    abstract java.lang.String getLabel()
    abstract java.lang.String getLanId()
    abstract java.util.Date getLastActivityTime()
    abstract java.lang.Long getLocationId()
    abstract java.lang.String getLocationName()
    abstract java.lang.Integer getMaxEvents()
    abstract java.lang.Integer getMaxStates()
    abstract boolean getMeshEnabled()
    abstract boolean getMeshFullSync()
    abstract boolean getMeshSelectionEnabled()
    abstract java.lang.String getName()
    abstract java.lang.Long getParentAppId()
    abstract java.lang.Long getParentDeviceId()
    abstract java.lang.String getRemoteDeviceUrl()
    abstract java.lang.String getStatus()
    abstract java.util.Date getUpdateTime()
    abstract java.lang.Long getVersion()
    abstract java.lang.String getZigbeeId()
    abstract int hashCode()
    abstract boolean isDisabled()
    abstract boolean isLinkedAndDisabled()
    abstract boolean isLinkedDevice()
    abstract boolean isMeshEnabled()
    abstract boolean isMeshFullSync()
    abstract java.lang.Object latestValue(java.lang.String a)
    abstract void populateCurrentStatesFromList(java.util.List a)
    abstract void removeDataValue(java.lang.String a)
    abstract void setControllerType(java.lang.String a)
    abstract void setCreateTime(java.util.Date a)
    abstract void setCurrentStates(java.util.Map a)
    abstract void setData(java.util.Map a)
    abstract void setDataJson(java.lang.String a)
    abstract void setDeviceNetworkId(java.lang.String a)
    abstract void setDeviceTypeId(java.lang.Long a)
    abstract void setDeviceTypeName(java.lang.String a)
    abstract void setDisabled(boolean a)
    abstract void setDisplayAsChild(java.lang.Boolean a)
    abstract void setDisplayAttributes(java.lang.String a)
    abstract void setDriverType(java.lang.String a)
    abstract void setEndpointId(java.lang.String a)
    abstract void setGroupId(java.lang.Long a)
    abstract void setGroupName(java.lang.String a)
    abstract void setHubId(java.lang.Long a)
    abstract void setId(java.lang.Long a)
    abstract void setIsComponent(java.lang.Boolean a)
    abstract void setLabel(java.lang.String a)
    abstract void setLanId(java.lang.String a)
    abstract void setLastActivityTime(java.util.Date a)
    abstract void setMaxEvents(java.lang.Integer a)
    abstract void setMaxStates(java.lang.Integer a)
    abstract void setMeshEnabled(boolean a)
    abstract void setMeshFullSync(boolean a)
    abstract void setName(java.lang.String a)
    abstract void setParentAppId(java.lang.Long a)
    abstract void setParentDeviceId(java.lang.Long a)
    abstract void setUpdateTime(java.util.Date a)
    abstract void setVersion(java.lang.Long a)
    abstract void setZigbeeId(java.lang.String a)
    abstract java.util.Map toMap()
    abstract void updateDataValue(java.lang.String a, java.lang.String b)
}