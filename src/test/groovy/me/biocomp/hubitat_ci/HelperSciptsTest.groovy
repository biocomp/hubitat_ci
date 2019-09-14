package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification

import java.lang.reflect.Method

class HelperSciptsTest extends Specification{
    private static void dumpMethods(Class cls, List<String> result)
    {
        final Set<String> skipTheseMethods = [
                "getProperty",
                "setProperty",
                "setMetaClass",
                "getMetaClass",
                "invokeMethod"
        ] as Set

        def shouldSkipMethod = { Method m ->
            skipTheseMethods.contains(m.name) || m.declaringClass.name == Object.name || m.name.endsWith("toString")
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
            return !shouldSkipMethod(it)
        }.sort(makeComparator([
                { a, b -> a.name.compareTo(b.name) },
                { a, b -> (a.parameters.size() <=> b.parameters.size()) },
                { a, b -> a.toString() <=> b.toString() }])
        ).collect{ "  ${it}"})

        result << "]"
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

class TestToDump
{
    int foo
    String bar(String a, String b) {}


    @Override
    String toString() { null }
}

enum EnumToDump
{
    One,
    Two
}
