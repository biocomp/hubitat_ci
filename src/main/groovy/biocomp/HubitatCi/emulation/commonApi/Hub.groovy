package biocomp.hubitatCi.emulation.commonApi;

/**
 * Real methods:
 *
 * Common:
 * public java.util.Map com.hubitat.hub.domain.Hub.toMap(),
 * public java.lang.String com.hubitat.hub.domain.Hub.toString()
 *
 * Meta:
 * public void com.hubitat.hub.domain.Hub.setMetaClass(groovy.lang.MetaClass)
 * public groovy.lang.MetaClass com.hubitat.hub.domain.Hub.getMetaClass(),
 * public java.lang.Object com.hubitat.hub.domain.Hub.getProperty(java.lang.String),
 * public void com.hubitat.hub.domain.Hub.setProperty(java.lang.String,java.lang.Object),
 * public java.lang.Object com.hubitat.hub.domain.Hub.invokeMethod(java.lang.String,java.lang.Object),
 *
 * Getters (most relevant to the trait)
 * public java.util.Date com.hubitat.hub.domain.Hub.getCreateTime(),
 * public java.util.Map com.hubitat.hub.domain.Hub.getData(),
 * public java.util.Map com.hubitat.hub.domain.Hub.getDataSorted(),
 * public java.lang.String com.hubitat.hub.domain.Hub.getDataValue(java.lang.String),
 * ++ public java.lang.String com.hubitat.hub.domain.Hub.getFirmwareVersionString(),
 * public java.lang.String com.hubitat.hub.domain.Hub.getHardwareID(),
 * ++ public java.lang.Long com.hubitat.hub.domain.Hub.getId(),
 * public java.util.Date com.hubitat.hub.domain.Hub.getLastActivityTime(),
 * ++ public java.lang.String com.hubitat.hub.domain.Hub.getLocalIP(),
 * ++ public java.lang.String com.hubitat.hub.domain.Hub.getLocalSrvPortTCP(),
 * public java.lang.Long com.hubitat.hub.domain.Hub.getLocationId(),
 * ++ public java.lang.String com.hubitat.hub.domain.Hub.getName(),
 * ++ public java.lang.String com.hubitat.hub.domain.Hub.getType(),
 * public java.util.Date com.hubitat.hub.domain.Hub.getUpdateTime(),
 * public java.math.BigInteger com.hubitat.hub.domain.Hub.getUptime(),
 * public java.lang.Long com.hubitat.hub.domain.Hub.getVersion(),
 * ++ public java.lang.String com.hubitat.hub.domain.Hub.getZigbeeEui(),
 * ++ public java.lang.String com.hubitat.hub.domain.Hub.getZigbeeId(),
 * public java.lang.Boolean com.hubitat.hub.domain.Hub.isBatteryInUse(),
 *
 * Setters:
 * public void com.hubitat.hub.domain.Hub.addData(java.util.Map),
 * public void com.hubitat.hub.domain.Hub.setCreateTime(java.util.Date),
 * public void com.hubitat.hub.domain.Hub.setData(java.util.Map),
 * public void com.hubitat.hub.domain.Hub.setId(java.lang.Long),
 * public void com.hubitat.hub.domain.Hub.setLastActivityTime(java.util.Date),
 * public void com.hubitat.hub.domain.Hub.setLocationId(java.lang.Long),
 * public void com.hubitat.hub.domain.Hub.setName(java.lang.String),
 * public void com.hubitat.hub.domain.Hub.setUpdateTime(java.util.Date),
 * public void com.hubitat.hub.domain.Hub.setVersion(java.lang.Long),
 */

interface Hub
{
    abstract String getFirmwareVersionString();
    abstract Long getId();
    abstract String getLocalIP();
    abstract String getLocalSrvPortTCP();
    abstract String getName();
    abstract String getType();
    abstract String getZigbeeEui();
    abstract String getZigbeeId();
}

