package biocomp.hubitatCiTest.apppreferences

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
class Input {
    final Map options
    final String name
    final String type
    final EnumSet<ValidationFlags> flags

    String readType()
    {
        if (type != null)
        {
            return type
        }

        return options?.type
    }
}
