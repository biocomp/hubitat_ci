package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.validation.Flags
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import spock.lang.Specification
import spock.lang.Unroll

class Validator {
    void hubitatciValidateAfterMethodCall(String method) {
        methods.add("validate(${method})")
    }

    int funcA() {
        methods.add("funcA")
        return 123
    }

    String funcB() {
        methods.add("funcB")
    }

    List<String> methods = []
}


class ValidationAfterEachMethodCustomizerTest extends
        Specification
{
    //@CompileStatic
    ValidatingScript makeModifiedScript(String script) {
        return makeScript(script, new AddValidationAfterEachMethodCompilationCustomizer())
    }

    //@CompileStatic
    ValidatingScript makeScript(String script, CompilationCustomizer customizer = null) {
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = ValidatingScript.class.name

        if (customizer) {
            compilerConfiguration.compilationCustomizers.add(customizer)
            compilerConfiguration.compilationCustomizers.add(new LoggingCompilationCustomizer())
        }

        def parsedScript = new GroovyShell(this.class.classLoader,
                new DoNotCallMeBinding(),
                compilerConfiguration).parse(script) as ValidatingScript

        assert (parsedScript.getValidator().methods == [])
        return parsedScript
    }

    @Unroll
    def "Injecting into #fullMethod"(String fullMethod, String[] expectedCalls) {
        when:
            def script = makeModifiedScript(fullMethod)
            def originalScript = makeScript(fullMethod)

            def returnVal = script.foo()
            def originalReturnVal = originalScript.foo()

        then:
            script.getValidator().methods == expectedCalls
            returnVal == originalReturnVal

        where:
            fullMethod                                                                                              || expectedCalls
            "void foo() { def i = 3 }"                                                                              || ["validate(foo)"]
            "def foo() { def i = 3 }"                                                                               || ["validate(foo)"]
            "String foo() { funcA()\nreturn 'returned string' }"                                                    || ["funcA", "validate(foo)"]
            "int foo() { if (true) return 42\nreturn 123 }"                                                         || ["validate(foo)"]
            "int foo() { 42 }"                                                                                      || ["validate(foo)"]
            "String foo() { return funcB() }"                                                                       || ["funcB", "validate(foo)"]
            "String foo() { return bar() }\n String bar() { return 'I am bar' }"                                    || ["validate(bar)", "validate(foo)"]
            "String foo() { return bar() }\n static String bar() { return 'I am static bar' }"                      || ["validate(foo)"]
            "String foo() { return bar('bararg') }\n String bar(String arg) { return \"I have an arg = \${arg}!\" }" || ["validate(bar)", "validate(foo)"]
    }



    def "Can read values from overriden methods"() {
        when:
            final def script = new HubitatAppSandbox("""
private static Map bar(def cls)
{
    Map result = [:]
    return result // Test was failing before with variable named 'result'
}

private String foo()
{
    return "blah"
}
""").run(validationFlags: [Flags.DontValidateDefinition, Flags.DontRunScript])

        then:
            script.foo() == "blah"
    }
}
