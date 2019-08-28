package me.biocomp.hubitat_ci.util

import groovy.transform.TypeChecked

@TypeChecked
class DoNotCallMeBinding extends Binding
{
    @Override
    def getProperty(String property)
    {
        ThisBindingShouldNotBeUsed("getProperty('${property}')")
    }

    @Override
    def getVariable(String name)
    {
        ThisBindingShouldNotBeUsed("getVariable('${name}')")
    }

    @Override
    java.util.Map getVariables()
    {
        return emptyVariables_
    }

    private final Map emptyVariables_ = [:]

    @Override
    boolean hasVariable(String name)
    {
        ThisBindingShouldNotBeUsed("hasVariable('${name}')")
    }

    @Override
    void setProperty(String property, Object newValue)
    {
        ThisBindingShouldNotBeUsed("setPropety('${property}')")
    }

    @Override
    void setVariable(String name, Object value)
    {
        ThisBindingShouldNotBeUsed("setVariable('${name}')")
    }

    private static ThisBindingShouldNotBeUsed(String name)
    {
        throw new SecurityException("This Binding's methods (${name}) should not be called")
    }
}