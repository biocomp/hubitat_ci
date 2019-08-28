package me.biocomp.hubitat_ci.device.metadata

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
    List<Map> fingerprints = []
    List<DeviceInput> preferences = []

    void addCapability(String name) {
        capabilities.add(name)
    }

    void addAttribute(Attribute attribute) {
        attributes.add(attribute)
    }

    void addCommand(Command command) {
        commands.add(command)
    }

    void addFingerprint(Map fingerprint) {
        fingerprints.add(fingerprint)
    }
}
