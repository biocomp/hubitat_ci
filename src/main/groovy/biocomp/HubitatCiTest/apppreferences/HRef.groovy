package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
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
        stringParameter(name: "title", required: false, canBeEmpty: false)
        boolParameter(name: "required")
        stringParameter(name: "description", required: false, canBeEmpty: false)
        enumStringParameter(name: "style", required: false, values: ["external", "embedded", "page"])
        stringParameter(name: "url", required: false, canBeEmpty: false)

        mapParameter(name: "params", required: false)

        stringParameter(name: "page", canBeEmpty: false)
        stringParameter(name: "image", canBeEmpty: false)
    }

    HRef(Map options, String nextPageName, EnumSet<ValidationFlags> flags)
    {
        this.options = options
        this.nextPageName = nextPageName

        validate(flags)
    }

    HRef(String nextPageName) {
        this.nextPageName = nextPageName
    }

    HRef(Map options, EnumSet<ValidationFlags> flags) {
        this.options = options

        validate(flags)
    }

    private void validate(EnumSet<ValidationFlags> flags)
    {
        if (!flags.contains(ValidationFlags.DontValidatePreferences)) {
            paramValidator.validate(this.toString(), options, flags, false)

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
