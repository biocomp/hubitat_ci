package biocomp.hubitatCi.apppreferences

import biocomp.hubitatCi.validation.NamedParametersValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.Validator
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
