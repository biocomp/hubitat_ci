package me.biocomp.hubitat_ci

import groovy.transform.CompileStatic
import groovy.transform.Sortable
import groovy.transform.TupleConstructor
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
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

    String toNormalizedSimpleString()
    {
        "${isPublic ? 'public ' : ''}${simpleReturnType} ${name}(${simpleArgumentTypes})"
    }
}

@CompileStatic
class MethodInfo
{
    MethodInfo(String[] properties, String returnType, String definingClass, String name, String[] argumentTypes)
    {
        this.properties = properties
        this.returnType = returnType
        this.definingClass = definingClass
        this.methodName = name
        this.argumentTypes = argumentTypes
        this.isStatic = properties.contains('static')
        this.isPublic = properties.contains('public')
    }

    private String toSimpleType(String type)
    {
        return type.split('\\.').last().split('\\$').last()
    }

    final String[] properties
    final String returnType
    final String definingClass
    final String methodName
    final String[] argumentTypes
    final boolean isStatic
    final boolean isPublic

    SimpleMethodInfo toSimpleInfo() {
        final def shortMethodName = methodName.split('\\$').last()

        return new SimpleMethodInfo(
                isPublic: isPublic,
                simpleReturnType: toSimpleType(returnType),
                name: shortMethodName,
                simpleArgumentTypes: argumentTypes.collect{toSimpleType(it)}.join(', ')
        )
    }
}


class HelperScriptsTest extends
        Specification
{
    @CompileStatic
    private static boolean isSignature(String signature)
    {
        signature.contains('public')
    }

    @CompileStatic
    private static MethodInfo parseSignature(String signature)
    {
        final def iss = isSignature(signature)
        signature = signature.trim()
        assert iss: "'${signature}' Isn't a method signature"
        final def indexOfOpenParen = signature.findIndexOf{it == '('}
        assert indexOfOpenParen != -1: "${signature} isn't a method signature no '(' present"

        final String[] argTypes = signature.substring(indexOfOpenParen + 1, signature.size() - 1).split(',')
        final String signatureFirstPart = signature.substring(0, indexOfOpenParen)
        final def startOfDefiningClass = signatureFirstPart.findLastIndexOf(0, { it == ' '})
        assert startOfDefiningClass != -1: "'${signature}' has no space before '(', which should never happen"

        final String[] partsOfDefiningClass = signatureFirstPart.substring(startOfDefiningClass + 1).split('\\.')
        final String definingClass = partsOfDefiningClass.dropRight(1).join('.')
        final String methodName = partsOfDefiningClass.last()

        final String[] optionsAndReturnType = signatureFirstPart.substring(0, startOfDefiningClass).split(' ')

        return new MethodInfo(
                optionsAndReturnType.dropRight(1),
                optionsAndReturnType.last(),
                definingClass,
                methodName,
                argTypes
        )
    }

    @Unroll
    def "#classToCheck methods match recorded ones"(Class classToCheck) {
        when:
            final def file = "src/main/groovy/${classToCheck.name.replace('.', '/')}Methods.txt"
            def fromFile = new File(file).readLines();
            final def indexOfClass = fromFile.findIndexOf { s -> s.startsWith("class") || s.startsWith("interface") }
            if (indexOfClass != -1) {
                fromFile = fromFile.drop(indexOfClass);
            }

            fromFile = fromFile.findAll{isSignature(it)}
                    .collect{parseSignature(it).toSimpleInfo()}
            // Cut out toString(), metaclass-related methods, and some weird methods like this$dist$get$1, which I'm not sure what they are for.
                    .findAll{it.name != 'toString' && !it.name.endsWith('getStaticMetaClass') && it.name != '1' }
                    .sort()
                    .collect{it.toNormalizedSimpleString()}

            final def actual = new HubitatAppSandbox(new File("Scripts/helper_app.groovy"))
                    .run()
                    .dumpClassImpl(classToCheck)
                    .split('\n')
                    .findAll{isSignature(it)}
                    .collect{parseSignature(it).toSimpleInfo()}
            // Cut out toString(), metaclass-related methods, and some weird methods like this$dist$get$1, which I'm not sure what they are for.
                    .findAll{it.name != 'toString' && !it.name.endsWith('getStaticMetaClass') && it.name != '1' }
                    .sort()
                    .collect{it.toNormalizedSimpleString()}

        then:
            actual == fromFile

        where:
            classToCheck << [
                    DeviceExecutor,
                    AppExecutor
            ]
    }

    def "helper_app.groovy properly dumps classes"()
    {
        when:
            final def script = new HubitatAppSandbox(new File("Scripts/helper_app.groovy")).run()

        then:
            script.dumpClassImpl(TestToDump.class) == """class ${TestToDump.class.name}:
Methods:[
  public java.lang.String me.biocomp.hubitat_ci.TestToDump.bar(java.lang.String,java.lang.String)
  public int me.biocomp.hubitat_ci.TestToDump.getFoo()
  public void me.biocomp.hubitat_ci.TestToDump.setFoo(int)
]"""

            script.dumpClassImpl(EnumToDump.class) == """class ${EnumToDump.class.name}:
Enum values:[
  One,
  Two
]"""
            script.dumpClassImpl(AppExecutor.class) != ""
    }

    def "helper_driver.groovy dumps class correctly"() {
        when:
            final def script = new HubitatDeviceSandbox(new File("Scripts/Devices/helper_driver.groovy")).run()

        then:
            script.dumpClassImpl(TestToDump.class) == """class ${TestToDump.class.name}:
Methods:[
  public java.lang.String me.biocomp.hubitat_ci.TestToDump.bar(java.lang.String,java.lang.String)
  public int me.biocomp.hubitat_ci.TestToDump.getFoo()
  public void me.biocomp.hubitat_ci.TestToDump.setFoo(int)
]"""

            script.dumpClassImpl(EnumToDump.class) == """class ${EnumToDump.class.name}:
Enum values:[
  One,
  Two
]"""
            script.dumpClassImpl(DeviceExecutor.class) != ""
    }
}

class BaseToDump
{
    String toString() {
        return null
    }

    def methodMissing(String name, def args) {}
    def propertyMissing(String name) {}

    void setProperty(String name, Object value) {}
    def getProperty(String name) {}
}

class TestToDump extends BaseToDump {
    int foo

    String bar(String a, String b) {
    }

    @Override
    String toString() {
        return null
    }

    def methodMissing(String name, def args) {}
    def propertyMissing(String name) {}

    void setProperty(String name, Object value) {}
    def getProperty(String name) {}
}

enum EnumToDump
{
    One,
    Two
}