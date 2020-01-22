package me.biocomp.hubitat_ci.capabilities

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.Attribute

@CompileStatic
class GeneratedAttribute implements Attribute {
    GeneratedAttribute(CapabilityAttributeInfo info) {
        name = info.name
        dataType = info.typeString
        values = info.values
    }

    @Override
    String getDataType() {
        dataType
    }

    @Override
    String getName() {
        name
    }

    @Override
    List<String> getValues() {
        values
    }

    private final String name
    private final String dataType
    private final List<String> values
}
