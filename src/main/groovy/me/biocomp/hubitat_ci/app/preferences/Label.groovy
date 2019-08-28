package me.biocomp.hubitat_ci.app.preferences

import biocomp.hubitatCi.app.AppValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.NamedParametersValidator
import groovy.transform.TypeChecked

@TypeChecked
class Label {
    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter("title", required(), mustNotBeEmpty())
        stringParameter("description", notRequired(), mustNotBeEmpty())
        stringParameter("image", notRequired(), mustNotBeEmpty())
        boolParameter("required", notRequired())
    }

    Label(Map options, EnumSet<Flags> validationFlags) {
        this.options = options

        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validationFlags)
        }
    }

    final Map options
}
