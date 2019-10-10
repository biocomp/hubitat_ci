package me.biocomp.hubitat_ci

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.app.preferences.Input
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.validation.Flags
import org.codehaus.groovy.control.MultipleCompilationErrorsException
import spock.lang.Specification
import spock.lang.Unroll

import static me.biocomp.hubitat_ci.app.preferences.PreferencesValidationCommon.parseOneChild
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.combinations

/*
*  Documented restrictions:
*
* Can't use these methods:
    addShutdownHook()
    execute()
    getClass()
    getMetaClass()
    setMetaClass()
    propertyMissing()
    methodMissing()
    invokeMethod()
    mixin()
    print()
    printf()
    println()
    sleep()


    You cannot create your own threads.
    You cannot use System methods, like System.out()
    You cannot create or access files.
    You cannot define closures outside of a method. Something like def squareItClosure = {it * it} is not allowed at the top-level, outside of a method body.

    Classes whitelist:

    ArrayList
    BigDecimal
    BigInteger
    Boolean
    Byte
    ByteArrayInputStream
    ByteArrayOutputStream
    Calendar
    Closure
    Collection
    Collections
    Date
    DecimalFormat
    Double
    Float
    GregorianCalendar
    HashMap
    HashMap.Entry
    HashMap.KeyIterator
    HashMap.KeySet
    HashMap.Values
    HashSet
    Integer
    JsonBuilder
    LinkedHashMap
    LinkedHashMap.Entry
    LinkedHashSet
    LinkedList
    List
    Long
    Map
    MarkupBuilder
    Math
    Random
    Set
    Short
    SimpleDateFormat
    String
    StringBuilder
    StringReader
    StringWriter
    SubList
    TimeCategory
    TimeZone
    TreeMap
    TreeMap.Entry
    TreeMap.KeySet
    TreeMap.Values
    TreeSet
    URLDecoder
    URLEncoder
    UUID
    XPath
    XPathConstants
    XPathExpressionImpl
    XPathFactory
    XPathFactoryImpl
    XPathImpl
    ZoneInfo
    com.amazonaws.services.s3.model.S3Object
    com.amazonaws.services.s3.model.S3ObjectInputStream
    com.sun.org.apache.xerces.internal.dom.DocumentImpl
    com.sun.org.apache.xerces.internal.dom.ElementImpl
    groovy.json.JsonOutput
    groovy.json.JsonSlurper
    groovy.util.Node
    groovy.util.NodeList
    groovy.util.XmlParser
    groovy.util.XmlSlurper
    groovy.xml.XmlUtil
    java.net.URI
    java.util.RandomAccessSubList
    org.apache.commons.codec.binary.Base64
    org.apache.xerces.dom.DocumentImpl
    org.apache.xerces.dom.ElementImpl
    org.codehaus.groovy.runtime.EncodingGroovyMethods
    org.json.JSONArray
    org.json.JSONException
    org.json.JSONObject
    org.json.JSONObject.Null
 */


class AppAndDeviceSandboxTest extends
        Specification
{
    /**
     * Add HubitatAppSandbox and HubitatDeviceSandbox permutations*/
    List<List> combineWithSandboxes(def inputs) {
        combinations([inputs, [HubitatAppSandbox, HubitatDeviceSandbox]]).collect{ it.flatten() } as List<List>
    }

    @CompileStatic
    static def parseInput(Class sandboxClass, String type, String extraOptions) {
        if (extraOptions) {
            extraOptions = ", " + extraOptions
        }

        if (sandboxClass == HubitatAppSandbox) {
            final def scriptText = """
preferences{
    page("name", "title", install: true){
        section("sec"){
            input 'myInput', '${type}' ${extraOptions} 
        }
    }
}"""
            return new HubitatAppSandbox(scriptText).run(validationFlags: [Flags.DontValidateDefinition]).producedPreferences.pages[0].sections[0].children[0]

        } else {
            final def scriptText = """
metadata {
    preferences() {
         input 'myInput', '${type}' ${extraOptions}
    }
}"""
            return new HubitatDeviceSandbox(scriptText).run(validationFlags: [Flags.DontValidateDefinition, Flags.DontRequireParseMethodInDevice]).producedPreferences[0]
        }
    }

    @CompileStatic
    static def parseInput(Class sandboxClass, String type, Map extraOptions) {
        return parseInput(sandboxClass, type, extraOptions.collect { k, v -> "${k.inspect()}: ${v.inspect()}" }.join(','))
    }

    @CompileStatic
    static def parseInput(Class sandboxClass, String type) {
        return parseInput(sandboxClass, type, "")
    }

    List<List<String>> makeScriptVariations(List<List<String>> expressionsAndResults) {
        def result = []

        combineWithSandboxes(expressionsAndResults).each {
            result << it
            result << ["""
def foo() {
    ${it[0]}
}
""", it[1], it[2]]
        }

        result
    }

    @Unroll
    def "Expression \"#script\" is not allowed and should fail to compile for #sandboxClass.simpleName"(
            String script, String expectedErrorPart, Class sandboxClass)
    {
        when:
            def sandbox = sandboxClass.newInstance(script)

            // Not running the script, compilation should still fail.
            sandbox.compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains(expectedErrorPart)

        where:
            [script, expectedErrorPart, sandboxClass] << makeScriptVariations([["println 'a'", "println"],
                                                                               ["print 'a'", "print"],
                                                                               ["[].execute()", "execute"],
                                                                               ["String s\ns.getClass()", "getClass"],
                                                                               ["String s\ns.getMetaClass()", "getMetaClass"],
                                                                               ["String s\ns.setMetaClass(null)", "setMetaClass"],
                                                                               ["String s\ns.propertyMissing()", "propertyMissing"],
                                                                               ["String s\ns.methodMissing()", "methodMissing"],
                                                                               ["String s\ns.invokeMethod('a')", "invokeMethod"],
                                                                               ["getProducedPreferences()", "getProducedPreferences"],
                                                                               ["void foo() { def prefs = producedPreferences }", "producedPreferences"],
                                                                               ["getProducedDefinition()", "getProducedDefinition"],
                                                                               ["void foo() { def prefs = producedDefinition }", "producedDefinition"],
                                                                               ["printf", "printf"],
                                                                               ["sleep 10", "sleep"]])
    }

    @Unroll
    def "#description not allowed and should fail to compile for #sandboxClass.simpleName"(
            String script, String expectedErrorPart, String description, Class sandboxClass)
    {
        when:
            sandboxClass.newInstance(script).compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains(expectedErrorPart)

        where:
            [script, expectedErrorPart, description, sandboxClass] << combineWithSandboxes(
                    [["class MyShinyNewClass{}", "MyShinyNewClass", "Defining a new class"],
                     ["System.out.print 'Boom!'", "System.out", "Calling System.out"],
                     ["File.createNewFile('foo.txt')", "File", "Creating a File"]])
    }

    @Unroll
    def "Local variable with no 'def' or type is not confused with property for #sandboxClass.simpleName"(
            Class sandboxClass, Class executorClass)
    {
        given:
            def log = Mock(Log)
            def api = Mock(executorClass)

        when:
            def script = new HubitatAppSandbox("""
int loginCheck() {
    return 42
}

def foo() {
    LoginCheck = loginCheck()
    if (LoginCheck) { log.debug '1' }
    else { log.debug '2' }
} 
""").run(validationFlags: [Flags.DontValidateMetadata, Flags.DontValidatePreferences, Flags.DontValidateDefinition,
                           Flags.DontRequireParseMethodInDevice])

        then:
            _ * api.getLog() >> log

        where:
            sandboxClass         | executorClass
            HubitatAppSandbox    | AppExecutor
            HubitatDeviceSandbox | DeviceExecutor
    }

    private def makeScriptForPrivateCheck(def fileOrText, Class sandboxClass) {
        return sandboxClass.newInstance(fileOrText).compile(
                validationFlags: [Flags.DontValidateMetadata, Flags.DontRequireParseMethodInDevice],
                customizeScriptBeforeRun: { script ->
                    script.getMetaClass().myPrivateMethod1 = { -> "was overridden1!" }
                    script.getMetaClass().myPrivateMethod2 = {
                        def arg1, def arg2 -> "was overridden2(${arg1}, ${arg2})!"
                    }
                })
    }

    void verifyMethodsWereOverridden(Script script) {
        assert script.myPrivateMethod1() == "was overridden1!"
        assert script.publicMethodThatCallsPrivateMethod1() == "was overridden1!"
        assert script.myPrivateMethod2(42, "abc") == "was overridden2(42, abc)!"
        assert script.publicMethodThatCallsPrivateMethod2() == "was overridden2(123, argFromPublicMethod)!"
    }

    @Unroll
    def "private methods in the Script (as text) can be mocked for #sandboxClass.simpleName"(Class sandboxClass) {
        setup:
            def script = makeScriptForPrivateCheck(new File("Scripts/ScriptWithPrivateMethod.groovy"), sandboxClass)

        expect:
            verifyMethodsWereOverridden(script)

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "private methods in the Script (as File) can be mocked for #sandboxClass.simpleName"(Class sandboxClass) {
        setup:
            def script = makeScriptForPrivateCheck(
                    new File("Scripts/ScriptWithPrivateMethod.groovy").readLines().join('\n'), sandboxClass)

        expect:
            verifyMethodsWereOverridden(script)

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "can override settings with userSettingValues for #sandboxClass.simpleName"(Class sandboxClass) {
        setup:
            def script = null
            if (sandboxClass == HubitatAppSandbox) {
                script = """
preferences {
    page(name:"mainPage", title:"Settings", install: true, uninstall: true) {
        section() {
            input (name:"testText", type: "text", title: "Test text", required: true, multiple: false)
        }
    }
}
"""
            } else {
                script = """
metadata {
    preferences {
        input (name:"testText", type: "text", title: "Test text", required: true)
    }
}
"""
            }
            final List<Flags> flags = [Flags.DontValidateDefinition, Flags.DontValidatePreferences,
                                       Flags.DontRequireParseMethodInDevice]
            String value = null
        when: 'Setting isn\'t overridden'
            def sandbox = sandboxClass.newInstance(script).run(userSettingValues: [:], validationFlags: flags)
            value = sandboxClass == HubitatAppSandbox ? sandbox.settings.testText : sandbox.testText
        then:
            value == null
        when: 'Setting is set to something'
            sandbox = sandboxClass.newInstance(script).run(userSettingValues: ['testText': 'My test text'],
                    validationFlags: flags)
            value = sandboxClass == HubitatAppSandbox ? sandbox.settings.testText : sandbox.testText
        then:
            value == 'My test text'
        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "#sandboxClass.simpleName: Getting its own method as closure is not confused with input reading and produces a string"(
            Class sandboxClass, Class executorClass)
    {
        setup:
            final def api = Mock(executorClass)
            final def scriptText = """
int myMethod1()
{
    return 42
}

int myMethod1(int val)
{
    return val
}

String myMethod2()
{
    return "Some string"
}

private static String myStaticPrivateMethod3()
{
    return "String from static method3"
}

def takesMethod(def method)
{
    return method
}

def getMethod1()
{
    return takesMethod(myMethod1)
}

def getMethod2()
{
    return takesMethod(myMethod2)
}

def getMethod3()
{
    return takesMethod(myStaticPrivateMethod3)
}

void scheduleUnschedule()
{
    schedule("cron string", myStaticPrivateMethod3)
    unschedule(myMethod1)
    unschedule(myMethod2)
}
"""

            final List<Flags> flags = [Flags.DontValidateMetadata, Flags.DontValidateDefinition, Flags.DontValidatePreferences,
                                       Flags.DontRequireParseMethodInDevice]

        when:
            def script = sandboxClass.newInstance(scriptText).run(validationFlags: flags, api: api)
            script.scheduleUnschedule()

        then:
            script.getMethod1() == "myMethod1"
            script.getMethod2() == "myMethod2"
            script.getMethod3() == "myStaticPrivateMethod3"

            1 * api.unschedule("myMethod1")
            1 * api.unschedule("myMethod2")
            1 * api.schedule("cron string", "myStaticPrivateMethod3")

        where:
            sandboxClass         | executorClass
            HubitatAppSandbox    | AppExecutor
            HubitatDeviceSandbox | DeviceExecutor
    }

    @Unroll
    def "#sandboxClass.simpleName: Getting or setting its own property is not confused with input reading"(
            Class sandboxClass, Class executorClass)
    {
        setup:
            final def scriptText = """
def getMyProp() 
{
    return state.myStateProp
}

void setMyProp(def val) 
{
    state.myStateProp = val
}

String getMyProp2() 
{
    return state.myStateProp2
}

void setMyProp2(String val) 
{
    state.myStateProp2 = val
}

List<String> readProperties()
{
    return [myProp, myProp2]
}

void writeProperties(String val1, String val2)
{
    myProp = val1
    myProp2 = val2
}
"""

            final List<Flags> flags = [Flags.DontValidateMetadata, Flags.DontValidateDefinition, Flags.DontValidatePreferences,
                                       Flags.DontRequireParseMethodInDevice]
            def state = [myStateProp: "Prop initial value", myStateProp2: "Prop initial value2"]
            def api = Mock(executorClass) {
                _ * getState() >> state
            }

        when:
            def script = sandboxClass.newInstance(scriptText).run(validationFlags: flags, api: api)

        then:
            script.readProperties() == ["Prop initial value", "Prop initial value2"]
            script.writeProperties("Updated value", "Updated value2")
            script.readProperties() == ["Updated value", "Updated value2"]

        where:
            sandboxClass         | executorClass
            HubitatAppSandbox    | AppExecutor
            HubitatDeviceSandbox | DeviceExecutor
    }

    @Unroll
    def "#sandboxClass.simpleName: Can override properties via metaclass"(Class sandboxClass)
    {
        setup:
            final def scriptText = """
def readProperty() 
{
    return myOverridenProperty
}
"""

            final List<Flags> flags = [Flags.DontValidateMetadata, Flags.DontValidateDefinition, Flags.DontValidatePreferences,
                                       Flags.DontRequireParseMethodInDevice]

        when:
            def script = sandboxClass.newInstance(scriptText).run(
                    validationFlags: flags,
                    customizeScriptBeforeRun: { script ->
                        script.getMetaClass().myOverridenProperty = "Overriden value"
            })

        then:
            script.readProperty() == "Overriden value"

        where:
            sandboxClass         | _
            HubitatAppSandbox    | _
            HubitatDeviceSandbox | _

    @Unroll
    def "Invalid input(name, type) types fail #typeAndInputType"() {
        when:
            def type = typeAndInputType[0]
            def input = typeAndInputType[1] ? parseOneChild("""input("nam1", '${type}')""") as Input : parseOneChild(
                    """input(name: "nam1", type: '${type}')""") as Input

        then:
            AssertionError e = thrown()
            e.message.contains("not supported")
            e.message.contains("nam1")
            e.message.contains(type)

        where:
            typeAndInputType << combinations([["capability.badCapability",
                                               "devic.someDeviceName",
                                               "devicee.someDeviceName",
                                               "blah",
                                               "booll"], [true, false]])
    }

    @Unroll
    def "#sandboxClass.simpleName: Enum input type must have 'options'"(Class sandboxClass) {
        when:
            parseInput(sandboxClass, 'enum')

        then:
            AssertionError e = thrown()
            e.message.contains("of type 'enum' must have 'options' parameter with enum values")

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "#sandboxClass.simpleName: Non-enum input(type: '#type') types must not have options"(
            String type, Class sandboxClass)
    {
        when:
            parseInput(sandboxClass, type, 'options: ["A", "B"]')

        then:
            AssertionError e = thrown()
            e.message.contains("only 'enum' input type needs 'options' parameter")
            e.message.contains("myInput")

        where:
            [type, sandboxClass] << [
                    ["capability.thermostat", HubitatAppSandbox],
                    ["device.someDeviceName", HubitatAppSandbox],
                    *combineWithSandboxes(["bool"])//,
//                    //*combineWithSandboxes(["boolean"]),
//                    *combineWithSandboxes(["decimal"]),
//                    *combineWithSandboxes(["email"]),
//                      //"enum", // Enum is tested separately - it needs 'options'
//                    *combineWithSandboxes(["hub"]),
//                    *combineWithSandboxes(["icon"]),
//                    *combineWithSandboxes(["number"]),
//                    *combineWithSandboxes(["password"]),
//                    *combineWithSandboxes(["phone"]),
//                    *combineWithSandboxes(["time"]),
//                    *combineWithSandboxes(["text"])
            ]
    }

    @Unroll
    def "#sandboxClass.simpleName: Enum input type can take list of strings as options"(Class sandboxClass) {
        when:
            final def input = parseInput(sandboxClass, 'enum', "options: ['Val1', 'Val2']")

        then:
            input.readType() == 'enum'
            input.options.options == ['Val1', 'Val2']

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "#sandboxClass.simpleName: Enum input type can't take string as 'options'"(Class sandboxClass) {
        when:
            parseInput(sandboxClass, 'enum', [options: 'Val1'])

        then:
            AssertionError e = thrown()
            e.message.contains("must be a list of values or map int->value")
            e.message.contains("Val1")

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "#sandboxClass.simpleName: Enum input type can take list of ints as options"(Class sandboxClass) {
        when:
            final def input = parseInput(sandboxClass, 'enum', [options: [11, 22]])

        then:
            input.readType() == 'enum'
            input.options.options == [11, 22]

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "#sandboxClass.simpleName: Enum input type can take map of int->string as options"(Class sandboxClass) {
        when:
            final def input = parseInput(sandboxClass, 'enum', [options: [42:'Val1', 33:'Val2']])

        then:
            input.readType() == 'enum'
            input.options.options == [42: 'Val1', 33: 'Val2']

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "#sandboxClass.simpleName: Enum input type can take map of string->string as options"(Class sandboxClass) {
        when:
            final def input = parseInput(sandboxClass, 'enum', [options: ['42':'Val1', '33':'Val2']])

        then:
            input.readType() == 'enum'
            input.options.options == ["42": 'Val1', "33": 'Val2']

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }

    @Unroll
    def "#sandboxClass.simpleName: Failing cases: enum default value must be one of its options (#options)"(
            String options, String expectedValidValues, Class sandboxClass)
    {
        when:
            parseInput(sandboxClass, 'enum', "defaultValue: 'ValUnknown', options: ${options}")

        then:
            AssertionError e = thrown()
            e.message.contains("defaultValue 'ValUnknown' is not one of valid values: ${expectedValidValues}")

        where:
            [options, expectedValidValues, sandboxClass] << combineWithSandboxes([
                    ["['Val1', 'Val2']"           , "[Val1, Val2]"],
                    ["[42:'Val1', 33:'Val2']"     , "[Val1, Val2] or [42, 33]"],
                    ["['42':'Val1', '33':'Val2']" , "[Val1, Val2] or [42, 33]"]
            ])
    }

    @Unroll
    def "#sandboxClass.simpleName: Successful cases: enum default value must be one of its options (#options)"(
            String options, String defaultValue, Class sandboxClass)
    {
        expect:
            parseInput(sandboxClass, 'enum', "defaultValue: ${defaultValue}, options: ${options}")

        where:
            [options, defaultValue, sandboxClass] << combineWithSandboxes([
                ["['Val1', 'Val2']"          , "'Val1'"],
                ["['Val1', 'Val2']"          , "'Val2'"],
                ["[42:'Val1', 33:'Val2']"    , "'Val1'"],
                ["[42:'Val1', 33:'Val2']"    , "'42'"],
                ["[42:'Val1', 33:'Val2']"    , "'33'"],
                ["['42':'Val1', '33':'Val2']", "'Val2'"],
                ["['42':'Val1', '33':'Val2']", "'42'"]
             ])
    }

    @Unroll
    def "#sandboxClass.simpleName: Enum options (#options) can't repeat each other"(
            String options, String whatWasDuplicated, Class sandboxClass)
    {
        when:
            parseInput(sandboxClass, 'enum', "options: ${options}")

        then:
            AssertionError e = thrown()
            e.message.contains("enum ${whatWasDuplicated} was duplicated")

        where:
            [options, whatWasDuplicated, sandboxClass] << combineWithSandboxes([
                ["['Val2', 'Val1', 'Val2']"                , "value 'Val2'"],
                ["[11:'Val2', 22:'Val1', 33:'Val2']"       , "value 'Val2'"],
                ["['11':'Val2', '22':'Val1', '33':'Val2']" , "value 'Val2'"]
            ])
    }
}
