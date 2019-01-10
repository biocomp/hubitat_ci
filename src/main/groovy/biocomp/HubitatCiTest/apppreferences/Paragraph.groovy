package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
class Paragraph {
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "title", required: true)
        stringParameter(name: "image")
        boolParameter(name: "required")
    }

    Paragraph(String text, Map options) {
        this.text = text
        this.options = options

        if (text == null) {
            paramValidator.validate(this.toString(), options)
        }
    }

    String text
    Map options
}
