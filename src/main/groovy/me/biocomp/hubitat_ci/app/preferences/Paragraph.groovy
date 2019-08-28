package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.app.AppValidator
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.TypeChecked

@TypeChecked
class Paragraph {
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter("title", notRequired(), mustNotBeEmpty())
        stringParameter("image", notRequired(), mustNotBeEmpty())
        boolParameter("required", notRequired())
    }

    Paragraph(String text, Map options, EnumSet<Flags> validationFlags) {
        this.text = text
        this.options = options

        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validationFlags)
        }
    }

    final String text
    final Map options
}
