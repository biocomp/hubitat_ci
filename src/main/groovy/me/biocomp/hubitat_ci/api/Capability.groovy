package me.biocomp.hubitat_ci.api

/*
Real methods:
public java.util.Map com.hubitat.hub.domain.Capability.toMap(),
public java.util.Map com.hubitat.hub.domain.Capability.toMap(boolean),
public java.lang.String com.hubitat.hub.domain.Capability.toString()

public java.lang.Object com.hubitat.hub.domain.Capability.invokeMethod(java.lang.String,java.lang.Object),
public groovy.lang.MetaClass com.hubitat.hub.domain.Capability.getMetaClass(),
public java.lang.Object com.hubitat.hub.domain.Capability.getProperty(java.lang.String),
public void com.hubitat.hub.domain.Capability.setProperty(java.lang.String,java.lang.Object),
public void com.hubitat.hub.domain.Capability.setMetaClass(groovy.lang.MetaClass),

Getters:
++ public java.util.List com.hubitat.hub.domain.Capability.getAttributes(),
++ public java.util.List com.hubitat.hub.domain.Capability.getCommands(),
public java.lang.Long com.hubitat.hub.domain.Capability.getId(),
++ public java.lang.String com.hubitat.hub.domain.Capability.getName(),
public java.lang.String com.hubitat.hub.domain.Capability.getReference(),
public java.lang.Long com.hubitat.hub.domain.Capability.getVersion(),

Setters:
public void com.hubitat.hub.domain.Capability.setAttributes(java.util.List),
public void com.hubitat.hub.domain.Capability.setCommands(java.util.List),
public void com.hubitat.hub.domain.Capability.setId(java.lang.Long),
public void com.hubitat.hub.domain.Capability.setName(java.lang.String),
public void com.hubitat.hub.domain.Capability.setReference(java.lang.String),
public void com.hubitat.hub.domain.Capability.setVersion(java.lang.Long),
*/
trait Capability
{
    abstract List<Attribute> getAttributes()
    abstract List<Command> getCommands()
    abstract String getName()

    java.lang.Long getId() {} // Original: public java.lang.Long com.hubitat.hub.domain.Capability.getId()
    java.lang.String getReference() {} // Original: public java.lang.String com.hubitat.hub.domain.Capability.getReference()
    java.lang.Long getVersion() {} // Original: public java.lang.Long com.hubitat.hub.domain.Capability.getVersion()
    void setAttributes(java.util.List a) {} // Original: public void com.hubitat.hub.domain.Capability.setAttributes(java.util.List)
    void setCommands(java.util.List a) {} // Original: public void com.hubitat.hub.domain.Capability.setCommands(java.util.List)
    void setId(java.lang.Long a) {} // Original: public void com.hubitat.hub.domain.Capability.setId(java.lang.Long)
    void setName(java.lang.String a) {} // Original: public void com.hubitat.hub.domain.Capability.setName(java.lang.String)
    void setReference(java.lang.String a) {} // Original: public void com.hubitat.hub.domain.Capability.setReference(java.lang.String)
    void setVersion(java.lang.Long a) {} // Original: public void com.hubitat.hub.domain.Capability.setVersion(java.lang.Long)
    java.util.Map toMap() {} // Original: public java.util.Map com.hubitat.hub.domain.Capability.toMap()
    java.util.Map toMap(boolean a) {} // Original: public java.util.Map com.hubitat.hub.domain.Capability.toMap(boolean)
}