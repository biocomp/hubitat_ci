package biocomp.hubitatCi.apppreferences

import biocomp.hubitatCi.emulation.appApi.Preferences as PreferencesInterface
import biocomp.hubitatCi.HubitatAppScript
import biocomp.hubitatCi.emulation.appApi.AppExecutor
import biocomp.hubitatCi.emulation.appApi.DynamicPage
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.AppValidator
import groovy.transform.TypeChecked

@TypeChecked
class AppPreferencesReader implements
        AppExecutor
{
    AppPreferencesReader(
            HubitatAppScript parentScript,
            AppExecutor delegate,
            AppValidator validator,
            Map userSettingsValue)
    {
        this.parentScript = parentScript
        this.delegate = delegate
        this.validator = validator

        this.settingsContainer = new SettingsContainer(prefState, validator, userSettingsValue);
    }

    Preferences getProducedPreferences() {
        if (!validator.hasFlag(Flags.DontValidatePreferences)) {
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
            @DelegatesTo(biocomp.hubitatCi.emulation.appApi.Page) Closure makeContents)
    {
        prefState.currentPreferences.pages << prefState.initWithPage(
                new Page(prefState.currentPreferences.pages.size(), name, title, null), makeContents)
    }

    @Override
    def page(Map options, @DelegatesTo(biocomp.hubitatCi.emulation.appApi.Page) Closure makeContents) {
        prefState.currentPreferences.pages << prefState.initWithPage(
                new Page(prefState.currentPreferences.pages.size(), null, null, options), makeContents)
    }

    @Override
    def page(Map options, String name, String title, @DelegatesTo(biocomp.hubitatCi.emulation.appApi.Page.class) Closure makeContents)
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
        if (validator.hasFlag(Flags.AllowTitleInPageCallingMethods)) {
            Page.dynamicPageInitialParamValidatorWithTitle.validate("page(${options}) - special case of reference to method",
                    options, validator)
        }
        else
        {
            Page.dynamicPageInitialParamValidator.validate("page(${options}) - special case of reference to method",
                    options, validator)
        }

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
            @DelegatesTo(biocomp.hubitatCi.emulation.appApi.Section.class) Closure makeContents)
    {
        addSectionImpl(null, options, makeContents)
    }

    @Override
    def section(
            String sectionTitle,
            @DelegatesTo(biocomp.hubitatCi.emulation.appApi.Section) Closure makeContents)
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
            @DelegatesTo(biocomp.hubitatCi.emulation.appApi.Section) Closure makeContents)
    {
        addSectionImpl(sectionTitle, options, makeContents)
    }

    @Override
    def section(
            @DelegatesTo(biocomp.hubitatCi.emulation.appApi.Section) Closure makeContents)
    {
        addSectionImpl(null, null, makeContents)
    }

    /*

    input()

     */

    @Override
    Object input(Map options, String name, String type) {
        prefState.currentSection.children << new Input([name: name, type: type], options, validator)
        settingsContainer.userInputFound(name)
        validator.validateInput(prefState.currentSection.children.last() as Input)
    }

    @Override
    Object input(String name, String type) {
        prefState.currentSection.children << new Input([name: name, type: type], null, validator)
        settingsContainer.userInputFound(name)
        validator.validateInput(prefState.currentSection.children.last() as Input)
    }

    @Override
    Object input(Map options) {
        prefState.currentSection.children << new Input([:], options, validator)
        settingsContainer.userInputFound(options.name as String)
        validator.validateInput(prefState.currentSection.children.last() as Input)
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
        prefState.currentSection.children << new HRef(options, validator)
    }

    @Override
    def href(Map options, String nextPageName)
    {
        prefState.currentSection.children << new HRef(options, nextPageName, validator)
    }

    /*

    label()

     */

    @Override
    def label(Map options) {
        prefState.currentSection.children << new Label(options, validator)
    }


    /*

    mode()

    */


    @Override
    def mode(Map options)
    {
        prefState.currentSection.children << new Mode(options, validator)
    }

    /*

    paragraph()

     */

    @Override
    def paragraph(Map options, String text) {
        prefState.currentSection.children << new Paragraph(text, options, validator)
    }

    @Override
    def paragraph(String text) {
        prefState.currentSection.children << new Paragraph(text, null, validator)
    }

    /*

    app()

     */

    @Override
    def app(Map options) {
        prefState.currentSection.children << new App(options, null, null, null, validator)
    }

    @Override
    def app(String name, String namespace, String title) {
        prefState.currentSection.children << new App(null, name, namespace, title, validator)
    }

    @Override
    def app(Map options, String name, String namespace, String title) {
        prefState.currentSection.children << new App(options, name, namespace, title, validator)
    }

    /*

    settings

    */

    @Override
    Map getSettings() {
        return settingsContainer
    }
    
    
    @Override
    String toString()
    {
        return "preferences()"
    }


    /*

    Private methods

    */

    private void readPreferencesImpl(Map options, @DelegatesTo(PreferencesInterface) Closure makeContents) {
        def newPreferences = new Preferences(parentScript, options)
        preferences_ = prefState.initWithPreferences(newPreferences,
                {
                    makeContents()
                    registerDynamicPages(newPreferences)
                })

        settingsContainer.preferencesReadingDone()
        settingsContainer.validateAfterPreferences()
    }

    void validateAfterMethodCall()
    {
        settingsContainer.validateAfterPreferences()
    }

    /**
     * Calls methods that create dymaic pages that were discovered previously*/
    void registerDynamicPages(Preferences preferences) {
        preferences.dynamicRegistrations.each { it() }
    }


    /*

    Private data

     */

    private final AppValidator validator

    private final PreferencesReaderState prefState = new PreferencesReaderState(validator)

    private final SettingsContainer settingsContainer

    private Preferences preferences_ = null

    private final HubitatAppScript parentScript;

    @Delegate
    final private AppExecutor delegate;
}