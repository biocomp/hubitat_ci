package biocomp.hubitatCi.capabilities

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@CompileStatic
class CapabilityAttributeInfo {
    private static HashSet<String> supportedOptions = ["min", "max"] as HashSet<String>

    static Map<String, CapabilityAttributeInfo> makeList(List<CapabilityAttributeInfo> attributes)
    {
        return attributes.collectEntries { it -> [it.name, it]}
    }

    CapabilityAttributeInfo(String name, Class type, Map options = [:])
    {
        assert name
        assert type

        this.name = name
        this.type = type

        def supportedOptionsClone = (HashSet<String>)supportedOptions.clone()
        def readOption = { String o ->
            assert(supportedOptionsClone.contains(o))

            if (options.containsKey(o))
            {
                supportedOptionsClone.remove(o)
                return options.o
            }

            return null
        }

        min = (Double)readOption("min")
        max = (Double)readOption("max")
    }

    public final String name
    public final Class type
    public final Double min = null
    public final Double max = null
}
