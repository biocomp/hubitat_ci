package biocomp.hubitatCi.deviceMetadata

import groovy.transform.TypeChecked

@TypeChecked
class Definition {
    Definition(Map options)
    {
        this.options = options
    }

    final Map options

    List capabilities = []

    void addCapability(String name) {
        capabilities.add(name)
    }
}
