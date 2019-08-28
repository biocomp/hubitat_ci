package me.biocomp.hubitat_ci.app.preferences

import me.biocomp.hubitat_ci.app.AppValidator
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import groovy.transform.TypeChecked

@TypeChecked
class HRef {
    final String nextPageName
    final Map options

    /**
     * Returns page this href points to (if any).
     * @return null, if page is not specified anywhere, or, of course, page name.
     */
    String readPage()
    {
        return nextPageName ? nextPageName : options?.page;
    }

    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter("title", notRequired(), mustNotBeEmpty())
        boolParameter("required", notRequired())
        stringParameter("description", notRequired(), mustNotBeEmpty())
        enumStringParameter("style", notRequired(), ["external", "embedded", "page"])
        stringParameter("url", notRequired(), mustNotBeEmpty())

        mapParameter("params", notRequired())

        stringParameter("page", notRequired(), mustNotBeEmpty())
        stringParameter("image", notRequired(), mustNotBeEmpty())
    }

    HRef(Map options, String nextPageName, EnumSet<Flags> validationFlags)
    {
        this.options = options
        this.nextPageName = nextPageName

        validate(validationFlags)
    }

    HRef(String nextPageName) {
        this.nextPageName = nextPageName
    }

    HRef(Map options, EnumSet<Flags> validationFlags) {
        this.options = options

        validate(validationFlags)
    }

    private void validate(EnumSet<Flags> validationFlags)
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validationFlags)

            // Extra validations ('page' is not compatible with 'style: external'):
            if (options?.containsKey('page'))
            {
                assert options?.style != "external" : "${this}: contains 'page' option (which points to internal page), and style == external. They are incompatible."
            }
        }
    }


    @Override
    String toString()
    {
        return "href(nextPageName: ${nextPageName}, options: ${options})"
    }
}
