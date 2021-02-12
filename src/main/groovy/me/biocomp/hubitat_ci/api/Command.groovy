package me.biocomp.hubitat_ci.api

trait Command {
    /**
     * @return List of the argument types for this command. One of “STRING”, “NUMBER”, “VECTOR3”, or “ENUM”.
     */
    abstract List<String> getArguments()

    abstract String getName()

    abstract me.biocomp.hubitat_ci.api.Command customVersion(me.biocomp.hubitat_ci.api.Command a, me.biocomp.hubitat_ci.api.Command b) // Original: public static com.hubitat.hub.domain.Command com.hubitat.hub.domain.Command.customVersion(com.hubitat.hub.domain.Command,com.hubitat.hub.domain.Command)
    abstract me.biocomp.hubitat_ci.api.Command fromJson(java.util.Map a) // Original: public static com.hubitat.hub.domain.Command com.hubitat.hub.domain.Command.fromJson(java.util.Map)
    abstract boolean getCapability() // Original: public boolean com.hubitat.hub.domain.Command.getCapability()
    abstract java.lang.Long getId() // Original: public java.lang.Long com.hubitat.hub.domain.Command.getId()
    abstract java.util.List getParameters() // Original: public java.util.List com.hubitat.hub.domain.Command.getParameters()
    abstract java.lang.Long getVersion() // Original: public java.lang.Long com.hubitat.hub.domain.Command.getVersion()
    abstract boolean isCapability() // Original: public boolean com.hubitat.hub.domain.Command.isCapability()
    abstract me.biocomp.hubitat_ci.api.Command merge(me.biocomp.hubitat_ci.api.Command a, me.biocomp.hubitat_ci.api.Command b) // Original: public static com.hubitat.hub.domain.Command com.hubitat.hub.domain.Command.merge(com.hubitat.hub.domain.Command,com.hubitat.hub.domain.Command)
    abstract void setArguments(java.util.List a) // Original: public void com.hubitat.hub.domain.Command.setArguments(java.util.List)
    abstract void setCapability(boolean a) // Original: public void com.hubitat.hub.domain.Command.setCapability(boolean)
    abstract void setId(java.lang.Long a) // Original: public void com.hubitat.hub.domain.Command.setId(java.lang.Long)
    abstract void setName(java.lang.String a) // Original: public void com.hubitat.hub.domain.Command.setName(java.lang.String)
    abstract void setParameters(java.util.List a) // Original: public void com.hubitat.hub.domain.Command.setParameters(java.util.List)
    abstract void setVersion(java.lang.Long a) // Original: public void com.hubitat.hub.domain.Command.setVersion(java.lang.Long)
    abstract java.util.Map toMap() // Original: public java.util.Map com.hubitat.hub.domain.Command.toMap()
    abstract java.util.Map toMap(boolean a) // Original: public java.util.Map com.hubitat.hub.domain.Command.toMap(boolean)
}