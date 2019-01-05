package biocomp.hubitatCiTest

class DoNotCallMeBinding extends Binding
{
    @Override
    def getProperty(String property)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    def getVariable(String name)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    java.util.Map getVariables()
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    boolean hasVariable(String name)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    void setProperty(String property, Object newValue)
    {
        ThisBindingShouldNotBeUsed()
    }

    @Override
    void setVariable(String name, Object value)
    {
        ThisBindingShouldNotBeUsed()
    }

    private static ThisBindingShouldNotBeUsed()
    {
        throw new SecurityException("This Binding's methods should not be called")
    }
}