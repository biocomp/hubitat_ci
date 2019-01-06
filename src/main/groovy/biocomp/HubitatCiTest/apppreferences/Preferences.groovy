package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.emulation.AppDynamicPage
import biocomp.hubitatCiTest.emulation.AppPage
import biocomp.hubitatCiTest.emulation.AppPreferences
import biocomp.hubitatCiTest.emulation.AppSection
import biocomp.hubitatCiTest.util.NamedParametersValidator
import biocomp.hubitatCiTest.util.Utility
import com.sun.istack.internal.NotNull
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
    Preferences(@NotNull Script parentScript, Map options)
    {
        this.parentScript = parentScript
    }

    final Script parentScript
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

    static private final NamedParametersValidator dynamicPageParamValidator = NamedParametersValidator.make{
        stringParameter(name:"name", required:true)
    }

    @Override
    def page(Map options) {
        dynamicPageParamValidator.validate(this.toString(), options)

        // Now need to run named closure that is adding dynamic pages
        List<MetaMethod> metaMethods = parentScript.metaClass.methods.findAll{ options.name as String == it.name }
        assert metaMethods.size() == 1 : "page(name: \"${options.name})\" points to ${metaMethods.size()} method(s) of the script. But it needs strictly one."

        if (metaMethods[0].parameterTypes.size() == 1)
        {
            metaMethods[0].invoke(parentScript, [:])
        }
        else
        {
            metaMethods[0].invoke(parentScript)
        }
    }

    @Override
    def section(String sectionTitle,
                @DelegatesTo(strategy=Closure.DELEGATE_ONLY, value= AppSection) Closure makeContents) {

        println "Preferences adding section with just title = '${sectionTitle}'"
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle), makeContents)
    }

    @Override
    def section(Map options, String sectionTitle, @DelegatesTo(strategy=Closure.DELEGATE_ONLY,
            value=AppSection) Closure makeContents) {
        println "Preferences adding section with title = ${sectionTitle} and options = ${options}"
        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle, options), makeContents)
    }

    void addDynamicPage(Map options, @DelegatesTo(strategy=Closure.DELEGATE_ONLY,
            value= AppDynamicPage) Closure makeContents) {
        pages << Utility.runClosureAndValidate(new Page(sections.size(), null, null, options, true), makeContents)
    }
}