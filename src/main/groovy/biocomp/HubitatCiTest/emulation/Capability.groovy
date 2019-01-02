package biocomp.hubitatCiTest.emulation

trait Capability
{
    abstract List<Attribute> getAttributes()
    abstract List<Command> getCommands()
    abstract String getName()
}