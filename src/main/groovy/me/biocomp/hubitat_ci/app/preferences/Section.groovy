package me.biocomp.hubitat_ci.app.preferences

import biocomp.hubitatCi.app.AppValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.NamedParametersValidator
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
class Section {
    int index
    String title
    Map options

    List children = []

    static private final NamedParametersValidator paramValidator = NamedParametersValidator.make{
        boolParameter("hideable", notRequired())
        boolParameter("hidden", notRequired())
        boolParameter("mobileOnly", notRequired())
        boolParameter("hideWhenEmpty", notRequired())
    }

    void validate(EnumSet<Flags> validationFlags)
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validationFlags)
            assert children.size() != 0: "Section ${this} must have at least some content"
        }
    }

    @Override
    String toString()
    {
        return "section #${index}(\"${title}\", ${options})"
    }
}
