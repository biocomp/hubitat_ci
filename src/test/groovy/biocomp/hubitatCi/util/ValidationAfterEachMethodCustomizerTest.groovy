package biocomp.hubitatCi.util

import biocomp.hubitatCi.DoNotCallMeBinding
import biocomp.hubitatCi.SandboxClassLoader
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
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
}
