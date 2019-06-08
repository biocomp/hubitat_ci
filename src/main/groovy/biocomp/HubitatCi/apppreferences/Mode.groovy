package biocomp.hubitatCi.apppreferences

import biocomp.hubitatCi.validation.NamedParametersValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.AppValidator
import groovy.transform.TypeChecked

@TypeChecked
class Mode {
    final Map options

    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter("title", required(), mustNotBeEmpty())
        boolParameter("required", notRequired())
        boolParameter("multiple", notRequired())
        stringParameter("image", notRequired(), mustNotBeEmpty())
    }

    Mode(Map options, AppValidator validator)
    {
        this.options = options

        if (!validator.hasFlag(Flags.DontValidatePreferences))
        {
            paramValidator.validate(this.toString(), options, validator)
        }
    }

    @Override
    String toString() { return "mode(${options})" }
}
