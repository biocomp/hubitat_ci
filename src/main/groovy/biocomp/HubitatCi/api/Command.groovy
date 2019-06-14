package biocomp.hubitatCi.api

trait Command
{
    /**
     * @return List of the argument types for this command. One of “STRING”, “NUMBER”, “VECTOR3”, or “ENUM”.
     */
    abstract List<String> getArguments()

    abstract String getName()
}