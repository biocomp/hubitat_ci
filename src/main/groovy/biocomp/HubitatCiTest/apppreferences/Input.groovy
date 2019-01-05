package biocomp.hubitatCiTest.apppreferences

import groovy.transform.TupleConstructor

@TupleConstructor
class Input {
    def parent

    Map options
    String name
    String type
}
