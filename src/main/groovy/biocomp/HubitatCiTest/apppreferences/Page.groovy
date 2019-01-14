package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
import groovy.transform.TupleConstructor
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
