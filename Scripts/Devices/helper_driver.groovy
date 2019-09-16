metadata {
    definition (name: "Driver to check hubitat APIs", namespace: "me.biocomp", author: "biocomp") {
        capability "Actuator"
        capability "Switch"
    }
}

void on() {
    dumpClass(this.class) // Your class here
}

void off() {}

private static void dumpMethods(def cls, List<String> result)
{
    final def skipTheseMethods = [
            "getProperty",
            "setProperty",
            "setMetaClass",
            "getMetaClass",
            "invokeMethod",
            "toString",
            "methodMissing",
            "propertyMissing",
            "getStaticMetaClass"
    ]

    def shouldSkipMethod = { m ->
        skipTheseMethods.any{m.name.endsWith(it)} || m.declaringClass.name == Object.name
    }

    result << "Methods:["

    def makeComparator = { comparators ->
        {
            a, b ->
                for (def cmp in comparators) {
                    final int cmpResult = ((Closure)cmp)(a, b)
                    if (cmpResult != 0) {
                        return cmpResult
                    }
                }

                return 0
        }
    }

    result.addAll(cls.methods.findAll{
        !shouldSkipMethod(it)
    }.sort(makeComparator([
            { a, b -> a.name.compareTo(b.name) },
            { a, b -> (a.parameters.size() <=> b.parameters.size()) },
            { a, b -> a.toString() <=> b.toString() }])
    ).collect{ "  ${it}"})

    result << "]"
}

private static def dumpEnumValues(def cls, List<String> result)
{
    result << "Enum values:["

    final def enumValues = cls.values()
    for (int i = 0; i != enumValues.size(); ++i) {
        if (i + 1 != enumValues.size())
        {
            result << "  ${enumValues[i]},"
        }
        else
        {
            result << "  ${enumValues[i]}"
        }
    }
    result << "]"
}

def dumpClass(def cls)
{
    log.info(dumpClassImpl(cls))
}

private static String dumpClassImpl(def cls)
{
    def result = []
    result << "${cls}:"

    if (cls.isEnum())
    {
        dumpEnumValues(cls, result)
    }
    else
    {
        dumpMethods(cls, result)
    }

    return result.join('\n')
}

def parse(String s)
{
    return [:]
}