package me.biocomp.hubitat_ci.api

/*
 * Real methods:
 * public boolean com.hubitat.hub.domain.Attribute.equals(java.lang.Object),
 * public java.util.Map com.hubitat.hub.domain.Attribute.toMap(),
 * public java.util.Map com.hubitat.hub.domain.Attribute.toMap(boolean),
 * public java.lang.String com.hubitat.hub.domain.Attribute.toString()
 *
 * public groovy.lang.MetaClass com.hubitat.hub.domain.Attribute.getMetaClass(),
 * public java.lang.Object com.hubitat.hub.domain.Attribute.getProperty(java.lang.String),
 * public java.lang.Object com.hubitat.hub.domain.Attribute.invokeMethod(java.lang.String,java.lang.Object),
 * public void com.hubitat.hub.domain.Attribute.setProperty(java.lang.String,java.lang.Object),
 * public void com.hubitat.hub.domain.Attribute.setMetaClass(groovy.lang.MetaClass),

 * ++ public java.lang.String com.hubitat.hub.domain.Attribute.getDataType(),
 * public java.lang.Long com.hubitat.hub.domain.Attribute.getId(),
 * ++ public java.lang.String com.hubitat.hub.domain.Attribute.getName(),
 * public java.util.List com.hubitat.hub.domain.Attribute.getPossibleValues(),
 * ++ public java.util.List com.hubitat.hub.domain.Attribute.getValues(),
 * public java.lang.Long com.hubitat.hub.domain.Attribute.getVersion(),
 *
 * public void com.hubitat.hub.domain.Attribute.setDataType(java.lang.String),
 * public void com.hubitat.hub.domain.Attribute.setId(java.lang.Long),
 * public void com.hubitat.hub.domain.Attribute.setName(java.lang.String),
 * public void com.hubitat.hub.domain.Attribute.setPossibleValues(java.util.List),
 * public void com.hubitat.hub.domain.Attribute.setVersion(java.lang.Long),
 */

/**
 * @see https://docs.smartthings.com/en/latest/ref-docs/attribute-ref.html
 *
 * You will typically interact with Attributes values directly, for example,
 * using the current<Uppercase attribute name> method on a Device instance.
 * That will get the value of the Attribute, which is typically what
 * SmartApps are most interested in.
 *
 * You can get the supported Attributes of a Device through the
 * Device’s getSupportedAttributes() method.
 */
trait Attribute
{
    /**
     * @return the data type of this Attribute.
     * Possible types are “STRING”, “NUMBER”, “VECTOR3”, “ENUM”.
     */
    abstract String getDataType()

    abstract String getName()

    /**
     * @return list of possible values, if data type is "ENUM"
     */
    abstract List<String> getValues()

    abstract me.biocomp.hubitat_ci.api.Attribute fromJson(java.util.Map a) // Original: public static com.hubitat.hub.domain.Attribute com.hubitat.hub.domain.Attribute.fromJson(java.util.Map)
    abstract boolean getCapability() // Original: public boolean com.hubitat.hub.domain.Attribute.getCapability()
    abstract java.lang.Long getDeviceTypeId() // Original: public java.lang.Long com.hubitat.hub.domain.Attribute.getDeviceTypeId()
    abstract java.lang.Long getId() // Original: public java.lang.Long com.hubitat.hub.domain.Attribute.getId()
    abstract java.lang.String getPossibleValueJson() // Original: public java.lang.String com.hubitat.hub.domain.Attribute.getPossibleValueJson()
    abstract java.util.List getPossibleValues() // Original: public java.util.List com.hubitat.hub.domain.Attribute.getPossibleValues()
    abstract java.lang.Long getVersion() // Original: public java.lang.Long com.hubitat.hub.domain.Attribute.getVersion()
    abstract boolean isCapability() // Original: public boolean com.hubitat.hub.domain.Attribute.isCapability()
    abstract void setCapability(boolean a) // Original: public void com.hubitat.hub.domain.Attribute.setCapability(boolean)
    abstract void setDataType(java.lang.String a) // Original: public void com.hubitat.hub.domain.Attribute.setDataType(java.lang.String)
    abstract void setDeviceTypeId(java.lang.Long a) // Original: public void com.hubitat.hub.domain.Attribute.setDeviceTypeId(java.lang.Long)
    abstract void setId(java.lang.Long a) // Original: public void com.hubitat.hub.domain.Attribute.setId(java.lang.Long)
    abstract void setName(java.lang.String a) // Original: public void com.hubitat.hub.domain.Attribute.setName(java.lang.String)
    abstract void setPossibleValues(java.util.List a) // Original: public void com.hubitat.hub.domain.Attribute.setPossibleValues(java.util.List)
    abstract void setPossibleValuesJson(java.lang.String a) // Original: public void com.hubitat.hub.domain.Attribute.setPossibleValuesJson(java.lang.String)
    abstract void setVersion(java.lang.Long a) // Original: public void com.hubitat.hub.domain.Attribute.setVersion(java.lang.Long)
    abstract java.util.Map toMap() // Original: public java.util.Map com.hubitat.hub.domain.Attribute.toMap()
    abstract java.util.Map toMap(boolean a) // Original: public java.util.Map com.hubitat.hub.domain.Attribute.toMap(boolean)
}