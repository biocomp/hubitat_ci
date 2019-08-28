package me.biocomp.hubitat_ci.app

import biocomp.hubitatCi.api.app_api.AppExecutor
import biocomp.hubitatCi.api.common_api.Log
import biocomp.hubitatCi.app.HubitatAppSandbox
import biocomp.hubitatCi.validation.Flags
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

class AppSandboxTest extends Specification {
    List<Tuple2<String, String>> makeScriptVariations(List<Tuple2<String, String>> expressionsAndResults)
    {
        def result = []
        expressionsAndResults.each({
            result << new Tuple(it.get(0), it.get(1))
            result << new Tuple("""
def foo()
{
    ${it.get(0)}
}
""", it.get(1))
        })
    }

    @Unroll
    def "Expressions that are not allowed fail to compile and expect"(String script, String expectedErrorPart) {
        when:
            def sandbox = new HubitatAppSandbox(script)

            // Not running the script, compilation should still fail.
            sandbox.compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains(expectedErrorPart)

        where:
            [script, expectedErrorPart] << makeScriptVariations([
                    new Tuple("println 'a'", "println"),
                    new Tuple("print 'a'", "print"),
                    new Tuple("[].execute()", "execute"),
                    new Tuple("String s\ns.getClass()", "getClass"),
                    new Tuple("String s\ns.getMetaClass()", "getMetaClass"),
                    new Tuple("String s\ns.setMetaClass(null)", "setMetaClass"),
                    new Tuple("String s\ns.propertyMissing()", "propertyMissing"),
                    new Tuple("String s\ns.methodMissing()", "methodMissing"),
                    new Tuple("String s\ns.invokeMethod('a')", "invokeMethod"),
                    new Tuple("getProducedPreferences()", "getProducedPreferences"),
                    new Tuple("void foo() { def prefs = producedPreferences }", "producedPreferences"),
                    new Tuple("getProducedDefinition()", "getProducedDefinition"),
                    new Tuple("void foo() { def prefs = producedDefinition }", "producedDefinition"),
                    new Tuple("printf", "printf"),
                    new Tuple("sleep 10", "sleep")
            ])
    }

    def "Can't define your classes!"()
    {
        when:
            new HubitatAppSandbox("""
class MyShinyNewClass{}
""").compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains("MyShinyNewClass")
    }

    def "Can't call System.out!"()
    {
        when:
            new HubitatAppSandbox("""
System.out.print "Boom!"
""").compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains("System.out")
    }

    def "Can't create File"()
    {
        when:
            new HubitatAppSandbox("""
File.createNewFile('foo.txt')"
""").compile()

        then:
            MultipleCompilationErrorsException ex = thrown()
            ex.message.contains("File")
    }

    def "Local variable with no 'def' or type is not confused with property"()
    {
        given:
            def log = Mock(Log)
            def api = Mock(AppExecutor)

        when:
            def script = new HubitatAppSandbox("""
int loginCheck()
{
    return 42
}

def foo()
{
    LoginCheck = loginCheck()
    if (LoginCheck) { log.debug '1' }
    else { log.debug '2' }
} 
""").run(noValidation: true)

        then:
            _*api.getLog() >> log
    }

    private def makeScriptForPrivateCheck(def fileOrText) {
        return new HubitatAppSandbox(fileOrText).compile(
                noValidation: true,
                customizeScriptBeforeRun: { script ->
                    script.getMetaClass().myPrivateMethod1 = { -> "was overridden1!" }
                    script.getMetaClass().myPrivateMethod2 = { def arg1, def arg2 -> "was overridden2(${arg1}, ${arg2})!"
                    }
                })
    }

    void verifyMethodsWereOverridden(Script script)
    {
        assert script.myPrivateMethod1() == "was overridden1!"
        assert script.publicMethodThatCallsPrivateMethod1() == "was overridden1!"
        assert script.myPrivateMethod2(42, "abc") == "was overridden2(42, abc)!"
        assert script.publicMethodThatCallsPrivateMethod2() == "was overridden2(123, argFromPublicMethod)!"
    }

    def "private methods in the Script (as text) can be mocked!()"()
    {
        setup:
            def script = makeScriptForPrivateCheck(new File("Scripts/ScriptWithPrivateMethod.groovy"))

        expect:
            verifyMethodsWereOverridden(script)
    }

    def "private methods in the Script (as File) can be mocked!()"()
    {
        setup:
            def script = makeScriptForPrivateCheck(new File("Scripts/ScriptWithPrivateMethod.groovy").readLines().join('\n'))

        expect:
            verifyMethodsWereOverridden(script)
    }

    def "subscribe() will fail validation for non-existing attribute"()
    {
        setup:
            final def script = """
preferences {
    page(name:"mainPage", title:"Settings", install: true, uninstall: true) {
        section() {
            input (name:"thermostat", type: "capability.thermostat", title: "Thermostat", required: true, multiple: false)
        }
    }
}

def installed() {
    initialize()
}

def initialize() {
    subscribe(thermostat, "badAttributeName", realCoolingSetpointHandler)
}

def realCoolingSetpointHandler(evt) {}
"""
        when:
            new HubitatAppSandbox(script).run(validationFlags: [Flags.DontValidateDefinition]).installed()

        then:
            AssertionError e = thrown()
            e.message.contains("'Thermostat' does not contain attribute 'badAttributeName'. Valid attributes are: [")
    }
}