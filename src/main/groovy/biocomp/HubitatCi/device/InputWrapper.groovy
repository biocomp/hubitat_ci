package biocomp.hubitatCi.device

import biocomp.hubitatCi.device.metadata.DeviceInput
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

/**
 * Wraps DeviceInput object for use within a script
 * (adds all proper properties and method names).
 */
@CompileStatic
@TupleConstructor
class InputWrapper {
    InputWrapper(DeviceInput d) {}
}
