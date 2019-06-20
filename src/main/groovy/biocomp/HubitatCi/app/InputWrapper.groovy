package biocomp.hubitatCi.app

import biocomp.hubitatCi.app.preferences.Input

/**
 * Produced when script reads input object as if it was a data member:
 * 'thermometer'
 *
 * This allows to send it down to subscribe() where it can be validated.
 */
class InputWrapper {

    /**
     * Not for script to touch
     */
    final Input inputDescription_Internal
}
