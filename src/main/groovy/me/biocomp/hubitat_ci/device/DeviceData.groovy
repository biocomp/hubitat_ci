package me.biocomp.hubitat_ci.device

import me.biocomp.hubitat_ci.device.metadata.DeviceInput
import me.biocomp.hubitat_ci.validation.IInputSource
import groovy.transform.CompileStatic

/**
 * All the information collected about the device during setup.
 */
@CompileStatic
class DeviceData implements IInputSource {
    final List<DeviceInput> producedPreferences = []

    @Override
    def generateInputWrapper(String name, def userProvidedValue) {
        def input = producedPreferences.find{it->it.readName() == name}
        return input ? input.makeInputObject(userProvidedValue) : userProvidedValue
    }

    @Override
    def generateInputWrapper(String name) {
        def input = producedPreferences.find{it->it.readName() == name}
        return input ? input.makeInputObject() : null
    }

    @Override
    Set<String> getAllInputNames() {
        return producedPreferences.collect{it.readName()} as Set<String>
    }
}
