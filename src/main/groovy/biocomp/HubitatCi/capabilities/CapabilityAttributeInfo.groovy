package biocomp.hubitatCi.capabilities

class CapabilityAttributeInfo {
    private static Map supportedOptions = ["min", "max"]
    CapabilityAttributeInfo(String name, Class type, Map options)
    {
        assert name
        assert type

        this.name = name
        this.type = type

        def supportedOptionsClone = (Map)supportedOptions.clone()
        def readOption = { String o ->
            assert(supportedOptionsClone.containsKey(o))

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

    private String name
    private Class type
    private Double min = null
    private Double max = null
}
