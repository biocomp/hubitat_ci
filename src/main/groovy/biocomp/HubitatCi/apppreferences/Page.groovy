package biocomp.hubitatCi.apppreferences


import biocomp.hubitatCi.validation.NamedParametersValidator
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.AppValidator
import groovy.transform.TypeChecked

@TypeChecked
class Page
{
    final int index
    final String name
    final String title
    final Map options
    final String generationMethodName

    Page(int index, String name, String title, Map options, String generationMethodName = null) {
        this.index = index
        this.name = name
        this.title = title
        this.options = options
        this.generationMethodName = generationMethodName

        this.options = NamedParametersValidator.addOptionAsNamedParam(this.options, "name", name)
        this.options = NamedParametersValidator.addOptionAsNamedParam(this.options, "title", title)
    }

    @Override
    String toString()
    {
        return "page #${index}(name: ${name}, title: ${title}, options: ${options})"
    }


    /**
     * Returns either 'name' or (if 'name' is null) options.name
     * @return
     */
    String readName()
    {
        if (name != null)
        {
            return name
        }

        return options?.name
    }

    private static final NamedParametersValidator paramValidator = NamedParametersValidator.make {
        stringParameter("name", required(), mustNotBeEmpty())
        stringParameter("title", required(), mustNotBeEmpty())
        stringParameter("nextPage", notRequired(), mustNotBeEmpty())
        boolParameter("install", notRequired())
        boolParameter("uninstall", notRequired())
        boolParameter("hideWhenEmpty", notRequired())
    }

    private static final NamedParametersValidator dynamicPageParamValidator = NamedParametersValidator.make {
        add(paramValidator)
        intParameter("refreshInterval", notRequired())
    }

    public static final NamedParametersValidator dynamicPageInitialParamValidator = NamedParametersValidator.make{
        stringParameter("name", required(), mustNotBeEmpty())
    }

    public static final NamedParametersValidator dynamicPageInitialParamValidatorWithTitle = NamedParametersValidator.make{
        add(dynamicPageInitialParamValidator)
        stringParameter("title", notRequired(), mustNotBeEmpty())
    }

    List<Section> sections = []

    static Page makeSinglePage()
    {
        return new Page(0, 'special_single_page', 'special_single_page_title', null)
    }

    boolean isDynamicPage()
    {
        return generationMethodName != null
    }

    void validate(AppValidator validator) {
        if (!validator.hasFlag(Flags.DontValidatePreferences)) {
            if (!isDynamicPage()) {
                paramValidator.validate(this.toString(), options, validator)
            } else {
                dynamicPageParamValidator.validate(this.toString(), options, validator)

                assert generationMethodName == readName() : "Page ${this}'s name does not match the method it was triggered with: ${generationMethodName}"
            }

            assert sections.size() != 0: "Page ${this} must have at least one section"
        }
    }
}
