package me.biocomp.hubitat_ci

import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.validation.Flags
import org.codehaus.groovy.control.MultipleCompilationErrorsException
import spock.lang.Specification
import spock.lang.Unroll

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
     * Add HubitatAppSandbox and HubitatDeviceSandbox permutations
     */
    List<Tuple3<String, String, Class>> makeScriptVariation(String expression, String result) {
        [
            new Tuple(expression, result, HubitatDeviceSandbox),
            new Tuple(expression, result, HubitatAppSandbox)
        ]
    }

    List<List> combineWithSandboxes(List<List> inputs)
    {
        def results = []

        inputs.each{
            results << (it.clone() << HubitatAppSandbox)
            results << (it.clone() << HubitatDeviceSandbox)
        }

        results
    }

    List<Tuple3<String, String, Class>> makeScriptVariations(List<Tuple2<String, String>> expressionsAndResults) {
        def result = []

        expressionsAndResults.each{
            result.addAll(makeScriptVariation(it.get(0), it.get(1)))
            result.addAll(makeScriptVariation("""
def foo() {
    ${it.get(0)}
}
""", it.get(1))
            )
        }

        result
    }

    @Unroll
    def "Expression \"#script\" is not allowed and should fail to compile for #sandboxClass.simpleName"(String script, String expectedErrorPart, Class sandboxClass) {
        when:
            def sandbox = sandboxClass.newInstance(script)

            // Not running the script, compilation should still fail.
            sandbox.compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains(expectedErrorPart)

        where:
            [script, expectedErrorPart, sandboxClass] << makeScriptVariations([
                    new Tuple("println 'a'", "println"),
                    new Tuple("print 'a'", "print"),
                    new Tuple("[].execute()", "execute"),
                    new Tuple("String s\ns.getClass()", "getClass"),
                 new Tuple("String s\ns.getMetaClass()",
                         "getMetaClass"),
                 new Tuple("String s\ns.setMetaClass(null)",
                         "setMetaClass"),
                 new Tuple("String s\ns.propertyMissing()",
                         "propertyMissing"),
                 new Tuple("String s\ns.methodMissing()",
                         "methodMissing"),
                 new Tuple("String s\ns.invokeMethod('a')",
                         "invokeMethod"),
                 new Tuple("getProducedPreferences()",
                         "getProducedPreferences"),
                 new Tuple(
                         "void foo() { def prefs = producedPreferences }",
                         "producedPreferences"),
                 new Tuple("getProducedDefinition()",
                         "getProducedDefinition"),
                 new Tuple(
                         "void foo() { def prefs = producedDefinition }",
                         "producedDefinition"),
                 new Tuple("printf", "printf"),
                 new Tuple("sleep 10", "sleep")])
    }

    @Unroll
    def "#description not allowed and should fail to compile for #sandboxClass.simpleName"(String script, String expectedErrorPart, String description, Class sandboxClass) {
        when:
            sandboxClass.newInstance(script).compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains(expectedErrorPart)

        where:
            [script, expectedErrorPart, description, sandboxClass] << combineWithSandboxes([
                ["class MyShinyNewClass{}", "MyShinyNewClass", "Defining a new class"],
                ["System.out.print 'Boom!'", "System.out", "Calling System.out"],
                ["File.createNewFile('foo.txt')", "File", "Creating a File"]
            ])
    }

    @Unroll
    def "Local variable with no 'def' or type is not confused with property for #sandboxClass.simpleName"(Class sandboxClass, Class executorClass) {
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
            sandboxClass | executorClass
            HubitatAppSandbox | AppExecutor
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
            final def script = """
preferences {
    page(name:"mainPage", title:"Settings", install: true, uninstall: true) {
        section() {
            input (name:"testText", type: "text", title: "Test text", required: true, multiple: false)
        }
    }
}
"""
            String value = null

        when: 'Setting isn\'t overridden'
            value = new HubitatAppSandbox(script).run(userSettingValues: [:],
                    validationFlags: [Flags.DontValidateDefinition]).settings.testText
        then:
            value == null

        when: 'Setting is set to something'
            value = new HubitatAppSandbox(script).run(userSettingValues: ['testText': 'My test text'],
                    validationFlags: [Flags.DontValidateDefinition]).settings.testText
        then:
            value == 'My test text'

        where:
            sandboxClass << [HubitatAppSandbox, HubitatDeviceSandbox]
    }
}
