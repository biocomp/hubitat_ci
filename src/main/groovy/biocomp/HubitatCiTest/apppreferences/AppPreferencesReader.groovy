package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.emulation.appApi.Preferences as PreferencesInterface
import biocomp.hubitatCiTest.HubitatAppScript
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.emulation.appApi.DynamicPage
import groovy.transform.TypeChecked

@TypeChecked
class AppPreferencesReader implements
        AppExecutor
{
    AppPreferencesReader(
            HubitatAppScript parentScript,
            AppExecutor delegate,
            EnumSet<ValidationFlags> validationFlags,
            Map userSettingsValue)
    {
        this.parentScript = parentScript
        this.delegate = delegate
        this.validationFlags = validationFlags

        this.settingsContainer = new SettingsContainer(prefState, validationFlags, userSettingsValue);
    }

    Preferences getProducedPreferences() {
        if (!validationFlags.contains(ValidationFlags.DontValidatePreferences)) {
            assert preferences_: "preferences() method was never called or failed. Either way, you can't get the preferences."
        }

        return preferences_
    }

    /*

    dynamicPage()

     */

    @Override
    Map dynamicPage(Map options, @DelegatesTo(DynamicPage) Closure makeContents) {
        prefState.currentPreferences.dynamicPages << prefState.initWithPage(
                new Page(prefState.currentPreferences.dynamicPages.size(), null, null, options, prefState.currentDynamicMethod), makeContents)

        return null
    }

    /*

    preferences()

     */

    @Override
    def preferences(@DelegatesTo(Preferences) Closure makeContents) {
        readPreferencesImpl(null, makeContents)
    }

    @Override
    def preferences(Map options, @DelegatesTo(PreferencesInterface) Closure makeContents) {
        readPreferencesImpl(options, makeContents)
    }

    /*

    page() + preferences()

     */

    @Override
    def page(
            String name, String title,
            @DelegatesTo(biocomp.hubitatCiTest.emulation.appApi.Page) Closure makeContents)
    {
        prefState.currentPreferences.pages << prefState.initWithPage(
                new Page(prefState.currentPreferences.pages.size(), name, title, null), makeContents)
    }

    @Override
    def page(Map options, @DelegatesTo(biocomp.hubitatCiTest.emulation.appApi.Page) Closure makeContents) {
        prefState.currentPreferences.pages << prefState.initWithPage(
                new Page(prefState.currentPreferences.pages.size(), null, null, options), makeContents)
    }

    @Override
    def page(Map options, String name, String title, @DelegatesTo(biocomp.hubitatCiTest.emulation.appApi.Page.class) Closure makeContents)
    {
        prefState.currentPreferences.pages << prefState.initWithPage(
                new Page(prefState.currentPreferences.pages.size(), name, title, options), makeContents)
    }


    /**
     * Overload for a dynamic page creation - takes only options with name of the method.
     * @param options
     * @return
     */
    @Override
    def page(Map options) {
        Page.dynamicPageInitialParamValidator.validate(this.toString(), options)

        // Now need to run named closure that is adding dynamic pages
        def methodWithNoArgs = parentScript.getMetaClass().pickMethod(options.name as String, [] as Class[])
        if (methodWithNoArgs) {
            prefState.currentPreferences.dynamicRegistrations << {
                prefState.withCurrentDynamicMethod(options.name as String, { methodWithNoArgs.invoke(parentScript) })
            }
            return null
        } else {
            def methodWithMapArg = parentScript.getMetaClass().pickMethod(options.name as String,
                    [Map.class] as Class[])
            if (methodWithMapArg) {
                prefState.currentPreferences.dynamicRegistrations << {
                    prefState.withCurrentDynamicMethod(options.name as String, { methodWithNoArgs.invoke(parentScript, [:]) })
                }
                return null
            }
        }

        assert false: "${this}: page '${options.name}' does not point to any method to create dynamic pages"
    }

    @Override
    def section(
            Map options,
            @DelegatesTo(biocomp.hubitatCiTest.emulation.appApi.Section.class) Closure makeContents)
    {
        addSectionImpl(null, options, makeContents)
    }

    @Override
    def section(
            String sectionTitle,
            @DelegatesTo(biocomp.hubitatCiTest.emulation.appApi.Section) Closure makeContents)
    {
        addSectionImpl(sectionTitle, null, makeContents)
    }

    private void addSectionImpl(String sectionTitle, Map options, Closure makeContents) {
        if (prefState.hasCurrentPage()) {
            prefState.currentPage.sections << prefState.initWithSection(
                    new Section(prefState.currentPage.sections.size(), sectionTitle, options), makeContents)
        } else {
            this.prefState.initWithPage(prefState.currentPreferences.specialSinglePage, {
                prefState.currentPage.sections << prefState.initWithSection(
                        new Section(prefState.currentPage.sections.size(), sectionTitle, options), makeContents)
            },
                    false)
        }
    }

    /*

    section()

     */

    @Override
    def section(
            Map options, String sectionTitle,
            @DelegatesTo(biocomp.hubitatCiTest.emulation.appApi.Section) Closure makeContents)
    {
        addSectionImpl(sectionTitle, options, makeContents)
    }

    @Override
    def section(
            @DelegatesTo(biocomp.hubitatCiTest.emulation.appApi.Section) Closure makeContents)
    {
        addSectionImpl(null, null, makeContents)
    }

    //
    //    @Override
    //    def section(
    //            String sectionTitle,
    //            @DelegatesTo(Section) Closure makeContents)
    //    {
    //
    //        println "Page adding section with just title = ${sectionTitle}"
    //        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle), makeContents)
    //    }
    //
    //    @Override
    //    def section(
    //            Map options, String sectionTitle,
    //            @DelegatesTo(Section) Closure makeContents)
    //    {
    //        println "Page adding section with title = ${sectionTitle} and options = ${options}"
    //        sections << Utility.runClosureAndValidate(new Section(sections.size(), sectionTitle, options), makeContents)
    //    }

    /*

    input()

     */

    @Override
    Object input(Map options, String name, String type) {
        prefState.currentSection.children << new Input(options, name, type, validationFlags)
        settingsContainer.userInputFound(name)
    }

    @Override
    Object input(String name, String type) {
        prefState.currentSection.children << new Input(null, name, type, validationFlags)
        settingsContainer.userInputFound(name)
    }

    @Override
    Object input(Map options) {
        prefState.currentSection.children << new Input(options, null, null, validationFlags)
        settingsContainer.userInputFound(options.name as String)
    }

    /*

    href

     */

    @Override
    def href(String nextPageName)
    {
        prefState.currentSection.children << new HRef(nextPageName)
    }

    @Override
    def href(Map options)
    {
        prefState.currentSection.children << new HRef(options, validationFlags)
    }

    @Override
    def href(Map options, String title)
    {
        prefState.currentSection.children << new HRef(options, title, validationFlags)
    }

    /*

    label()

     */

    @Override
    def label(Map options) {
        prefState.currentSection.children << new Label(options, validationFlags)
    }

    //
    //    @Override
    //    def mode(Map options) {
    //        return null
    //    }
    //
    /*

    paragraph()

     */

    @Override
    def paragraph(Map options) {
        prefState.currentSection.children << new Paragraph(null, options, validationFlags)
    }

    @Override
    def paragraph(String text) {
        prefState.currentSection.children << new Paragraph(text, null, validationFlags)
    }

    /*

    settings

    */

    @Override
    Map getSettings() {
        return settingsContainer
    }


    /*

    Private methods

    */

    private void readPreferencesImpl(Map options, @DelegatesTo(PreferencesInterface) Closure makeContents) {
        def newPreferences = new Preferences(parentScript, null)
        preferences_ = prefState.initWithPreferences(newPreferences,
                {
                    makeContents()
                    newPreferences.registerDynamicPages(validationFlags)
                })

        settingsContainer.validateAfterPreferences()
    }

    /*

    Private data

     */

    private final EnumSet<ValidationFlags> validationFlags

    private final PreferencesReaderState prefState = new PreferencesReaderState(validationFlags)

    private final SettingsContainer settingsContainer

    private Preferences preferences_ = null

    private final HubitatAppScript parentScript;

    @Delegate
    final private AppExecutor delegate;
}