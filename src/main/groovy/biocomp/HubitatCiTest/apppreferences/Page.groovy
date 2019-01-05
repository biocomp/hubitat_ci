package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.emulation.AppPage
import biocomp.hubitatCiTest.emulation.AppSection
import biocomp.hubitatCiTest.util.Utility
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
@ToString
class Page implements AppPage {
    int index
    String name
    String title
    Map options

    static private final HashMap<String, Closure<Void>> validOptionsAndValidators = [
            "name": Utility.&nonEmptyStringProperty,
            "nextPage": Utility.&nonEmptyStringProperty,
            "install": Utility.&booleanProperty,
            "uninstall": Utility.&booleanProperty,
    ]

    List<Section> sections = []

    void validate()
    {
        Utility.validateOptionMap(this.toString(), options, validOptionsAndValidators)
        assert sections.size() != 0 : "Page ${this} must have at least one section"
    }

    @Override
    def section(String sectionTitle,
                @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value= AppSection) Closure makeContents) {
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle), makeContents)
    }

    @Override
    def section(String sectionTitle, Map options,
                @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppSection) Closure makeContents) {
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle, options), makeContents)
    }
}
