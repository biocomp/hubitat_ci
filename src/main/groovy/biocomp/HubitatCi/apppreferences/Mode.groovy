package biocomp.hubitatCi.apppreferences

import biocomp.hubitatCi.validation.NamedParametersValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.Validator
import groovy.transform.TypeChecked

@TypeChecked
class Mode {
    final Map options

    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "title", required: true, canBeEmpty: false)
        boolParameter(name: "required")
        boolParameter(name: "multiple")
        stringParameter(name: "image", canBeEmpty: false)
    }

    Mode(Map options, Validator validator)
    {
        this.options = options

        if (!validator.hasFlag(Flags.DontValidatePreferences))
        {
            paramValidator.validate(this.toString(), options, validator, true)
        }
    }

    @Override
    String toString() { return "mode(${options})" }
}
