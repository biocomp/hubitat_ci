definition(
        name: "hubitat_api_exporter_app.groovy - exports Hubitat APIs",
        namespace: "me.biocomp",
        author: "biocomp",
        description: "Testing",
        iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
        iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
        iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
        oauth: [displayName: "HTML Endpoint", displayLink: "https://sharptools.io"])

preferences() {
    page(name: "setupScreen")
}

@groovy.transform.Field static Object capturedEvent = null
@groovy.transform.Field static Object capturedHubResponse = null
@groovy.transform.Field static Object lock = new Object()

private def updateImpl() {
    unsubscribe()
    initialize()
}

def installed() {
    initialize()
}

def updated() {
    updateImpl()
}

def uninstalled() {
    unsubscribe()
}

def initialize() {
    def driver = getChildDriver()
    subscribe(driver, "switch.on", onHandler)

    log.debug "Trying to turn device off and on..."
    driver.off()
    driver.on()
    driver.off()
    log.debug "Done turning device off and on, capturedEvent = ${capturedEvent}"

    log.debug "Trying to capture hub response by sending hub command..."

    sendHubCommand(
            new hubitat.device.HubAction(
                    """GET ${makeUri("/")} HTTP/1.1\r\nHOST: 1.1.1.1\r\n\r\n""",
                    hubitat.device.Protocol.LAN,
                    null,
                    [ callback: onHubResponse ]
            ))

    while (true)
    {
        synchronized(lock) {
            if (capturedHubResponse != null) { break }
        }
    }

    log.debug "Done capturing hub response: ${capturedHubResponse}"

    return driver
}

void onHubResponse(hubitat.device.HubResponse response)
{
    synchronized(lock) {
        if (capturedHubResponse == null) {
            capturedHubResponse = response
        }
    }

    log.debug "After 'onHubResponse(${response})', capturedHubResponse = ${capturedHubResponse}"
}

def onHandler(def event) {
    if (capturedEvent == null) {
        capturedEvent = event
    }
    log.debug "After 'onHandler(${event})', capturedEvent = ${capturedEvent}"
}

private def makeUri(String extraPath) {
    return getFullLocalApiServerUrl() + extraPath + "?access_token=${state.accessToken}"
}

def setupScreen() {
    if (!state.accessToken) {
        // Enable OAuth in the app settings or this call will fail
        createAccessToken()
    }

    String uri = makeUri("/");
    return dynamicPage(name: "setupScreen", uninstall: true, install: true) {
        section(){
            paragraph("Use the following URI to access the page: <a href='${uri}'>${uri}</a>")
            input "export_devices", "capability.actuator", description: "Specify one Export helper device", multiple: false
        }
    }
}

mappings {
    // The root path - you can also map other paths or use parameters in paths and posted data
    path("/") { action: [GET: "mainPage"]}
    path("/dump_classes") { action: [GET: "dumpClasses" ]}
}

def mainPage() {
    log.info "Rendering main page"
    String uri = makeUri("/nested_page");
    html = "<html><head><title>Http server - main page</title></head><body>Use <a href='${uri}'>${uri}</a> to get to nested page</body></html>"
    render contentType: "text/html", data: html, status: 200
}

def dumpClasses() {
    render contentType: "application/json", data: dumpClassesImpl(), status: 200
}

/**
 *
 * @param cls
 * @return list of metadata about each method
 */
private def readClassMethods(def cls, def castType) {
    final def skipTheseMethods = [
            "getProperty",
            "setProperty",
            "setMetaClass",
            "getMetaClass",
            "invokeMethod",
            "toString",
            "methodMissing",
            "propertyMissing",
            "getStaticMetaClass",
            "clone",
            "hashCode",
            "canEqual",
            "equals"
    ]

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

    def ignoreMethod = {
        m ->
            skipTheseMethods.any{m.name.endsWith(it)} || m.declaringClass.name == Object.name || m.name.startsWith("_") || m.name.contains('$')
    }

    def constructors = cls.declaredConstructors.sort{it.parameters.size()}.collect{
        [
                "returnType": castType(cls),
                "name": cls.simpleName,
                "parameters": it.parameterTypes.collect{castType(it)},
                "modifiers": ["constructor"],
                "as_string": it.toString(),
                "self": it
        ]
    }

    def otherMethods = cls.methods.findAll{!ignoreMethod(it)}
            .sort(makeComparator([
                    { a, b -> a.name.compareTo(b.name) },
                    { a, b -> (a.parameters.size() <=> b.parameters.size()) },
                    { a, b -> a.toString() <=> b.toString() }])
            ).collect{
        def modifiers = []
        if (it.toString().contains(" static ")) {
            modifiers << "static"
        }

        [
                "returnType": castType(it.returnType),
                "name": it.name,
                "parameters": it.parameterTypes.collect{castType(it)},
                "modifiers": modifiers,
                "as_string": it.toString(),
                "self": it
        ]
    }

    return constructors + otherMethods
}

// castClass - conversion which is applied whenever type is being added to return value.
// Usually is {it.name} to use class name instead of full class object, but could be just {it} to return full class.
private def dumpMethods(def obj, def cls, def objAndClassesToDump, def castClass)
{
    final def ignoreFoundType = {
        c -> return (
                c.name == "void"
                        || c == cls
                        || c.isPrimitive()
                        || c.name.startsWith("java")
                        || c.name.startsWith("groovy")
                        || c.name.startsWith("org.apache")
                        || c.name.startsWith("org.codehaus")
                        || c.name.startsWith("sun.")
                        || c.name.startsWith("su.litvak"))
    }

    final def reportObjWithType = { newObj, newCls ->
        if (newCls.isArray()) {
            if (newObj != null) {
                newObj = newObj[0]
            }
            newCls = newCls.componentType
        }

        if (ignoreFoundType(newCls)) {
            return
        }

        boolean classAlreadyReported = newCls.name in objAndClassesToDump
        if (!classAlreadyReported || objAndClassesToDump[newCls.name].obj == null) {
            objAndClassesToDump[newCls.name] = [obj: newObj, cls: newCls]
        }
    }

    if (!obj) {
        try {
            obj = cls.newInstance()
        } catch (Exception)
        {}
    }

    List exportableMethods = readClassMethods(cls, {it})
    def result = [
            class_name: cls.name,
            methods: exportableMethods.collect{
                if (!ignoreFoundType(it.returnType)) {
                    reportObjWithType(null, it.returnType)
                }

                it.parameters.findAll{!ignoreFoundType(it)}.each{reportObjWithType(null, it)}

                if (it.parameters.size() == 0
                        && !it.name.startsWith("dumpClasses")
                        && !it.name.toLowerCase().contains("reset")
                        && !it.name.toLowerCase().contains("revoke")
                        && !it.name.toLowerCase().contains("erase")
                        && !it.name.toLowerCase().contains("clear")
                        && !it.name.toLowerCase().contains("init")
                        && !it.name.toLowerCase().contains("swap")
                        && !it.name.startsWith("set")
                        && !("constructor" in it.modifiers)) {
                    try {
                        def resultObject = it.self.invoke(obj)

                        if (resultObject != null) {
                            def resultClass = null

                            // If it's a class, use it directly
                            try {
                                if (resultObject.methods != null) {
                                    resultClass = resultObject
                                    resultObject = null
                                }
                            }
                            catch (Exception) {
                            }

                            // If it's a list, use first element's class
                            if (resultObject instanceof List) {
                                try {
                                    resultObject = resultObject[0]
                                }
                                catch (Exception) {
                                }
                            }

                            // If it's a map, use first element's value
                            if (resultObject instanceof Map) {
                                try {
                                    resultObject = resultObject.values()[0]
                                }
                                catch (Exception) {
                                }
                            }

                            if (resultClass == null) {
                                resultClass = resultObject.class
                            }

                            reportObjWithType(resultObject, resultClass)
                        }
                    }
                    catch (Exception e) {
                        log.debug "Could not call method ${it.name} - ${e}"
                    }
                }

                return [
                        "returnType": castClass(it.returnType),
                        "name": it.name,
                        "parameters": it.parameters.collect{castClass(it)},
                        "modifiers": it.modifiers,
                        "as_string": it.as_string
                ]
            }
    ]

    return result
}

private Map dumpEnumValues(def cls)
{
    Map result = [:]

    result.enum_name = cls.name
    result.enum_values = cls.values()

    return result
}

private def dumpClassImpl(def obj, def cls, def classesToDump, def castClass = {it})
{
    if (cls.isEnum())
    {
        return dumpEnumValues(cls)
    }
    else
    {
        return dumpMethods(obj, cls, classesToDump, castClass)
    }
}

def getChildDriver() {
    def childDevice = getAllChildDevices()?.find {it.device.deviceNetworkId == "Device_exporter_${app.id}"}
    log.debug "${childDevice}"
    if (!childDevice) {
        // Device code is in hubitat_api_exporter_driver.groovy
        childDevice = addChildDevice(
                "me.biocomp",
                "hubitat_api_exporter_driver.groovy - use together with hubitat_api_exporter_app.groovy",
                "Device_exporter_${app.id}",
                null,
                [completedSetup: true, label: app.label])

        log.info "Created child device [${childDevice}]"
    }

    return childDevice
}

private String dumpClassesImpl()
{
    def childDriver = updateImpl() // To try and capture the event

    def classesToDump = childDriver.getExportedClasses().collect{[obj:null, cls:it]};
    classesToDump << [obj:this, cls:this.class]

    def capability = childDriver.capabilities[0]
    classesToDump << [obj:capability, cls:capability.class]

    try {
        def subscription = app.subscriptions[0]
        classesToDump << [obj: subscription, cls: subscription.class]
    } catch (Exception e) {
    }

    if (capturedEvent != null) {
        log.debug "capturedEvent not null"
        classesToDump << [obj: capturedEvent, cls: capturedEvent.class]
    } else {
        log.debug "capturedEvent is null"
    }

    if (capturedHubResponse != null) {
        log.debug "capturedHubResponse not null"
        classesToDump << [obj: capturedHubResponse, cls: capturedHubResponse.class]
    } else {
        log.debug "capturedHubResponse is null"
    }

    log.debug "Starting dumping with these classes: ${classesToDump}"

    def dumpedClasses = [] as HashSet
    def classesYetToDump = classesToDump.collectEntries{[it.cls.name, it]}

    def formatDumpedClassesKey = {
        def key = it.cls.name;
        if (it.obj) {
            key = key + "_with_obj"
        }
        return key
    }

    def classes = []
    while (!classesYetToDump.isEmpty()) {
        def currentClassIt = classesYetToDump.iterator();
        def currentObjAndClass = currentClassIt.next();

        currentClassIt.remove();

        String currentKey = formatDumpedClassesKey(currentObjAndClass.value)
        if (!(currentKey in dumpedClasses)) {
            classes << dumpClassImpl(currentObjAndClass.value.obj, currentObjAndClass.value.cls, classesYetToDump, {it.name})
            dumpedClasses.add(currentKey)
        }
    }

    return groovy.json.JsonOutput.toJson([firmware_version: location.hubs[0].firmwareVersionString, classes: classes])
}