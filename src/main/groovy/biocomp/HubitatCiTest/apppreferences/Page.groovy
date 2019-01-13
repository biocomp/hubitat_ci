package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
import biocomp.hubitatCiTest.util.Utility
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
class Page implements biocomp.hubitatCiTest.emulation.appApi.DynamicPage
{
    int index
    String name
    String title
    Map options
    final String generationMethodName = null

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
        stringParameter(name: "name", required: true)
        stringParameter(name: "title", required: true)
        stringParameter(name: "nextPage")
        boolParameter(name: "install")
        boolParameter(name: "uninstall")
    }

    private static final NamedParametersValidator dynamicPageParamValidator = NamedParametersValidator.make {
        add(paramValidator)
        intParameter(name: "refreshInterval")
    }

    public static final NamedParametersValidator dynamicPageInitialParamValidator = NamedParametersValidator.make{
        stringParameter(name:"name", required:true)
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

    void validate(EnumSet<ValidationFlags> flags) {
        options = NamedParametersValidator.addOptionAsNamedParam(options, "name", name)
        options = NamedParametersValidator.addOptionAsNamedParam(options, "title", title)

        if (!flags.contains(ValidationFlags.DontValidatePreferences)) {
            if (!isDynamicPage()) {
                paramValidator.validate(this.toString(), options)
            } else {
                dynamicPageParamValidator.validate(this.toString(), options)

                assert generationMethodName == readName() : "Page ${this}'s name does not match the method it was triggered with: ${generationMethodName}"
            }

            assert sections.size() != 0: "Page ${this} must have at least one section"
        }
    }
}
