package biocomp.hubitatCi.device

import biocomp.hubitatCi.device.metadata.DeviceInput
import biocomp.hubitatCi.validation.IInputSource
import groovy.transform.CompileStatic

/**
 * All the information collected about the device during setup.
 */
@CompileStatic
class DeviceData implements IInputSource {
    final List<DeviceInput> producedPreferences = []

    @Override
    def findInput(String name) {
        def input = producedPreferences.find{it->it.readName() == name}
        return input ? new InputWrapper(input) : null
    }

    @Override
    Set<String> getAllInputNames() {
        return producedPreferences.collect{it.readName()} as Set<String>
    }
}
