package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
import groovy.transform.TypeChecked

@TypeChecked
class Paragraph {
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter(name: "title")
        stringParameter(name: "image")
        boolParameter(name: "required")
    }

    Paragraph(String text, Map options, EnumSet<ValidationFlags> flags) {
        this.text = text
        this.options = options

        if (!flags.contains(ValidationFlags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, flags)
        }
    }

    final String text
    final Map options
}
