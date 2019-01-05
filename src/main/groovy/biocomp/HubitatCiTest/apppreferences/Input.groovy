package biocomp.hubitatCiTest.apppreferences

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
class Input {
    Map options
    String name
    String type
}
