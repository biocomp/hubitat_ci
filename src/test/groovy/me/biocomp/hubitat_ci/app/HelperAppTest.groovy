package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import spock.lang.Specification

class HelperAppTest extends Specification{
    def "helper_app.groovy properly dumps classes()"()
    {
   when:
        final def script = new HubitatAppSandbox(new File("Scripts/helper_app.groovy")).run()

    then:
        script.dumpClassImpl(TestToDump.class) == """class ${TestToDump.class.name}:
Methods:[
  public java.lang.String me.biocomp.hubitat_ci.app.TestToDump.bar(java.lang.String,java.lang.String)
  public int me.biocomp.hubitat_ci.app.TestToDump.getFoo()
  public void me.biocomp.hubitat_ci.app.TestToDump.setFoo(int)
]"""

        script.dumpClassImpl(EnumToDump.class) == """class ${EnumToDump.class.name}:
Enum values:[
  One,
  Two
]"""
        script.dumpClassImpl(AppExecutor.class) != ""
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
