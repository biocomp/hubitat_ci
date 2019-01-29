package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.validation.NamedParametersValidator
import biocomp.hubitatCiTest.validation.Flags
import biocomp.hubitatCiTest.validation.Validator
import groovy.transform.TypeChecked

@TypeChecked
class Paragraph {
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "title")
        stringParameter(name: "image")
        boolParameter(name: "required")
    }

    Paragraph(String text, Map options, Validator validator) {
        this.text = text
        this.options = options

        if (!validator.hasFlag(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validator)
        }
    }

    final String text
    final Map options
}
