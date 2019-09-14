package me.biocomp.hubitat_ci.device

import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import spock.lang.Specification

class RestOfDeviceScriptTests extends
        Specification
{
    def "helper_driver.groovy dumps class correctly"() {
        when:
            final def script = new HubitatDeviceSandbox(new File("Scripts/Devices/helper_driver.groovy")).run()

        then:
            script.dumpClassImpl(TestToDump.class) == """class ${TestToDump.class.name}:
Methods:[
  public java.lang.String me.biocomp.hubitat_ci.device.TestToDump.bar(java.lang.String,java.lang.String)
  public int me.biocomp.hubitat_ci.device.TestToDump.getFoo()
  public void me.biocomp.hubitat_ci.device.TestToDump.setFoo(int)
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
}

enum EnumToDump
{
    One,
    Two
}