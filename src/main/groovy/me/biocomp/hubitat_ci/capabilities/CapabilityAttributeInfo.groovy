package me.biocomp.hubitat_ci.capabilities

import groovy.transform.CompileStatic

@CompileStatic
class CapabilityAttributeList
{
    HashMap<String, CapabilityAttributeInfo> result = new HashMap<String, CapabilityAttributeInfo>()

    void attribute(Map options = [:], String name, Class type)
    {
        result.put(name, new CapabilityAttributeInfo(options, name, type))
    }

    void attribute(Map options = [:], String name, String typeString, Class type)
    {
        result.put(name, new CapabilityAttributeInfo(options, name, typeString, type))
    }
}

@CompileStatic
class CapabilityAttributeInfo {
    private static HashSet<String> supportedOptions = ["min", "max"] as HashSet<String>

    static Map<String, CapabilityAttributeInfo> makeList(List<CapabilityAttributeInfo> attributes)
    {
        return attributes.collectEntries { it -> [it.name, it]}
    }

    static Map<String, CapabilityAttributeInfo> makeList(
            @DelegatesTo(CapabilityAttributeList) Closure add)
    {
        def list = new CapabilityAttributeList()

        list.with(add)

        return list.result
    }

    private static final HashMap<Class, String> typeToString = [
            (Number): "NUMBER",
            (String): "STRING",
            (GString): "STRING",
            (Object): "OBJECT",
            (Double): "NUMBER"
    ] as HashMap

    @CompileStatic
    CapabilityAttributeInfo(Map options = [:], String name, String typeString, Class type)
    {
        assert name
        assert type
        assert typeString

        this.name = name
        this.type = type
        this.typeString = typeString

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

    /**
     * This constructor infers type string from the 'type' parameter.
     * @param name
     * @param type
     * @param options
     */
    @CompileStatic
    CapabilityAttributeInfo(Map options = [:], String name, Class type)
    {
        this(options, name, inferTypeString(type), type)
    }

    static String inferTypeString(Class from)
    {
        assert from

        if (from.isEnum())
        {
            return "ENUM"
        }
        else
        {
            String typeString = typeToString.get(from)
            assert typeString: "Type ${from} is not supported or doesn't have mapping - provide it explicitly"
            return typeString
        }
    }

    public final String name
    public final Class type
    public final String typeString
    public final Double min = null
    public final Double max = null
}
