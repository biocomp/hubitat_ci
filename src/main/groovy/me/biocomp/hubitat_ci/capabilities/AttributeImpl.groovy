package me.biocomp.hubitat_ci.capabilities

import me.biocomp.hubitat_ci.api.Attribute
import groovy.transform.CompileStatic

/**
 * @see https://docs.smartthings.com/en/latest/ref-docs/attribute-ref.html
 *
 *
 * You will typically interact with Attributes values directly, for example,
 * using the current<Uppercase attribute name> method on a Device instance.
 * That will get the value of the Attribute, which is typically what SmartApps are most interested in.
 *
 * You can get the supported Attributes of a Device through the Device’s getSupportedAttributes() method.
 */
@CompileStatic
class AttributeImpl implements Attribute {
    private final String dataType_
    private final String name_
    private final List<String> values_

    AttributeImpl(String name, String dataType)
    {
        this.name_ = name
        this.dataType_ = dataType
    }

    @Override
    String getDataType() {
        return dataType_
    }

    @Override
    String getName() {
        return name_
    }

    @Override
    List<String> getValues() {
        return values_
    }
}
