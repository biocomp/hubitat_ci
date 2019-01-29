package biocomp.hubitatCi.apppreferences

import biocomp.hubitatCi.validation.Validator
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@CompileStatic
class Input {
    final Map unnamedOptions
    final Map options
    final Validator validator

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
