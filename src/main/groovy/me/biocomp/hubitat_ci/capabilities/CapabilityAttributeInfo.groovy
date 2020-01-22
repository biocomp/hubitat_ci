package me.biocomp.hubitat_ci.capabilities

import groovy.transform.CompileStatic

@CompileStatic
class CapabilityAttributeList
{
    HashMap<String, CapabilityAttributeInfo> result = new HashMap<String, CapabilityAttributeInfo>()

    void jsonObject(String name)
    {
        result.put(name, new CapabilityAttributeInfo(name, "JSON_OBJECT"))
    }

    void number(Map options = [:], String name)
    {
        result.put(name, new CapabilityAttributeInfo(options, name, "NUMBER"))
    }

    void stringAttribute(String name)
    {
        result.put(name, new CapabilityAttributeInfo(name, "STRING"))
    }

    void enumAttribute(String name, List<String> values)
    {
        result.put(name, new CapabilityAttributeInfo([:], name, "ENUM", values))
    }

    void date(String name) {
        result.put(name, new CapabilityAttributeInfo(name, "DATE"))
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

    private static final HashSet<String> validTypeStrings = [
            "STRING",
            "NUMBER",
            "VECTOR3",
            "ENUM",
            "DYNAMIC_ENUM",
            "COLOR_MAP",
            "JSON_OBJECT",
            "DATE",
            "BOOLEAN"
    ] as HashSet<String>

    @CompileStatic
    CapabilityAttributeInfo(Map options = [:], String name, String typeString, List<String> values = null)
    {
        assert name
        assert typeString

        this.name = name
        this.typeString = typeString
        this.values = values

        assert validTypeStrings.contains(this.typeString): "Unsupported type string ${this.typeString}."
        if (typeString == "ENUM")
        {
            assert values
            assert values.unique().size() == values.size(): "Attribute ${name} enum values have duplicates: ${values}"
        }

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
    public final String typeString
    public final Double min = null
    public final Double max = null
    public final List<String> values
}
