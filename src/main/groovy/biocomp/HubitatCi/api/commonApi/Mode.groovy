package me.biocomp.hubitat_ci.api.common_api;

/*
Real methods:
Meta/Common:
public void com.hubitat.hub.domain.Mode.setProperty(java.lang.String,java.lang.Object),
public java.lang.String com.hubitat.hub.domain.Mode.toString()
public groovy.lang.MetaClass com.hubitat.hub.domain.Mode.getMetaClass(),
public java.lang.Object com.hubitat.hub.domain.Mode.getProperty(java.lang.String),
public java.lang.Object com.hubitat.hub.domain.Mode.invokeMethod(java.lang.String,java.lang.Object),
public void com.hubitat.hub.domain.Mode.setMetaClass(groovy.lang.MetaClass),

Getters/main methods:
++ public java.lang.Long com.hubitat.hub.domain.Mode.getId(),
++ public java.lang.Long com.hubitat.hub.domain.Mode.getLocationId(),
++ public java.lang.String com.hubitat.hub.domain.Mode.getName(),

Setters:
public void com.hubitat.hub.domain.Mode.setId(java.lang.Long),
public void com.hubitat.hub.domain.Mode.setLocationId(java.lang.Long),
public void com.hubitat.hub.domain.Mode.setName(java.lang.String),
 */

interface Mode
{
    /**
     * @return unique internal identifier
     */
    String getId();

    Long getLocationId();

    /**
     * @return user-friendly mode name
     */
    String getName();
}

