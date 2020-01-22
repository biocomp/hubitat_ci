package me.biocomp.hubitat_ci.validation

import groovy.transform.TupleConstructor

/**
 * This object is returned when script accesses inputs, but input validation was disabled.
 * There is no way to determine correct object type in this case.
 */
@TupleConstructor
class UnvalidatedInput {
    String name
    String type

    @Override
    String toString() {
        "UnvalidatedInput(name: '${name}', type:'${type}')"
    }
}
