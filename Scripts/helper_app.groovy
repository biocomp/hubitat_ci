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
    dumpClass(this.class) // Your class here
}

def installed()
{
    update()
}

def updated()
{
    update()
}

private void dumpMethods(def cls, List<String> result)
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

    result << "Methods:["

    // TODO: Can be replaced with OrderBy class:
    // https://mrhaki.blogspot.com/2009/12/groovy-goodness-using-orderby.html
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

    result.addAll(cls.methods.findAll{ m ->
        !skipTheseMethods.any{m.name.endsWith(it)} && m.declaringClass.name != Object.name
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

private String dumpClassImpl(def cls)
{
    def result = []

    result << "Platform version: ${location.hubs[0].firmwareVersionString}"
    result << ""
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
