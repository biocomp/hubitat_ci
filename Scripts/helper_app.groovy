definition(
        name: "Helper app",
        namespace: "me.biocomp",
        author: "biocomp",
        description: "Testing",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
        iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png"
)
preferences() {
    section("Update input to dump classes"){
        input "updateMe", "text", title: "Update me", required:true, defaultValue: "One"
    }
}

private def update()
{
    dumpClass(this.class)
}

def installed()
{
    update()
}

def updated()
{
    update()
}

private static void dumpMethods(def cls, List<String> result)
{
    final Set<String> skipTheseMethods = [
            "getProperty",
            "setProperty",
            "setMetaClass",
            "getMetaClass",
            "invokeMethod"
    ] as Set

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
        return !skipTheseMethods.contains(it.name) && it.declaringClass.name != Object.name
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
