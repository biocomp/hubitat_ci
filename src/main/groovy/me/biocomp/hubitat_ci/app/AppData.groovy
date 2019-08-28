package me.biocomp.hubitat_ci.app


import biocomp.hubitatCi.app.preferences.Preferences
import biocomp.hubitatCi.validation.IInputSource
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * All the information collected about the app.
  */
@TypeChecked
class AppData implements IInputSource {
    /**
     * Results of definition() call.
     */
    final Map<String, Object> definitions = [:]

    final Preferences preferences = new Preferences()

    @Override
    @CompileStatic
    def generateInputWrapper(String name, def userProvidedValue) {
        // Linear search + reconstruction of list of inputs too.
        def input = preferences.getAllInputs().find{ it.readName() == name }
        if (input)
        {
            return input.makeInputObject(userProvidedValue)
        }

        return userProvidedValue
    }

    @Override
    @CompileStatic
    Set<String> getAllInputNames() {
        return preferences.getAllInputs().collect{ it.readName() } as Set<String>
    }
}
