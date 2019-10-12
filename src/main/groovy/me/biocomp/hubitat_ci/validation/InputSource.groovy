package me.biocomp.hubitat_ci.validation

import groovy.transform.TypeChecked

/**
 * Used by {@see SettingsContainer} to get inputs.
 */
@TypeChecked
interface IInputSource {
    /**
     * Returns input object in such a way that it could be used in
     * a script when script accesses it (with all correct attributes and methods)
     * @param name
     * @param userProvidedValue - user can provide a mock object for this input.
     * @return
     */
    def generateInputWrapper(String name, def userProvidedValue)

    def generateInputWrapper(String name)

    Set<String> getAllInputNames()
}