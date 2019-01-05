package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.emulation.AppPage
import biocomp.hubitatCiTest.emulation.AppPreferences
import biocomp.hubitatCiTest.emulation.AppSection
import biocomp.hubitatCiTest.util.Utility
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

//
//@CompileStatic
//class InPreferencesState implements State
//{
//    Preferences preferences = new Preferences()
//
//    @Override
//    void finalValidation()
//    {
//        assert childrenCount != 0 : "${this} contents can't be empty"
//    }
//
//    @Override
//    int getChildrenCount() { return preferences.pages.size() + preferences.sections.size() }
//
//    @Override
//    String toString() { return "preferences()" }
//
//    @Override
//    void addChild(State child)
//    {
//        if (child instanceof InPageState)
//        {
//            preferences.pages << (child as InPageState).page
//        }
//        else if (child instanceof InSectionState)
//        {
//            preferences.sections << (child as InSectionState).section
//        }
//    }
//}

@TypeChecked
class Preferences implements AppPreferences
{
    Preferences(Map options)
    {}

    List<Page> pages = []
    List<Section> sections = []

    /**
     * Run validations after all pages and sections were added
     */
    void validate()
    {
        assert pages.size() != 0 || sections.size() != 0 : "preferences() has to have pages (got ${pages.size()}) or sections (got ${sections.size()})"
    }

    @Override
    def page(String name, String title,
             @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppPage) Closure makeContents) {
        pages << Utility.runClosureAndValidate(new Page(pages.size(), name, title, null), makeContents)
    }

    @Override
    def page(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value=AppPage) Closure makeContents) {
        pages << Utility.runClosureAndValidate(new Page(pages.size(), null, null, options), makeContents)
    }

    @Override
    def section(String sectionTitle,
                @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value= AppSection) Closure makeContents) {
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle), makeContents)
    }

    @Override
    def section(String sectionTitle, Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY,
            value=AppSection) Closure makeContents) {
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle), makeContents)
    }
}