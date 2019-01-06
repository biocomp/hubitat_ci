package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.emulation.AppDynamicPage
import biocomp.hubitatCiTest.emulation.AppSection
import biocomp.hubitatCiTest.util.NamedParametersValidator
import biocomp.hubitatCiTest.util.Utility
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
@ToString
class Page implements AppDynamicPage {
    int index
    String name
    String title
    Map options
    final boolean isDynamicPage = false

    private final NamedParametersValidator paramValidator = NamedParametersValidator.make{
        stringParameter(name:"name", required:true)
        stringParameter(name:"title", required:true)
        stringParameter(name:"nextPage")
        boolParameter(name:"install")
        boolParameter(name:"uninstall")
    }

    List<Section> sections = []

    void validate()
    {
        options = NamedParametersValidator.addOptionAsNamedParam(options, "name", name)
        options = NamedParametersValidator.addOptionAsNamedParam(options, "title", title)

        paramValidator.validate(this.toString(), options)
        assert sections.size() != 0 : "Page ${this} must have at least one section"
    }

    @Override
    def section(String sectionTitle,
                @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppSection) Closure makeContents) {

        println "Page adding section with just title = ${sectionTitle}"
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle), makeContents)
    }

    @Override
    def section(Map options, String sectionTitle,
                @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppSection) Closure makeContents) {
        println "Page adding section with title = ${sectionTitle} and options = ${options}"
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle, options), makeContents)
    }
}
