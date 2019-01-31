package biocomp.hubitatCi.apppreferences

import biocomp.hubitatCi.validation.AppValidator
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

@TupleConstructor
@CompileStatic
class Input {
    final Map unnamedOptions
    final Map options
    final AppValidator validator

    String readType()
    {
        return unnamedOptions.type ? unnamedOptions.type : options?.type
    }

    @Override
    String toString()
    {
        return "input(options: ${options}, unnamedOptions: ${unnamedOptions})"
    }
}
