package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
import biocomp.hubitatCiTest.util.Utility
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
@ToString
class Page implements biocomp.hubitatCiTest.emulation.appApi.DynamicPage
{
    int index
    String name
    String title
    Map options
    final boolean isDynamicPage = false

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

    void validate() {
        options = NamedParametersValidator.addOptionAsNamedParam(options, "name", name)
        options = NamedParametersValidator.addOptionAsNamedParam(options, "title", title)

        if (!isDynamicPage) {
            paramValidator.validate(this.toString(), options)
        } else {
            dynamicPageParamValidator.validate(this.toString(), options)
        }
        assert sections.size() != 0: "Page ${this} must have at least one section"
    }
}
