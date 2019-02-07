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

    void addCapability(String name) {
        capabilities.add(name)
    }

    void addAttribute(Attribute attribute) {
        attributes.add(attribute)
    }
}
