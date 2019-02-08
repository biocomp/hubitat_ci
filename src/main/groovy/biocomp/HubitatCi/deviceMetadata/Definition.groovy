package biocomp.hubitatCi.deviceMetadata

import groovy.transform.TypeChecked

@TypeChecked
class Definition {
    Definition(Map options)
    {
        this.options = options
    }

    final Map options

    List<String> capabilities = []
    List<Attribute> attributes = []
    List<Command> commands = []

    void addCapability(String name) {
        capabilities.add(name)
    }

    void addAttribute(Attribute attribute) {
        attributes.add(attribute)
    }

    void addCommand(Command command) {
        commands.add(command)
    }
}
