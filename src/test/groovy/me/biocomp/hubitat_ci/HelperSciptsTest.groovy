package me.biocomp.hubitat_ci

import groovy.transform.CompileStatic
import groovy.transform.Sortable
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Hub
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification
import spock.lang.Unroll

@CompileStatic
@TupleConstructor
@Sortable
class SimpleMethodInfo {
    boolean isPublic
    String simpleReturnType
    String name
    String simpleArgumentTypes

    String toNormalizedSimpleString() {
        "${isPublic ? 'public ' : ''}${simpleReturnType} ${name}(${simpleArgumentTypes})"
    }
}

@CompileStatic
class MethodInfo {
    MethodInfo(List<String> properties, String returnType, String definingClass, String name, List<String> argumentTypes) {
        this.properties = properties
        this.returnType = returnType
        this.definingClass = definingClass
        this.methodName = name
        this.argumentTypes = argumentTypes
        this.isStatic = properties.contains('static')
        this.isPublic = properties.contains('public')
    }

    private String toSimpleType(String type) {
        return type.split('\\.').last().split('\\$').last()
    }

    final List<String> properties
    final String returnType
    final String definingClass
    final String methodName
    final List<String> argumentTypes
    final boolean isStatic
    final boolean isPublic

    SimpleMethodInfo toSimpleInfo() {
        final def shortMethodName = methodName.split('\\$').last()

        return new SimpleMethodInfo(isPublic: isPublic,
                simpleReturnType: toSimpleType(returnType),
                name: shortMethodName,
                simpleArgumentTypes: argumentTypes.collect { toSimpleType(it) }.join(', '))
    }
}

/**
 * Compare dumped methods from *Methods.txt against defined interfaces.
 */
class ApiSignaturesVerificationTest extends
        Specification
{
    @CompileStatic
    private static boolean isSignature(String signature) {
        signature.trim().startsWith('public')
    }

    @CompileStatic
    private static MethodInfo parseSignature(String signature) {
        final def iss = isSignature(signature)
        assert iss: "'${signature}' Isn't a method signature"

        // Remove comment after the method
        final def commentStart = signature.findIndexOf { it == '#' }
        if (commentStart != -1) {
            signature = signature.substring(0, commentStart)
        }

        signature = signature.trim()

        final def indexOfOpenParen = signature.findIndexOf { it == '(' }
        assert indexOfOpenParen != -1: "${signature} isn't a method signature no '(' present"

        final String[] argTypes = signature
                .substring(indexOfOpenParen + 1, signature.size() - 1)
                .split(',')
                .collect { it.trim() }
                .findAll { it }
        final String signatureFirstPart = signature.substring(0, indexOfOpenParen)
        final def startOfDefiningClass = signatureFirstPart.findLastIndexOf(0, { it == ' ' })
        assert startOfDefiningClass != -1: "'${signature}' has no space before '(', which should never happen"

        final String[] partsOfDefiningClass = signatureFirstPart.substring(startOfDefiningClass + 1).split('\\.')
        final String definingClass = partsOfDefiningClass.dropRight(1).join('.')
        final String methodName = partsOfDefiningClass.last()

        final String[] optionsAndReturnType = signatureFirstPart.substring(0, startOfDefiningClass).split(' ')

        return new MethodInfo(optionsAndReturnType.dropRight(1) as List<String>,
                optionsAndReturnType.last(),
                definingClass,
                methodName,
                argTypes as List<String>)
    }

    @CompileStatic
    private static List<MethodInfo> parseDumpedSignatures(List<String> contents) {
        contents.findAll { isSignature(it) }.collect { parseSignature(it) }
    }

    @CompileStatic
    private static List<String> normalizeAndPrintSignatures(List<MethodInfo> contents) {
        contents.collect { it.toSimpleInfo() }
        // Cut out toString(), metaclass-related methods, and some weird methods like this$dist$get$1, which I'm not sure what they are for.
                .findAll { it.name != 'toString' && !it.name.endsWith('getStaticMetaClass') && it.name != '1' }
                .sort()
                .collect { it.toNormalizedSimpleString() }
    }

    @CompileStatic
    private static List<String> readSignaturesFromFile(Class classToCheck) {
        final def file = "src/main/groovy/${classToCheck.name.replace('.', '/')}Methods.txt"
        def fromFile = new File(file).readLines();
        final def indexOfClass = fromFile.findIndexOf { s -> s.startsWith("class") || s.startsWith("interface") }
        if (indexOfClass != -1) {
            fromFile = fromFile.drop(indexOfClass);
        }

        fromFile = normalizeAndPrintSignatures(parseDumpedSignatures(fromFile))
    }

    @CompileStatic
    private static List<String> readSignaturesFromClass(Class classToCheck, DeviceExecutor executorMock) {
        final def methodsAsString = (String) new HubitatDeviceSandbox(new File("Scripts/Devices/helper_driver.groovy"))
                .run(api: executorMock)
                .invokeMethod("dumpClassImpl", classToCheck)

        final def actual = normalizeAndPrintSignatures(parseDumpedSignatures(methodsAsString.split('\n') as List))
    }

    @Unroll
    def "parseSignature(#input) -> #result test"(String input, MethodInfo result) {
        when:
            final def actualResult = parseSignature(input)

        then:
            actualResult.properties == result.properties
            actualResult.returnType == result.returnType
            actualResult.definingClass == result.definingClass
            actualResult.methodName == result.methodName
            actualResult.argumentTypes == result.argumentTypes
            actualResult.isStatic == result.isStatic
            actualResult.isPublic == result.isPublic

        where:
            input                                                          | result
            "public void My.Class.m()#comment"                             | new MethodInfo(["public"], "void",
                    "My.Class", "m", [])
            "public My.Return.Type My.Class.m(java.String, Long) #comment" | new MethodInfo(["public"],
                    "My.Return.Type", "My.Class", "m", ["java.String", "Long"])
            "public static Object My.Class.m(Long) #comment"               | new MethodInfo(["public", "static"],
                    "Object", "My.Class", "m", ["Long"])
    }

    @Unroll
    def "#classToCheck.simpleName methods match recorded ones"(Class classToCheck) {
        when:
            final def fromFile = readSignaturesFromFile(classToCheck)

            final def api = Mock(DeviceExecutor) {
                _ * getDevice() >> Mock(DeviceWrapper) {
                    _ * getHub() >> Mock(Hub) {
                        _ * getFirmwareVersionString() >> "1.2.3"
                    }
                }
            }

            final def actual = readSignaturesFromClass(classToCheck, api)

        then:
            actual == fromFile

        where:
            classToCheck << [DeviceExecutor,
                             AppExecutor,
                             InstalledAppWrapper,
                             Location]
    }
}

class HelperScriptsTest extends
        Specification
{
    private static final String expectedTestToDump = """Platform version: 1.2.3

class ${TestToDump.class.name}:
Methods:[
  public java.lang.String me.biocomp.hubitat_ci.TestToDump.bar(java.lang.String,java.lang.String)
  public int me.biocomp.hubitat_ci.TestToDump.getFoo()
  public void me.biocomp.hubitat_ci.TestToDump.setFoo(int)
]"""

    private static final String expectedEnumToDump = """Platform version: 1.2.3

class ${EnumToDump.class.name}:
Enum values:[
  One,
  Two
]"""

    def "helper_driver.groovy dumps class correctly"() {
        when:
            final def hub = Mock(Hub) {
                _ * getFirmwareVersionString() >> "1.2.3"
            }

            final def deviceWrapper = Mock(DeviceWrapper) {
                _ * getHub() >> hub
            }

            final def api = Mock(DeviceExecutor) {
                _ * getDevice() >> deviceWrapper
            }

            final def script = new HubitatDeviceSandbox(new File("Scripts/Devices/helper_driver.groovy")).run(api: api)

        then:
            script.dumpClassImpl(TestToDump.class) == expectedTestToDump
            script.dumpClassImpl(EnumToDump.class) == expectedEnumToDump
            script.dumpClassImpl(DeviceExecutor.class) != ""
    }

    def "helper_app.groovy dumps class correctly"() {
        when:
            final def hub = Mock(Hub) {
                _ * getFirmwareVersionString() >> "1.2.3"
            }

            final def location = Mock(Location) {
                _ * getHubs() >> [hub]
            }

            final def api = Mock(AppExecutor) {
                _ * getLocation() >> location
            }

            final def script = new HubitatAppSandbox(new File("Scripts/helper_app.groovy")).run(api: api)

        then:
            script.dumpClassImpl(TestToDump.class) == expectedTestToDump
            script.dumpClassImpl(EnumToDump.class) == expectedEnumToDump
            script.dumpClassImpl(DeviceExecutor.class) != ""
    }
}

class BaseToDump {
    String toString() {
        return null
    }

    def methodMissing(String name, def args) {
    }

    def propertyMissing(String name) {
    }

    void setProperty(String name, Object value) {
    }

    def getProperty(String name) {
    }
}

class TestToDump extends
        BaseToDump
{
    int foo

    String bar(String a, String b) {
    }

    @Override
    String toString() {
        return null
    }

    def methodMissing(String name, def args) {
    }

    def propertyMissing(String name) {
    }

    void setProperty(String name, Object value) {
    }

    def getProperty(String name) {
    }
}

enum EnumToDump
{
    One,
    Two
}