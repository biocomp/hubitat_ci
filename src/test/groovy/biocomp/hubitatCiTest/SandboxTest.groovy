package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.AppExecutorApi
import spock.lang.Specification

/*
*
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

class SandboxTest extends Specification {
    def prohibitedMethodsAndClasses = []

    def "println is not allowed"() {
        setup:
            def sandbox = new HubitatAppSandbox("println 'a'")

        when:
            sandbox.setupScript()

        then:
            SecurityException ex = thrown()
            ex.message.contains("println")
    }

    def "Can't define your classes!"()
    {
        expect:
            assert false
    }
}