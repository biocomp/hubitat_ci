package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification

import java.lang.reflect.Method

class HelperSciptsTest extends Specification{
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
