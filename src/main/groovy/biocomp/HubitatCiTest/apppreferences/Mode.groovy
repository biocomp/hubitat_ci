package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
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

    Mode(Map options, EnumSet<ValidationFlags> validationFlags)
    {
        this.options = options

        if (!validationFlags.contains(ValidationFlags.DontValidatePreferences))
        {
            paramValidator.validate(this.toString(), options, validationFlags, true)
        }
    }

    @Override
    String toString() { return "mode(${options})" }
}
