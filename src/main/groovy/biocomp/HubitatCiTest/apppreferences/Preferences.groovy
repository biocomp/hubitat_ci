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

    /**
     * Pages for multiple-page apps.
     */
    final List<Page> pages = []

    /**
     * Dynamic pages for multiple-page apps.
     */
    final List<Page> dynamicPages = []


    /**
     * Methods to be called to generate dynamic pages.
     * Called via registerDynamicPages()
     */
    final List<Closure> dynamicRegistrations = []

    /**
     * Sections for single-page apps
     */
    List<Section> sections = []

    /**
     * Run validations after all pages and sections were added
     */
    void validate(boolean okIfRegisteredDynamicPages = true)
    {
        if (!okIfRegisteredDynamicPages || dynamicRegistrations.size() == 0) {
            assert (pages.size() != 0 || sections.size() != 0 || dynamicPages != 0): "preferences() has to have pages (got ${pages.size()}), dynamic pages (got ${dynamicPages.size()}) or sections (got ${sections.size()})"
        }
    }

    /**
     * Calls methods that create dymaic pages that were discovered previously
     */
    void registerDynamicPages()
    {
        dynamicRegistrations.each { it() }

        // Now validate and make sure something was actually created, not just methods registered.
        validate(false)
    }

    @Override
    def page(String name, String title,
             @DelegatesTo(AppPage) Closure makeContents) {
        pages << Utility.runClosureAndValidate(new Page(pages.size(), name, title, null), makeContents)
    }

    @Override
    def page(Map options, @DelegatesTo(AppPage) Closure makeContents) {
        pages << Utility.runClosureAndValidate(new Page(pages.size(), null, null, options), makeContents)
    }

    static private final NamedParametersValidator dynamicPageParamValidator = NamedParametersValidator.make{
        stringParameter(name:"name", required:true)
    }

    /**
     * Overload for a dynamic page creation - takes only options with name of the method.
     * @param options
     * @return
     */
    @Override
    def page(Map options) {
        dynamicPageParamValidator.validate(this.toString(), options)

        // Now need to run named closure that is adding dynamic pages
        def methodWithNoArgs = parentScript.getMetaClass().pickMethod(options.name as String, [] as Class[])
        if (methodWithNoArgs)
        {
            dynamicRegistrations << { methodWithNoArgs.invoke(parentScript) }
            return
        }
        else
        {
            def methodWithMapArg = parentScript.getMetaClass().pickMethod(options.name as String, [Map.class] as Class[])
            if (methodWithMapArg) {
                dynamicRegistrations << { methodWithMapArg.invoke(parentScript, [:]) }
                return
            }
        }

        assert false : "${this}: page '${options.name}' does not point to any method to create dynamic pages"
    }

    @Override
    def section(String sectionTitle,
                @DelegatesTo( AppSection) Closure makeContents) {

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
        dynamicPages << Utility.runClosureAndValidate(new Page(sections.size(), null, null, options, true), makeContents)
    }
}