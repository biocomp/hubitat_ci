package biocomp.hubitatCi.util

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
abstract class ValidatingScript extends Script
{
    Validator validator = new Validator()

    @CompileStatic
    Validator getValidator()
    {
        return this.@validator
    }

    @CompileStatic
    void hubitatciValidateAfterMethodCall(String method)
    {
        this.@validator.hubitatciValidateAfterMethodCall(method)
//        println "Look, ${method} is called from test!"
    }

    @CompileStatic
    int funcA() {
        return this.@validator.funcA()
    }

    @CompileStatic
    String funcB() {
        return this.@validator.funcB()
    }
}