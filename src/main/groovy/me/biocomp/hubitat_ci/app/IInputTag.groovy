package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.app.preferences.Input

/**
 * Is added to the generated input objects that are injected in user's script.
 * This way it's easy to identify such an object when validating subsciptions.
 */
interface IInputTag {
    Input _hubitat_ci_internal_getInput()
}
