package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.validation.Validator
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@CompileStatic
class Input {
    final Map options
    final String name
    final String type
    final Validator validator

    String readType()
    {
        if (type != null)
        {
            return type
        }

        return options?.type
    }

    @Override
    String toString()
    {
        return "input(options: ${options}, name: ${name}, type: ${type})"
    }
}
