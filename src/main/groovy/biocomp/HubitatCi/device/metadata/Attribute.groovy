package me.biocomp.hubitat_ci.device.metadata

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

/**
 * Information about 'attribute' in device.
 */
@TypeChecked
@TupleConstructor
class Attribute {
    String name
    String type
    List<String> possibleValues

    @Override
    String toString()
    {
        if (possibleValues != null) {
            return "attribute('${name}', '${type}', ${possibleValues})"
        }
        else {
            return "attribute('${name}', '${type}')"
        }
    }
}
