package biocomp.hubitatCi.validation

import groovy.transform.TypeChecked

/**
 * Used by {@see SettingsContainer} to get inputs
 */
@TypeChecked
interface IInputSource {
    /**
     * Returns input object in such a way that it could be used in
     * a script when script accesses it (with all correct attributes and methods)
     * @param name
     * @return
     */
    def generateInputWrapper(String name, def userProvidedObject)

    Set<String> getAllInputNames()
}