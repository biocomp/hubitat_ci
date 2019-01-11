package biocomp.hubitatCiTest.apppreferences

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
class Input {
    Map options
    String name
    String type

    String readType()
    {
        if (type != null)
        {
            return type
        }

        return options?.type
    }
}
