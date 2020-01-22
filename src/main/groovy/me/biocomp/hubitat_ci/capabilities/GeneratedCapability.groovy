package me.biocomp.hubitat_ci.capabilities

import me.biocomp.hubitat_ci.api.Attribute
import me.biocomp.hubitat_ci.api.Capability
import me.biocomp.hubitat_ci.api.Command

class GeneratedCapability implements Capability {
    GeneratedCapability(Class capability) {
        attributes = Capabilities.readAttributes(capability).values().collect { new GeneratedAttribute(it) }.sort { it.name }
        commands = Capabilities.readMethods(capability).collect { new GeneratedCommand(it) }.sort { it.name }
        name = capability.simpleName
    }

    @Override
    List<Attribute> getAttributes() {
        attributes
    }

    @Override
    List<Command> getCommands() {
        commands
    }

    @Override
    String getName() {
        name
    }

    private final String name
    private final List<Attribute> attributes
    private final List<Command> commands
}
