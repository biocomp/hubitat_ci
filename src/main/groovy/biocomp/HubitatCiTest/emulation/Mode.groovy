package biocomp.hubitatCiTest.emulation

trait Mode
{
    /**
     * @return unique internal identifier
     */
    abstract String getId()

    /**
     * @return user-friendly mode name
     */
    abstract String getName()
}

