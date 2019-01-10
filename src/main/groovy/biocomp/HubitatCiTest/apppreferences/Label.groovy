package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
import groovy.transform.TypeChecked

@TypeChecked
class Label {
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "title", required: true)
        stringParameter(name: "description")
        stringParameter(name: "image")
        boolParameter(name: "required")
    }

    Label(Map options) {
        this.options = options

        paramValidator.validate(this.toString(), options)
    }

    Map options
}
