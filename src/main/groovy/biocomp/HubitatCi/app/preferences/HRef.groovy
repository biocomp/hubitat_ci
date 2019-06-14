package biocomp.hubitatCi.app.preferences

import biocomp.hubitatCi.app.AppValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.NamedParametersValidator
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

    HRef(Map options, String nextPageName, AppValidator validator)
    {
        this.options = options
        this.nextPageName = nextPageName

        validate(validator)
    }

    HRef(String nextPageName) {
        this.nextPageName = nextPageName
    }

    HRef(Map options, AppValidator validator) {
        this.options = options

        validate(validator)
    }

    private void validate(AppValidator validator)
    {
        if (!validator.hasFlag(Flags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, validator)

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
