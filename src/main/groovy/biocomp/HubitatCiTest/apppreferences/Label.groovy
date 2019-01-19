package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.validation.NamedParametersValidator
import biocomp.hubitatCiTest.validation.Flags
import biocomp.hubitatCiTest.validation.Validator
import groovy.transform.TypeChecked

@TypeChecked
class Label {
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "title", required: true)
        stringParameter(name: "description")
        stringParameter(name: "image")
        boolParameter(name: "required")
    }

    Label(Map options, Validator validator) {
        this.options = options

        if (!validator.hasFlag(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validator)
        }
    }

    final Map options
}
