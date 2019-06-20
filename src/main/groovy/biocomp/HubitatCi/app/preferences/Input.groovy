package biocomp.hubitatCi.app.preferences

import biocomp.hubitatCi.app.AppValidator
import biocomp.hubitatCi.capabilities.Capabilities
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

@TupleConstructor
@CompileStatic
class Input {
    final Map unnamedOptions
    final Map options
    final AppValidator validator

    String readName()
    {
        return unnamedOptions.name ? unnamedOptions.name : options?.name
    }

    String readType()
    {
        return unnamedOptions.type ? unnamedOptions.type : options?.type
    }

    @CompileStatic
    static boolean isCapabilityType(String type) {
        return type =~ /capability\.[a-zA-Z0-9._]+/
    }

    @CompileStatic
    static Class findCapabilityFromTypeString(String type)
    {
        assert isCapabilityType(type), "Call this method only if input type points to capability. Current value: '${type}'"
        return (Class)Capabilities.capabilitiesByDeviceSelector.get(type.substring(11))
    }

    @Override
    String toString()
    {
        return "input(options: ${options}, unnamedOptions: ${unnamedOptions})"
    }
}
