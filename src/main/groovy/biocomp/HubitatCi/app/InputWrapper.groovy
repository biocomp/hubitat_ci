package biocomp.hubitatCi.app

import biocomp.hubitatCi.app.preferences.Input
import groovy.transform.TupleConstructor

/**
 * Produced when script reads input object as if it was a data member:
 * 'thermometer'
 *
 * This allows to send it down to subscribe() where it can be validated.
 */
@TupleConstructor
class InputWrapper {

    /**
     * Not for script to touch
     */
    final Input inputDescription_Internal
}
