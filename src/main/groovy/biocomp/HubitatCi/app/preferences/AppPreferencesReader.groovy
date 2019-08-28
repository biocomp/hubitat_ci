package me.biocomp.hubitat_ci.app.preferences

import biocomp.hubitatCi.api.app_api.AppExecutor
import biocomp.hubitatCi.api.app_api.DynamicPage
import biocomp.hubitatCi.api.app_api.Preferences as PreferencesInterface
import biocomp.hubitatCi.app.AppValidator
import biocomp.hubitatCi.app.HubitatAppScript
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.IInputSource
import biocomp.hubitatCi.validation.SettingsContainer
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
class AppPreferencesReader implements
        AppExecutor
{
    AppPreferencesReader(
            HubitatAppScript parentScript,
            AppExecutor delegate,
            AppValidator validator,
            Map userSettingsValue,
            Preferences preferncesToFill,
            IInputSource inputSource)
    {
        this.parentScript = parentScript
        this.delegate = delegate
        this.validator = validator
        this.preferences_ = preferncesToFill

        assert preferncesToFill, "AppPreferencesReader will fill out existing Preferences object from AppData"

        this.settingsContainer = new SettingsContainer(
                { prefState.hasCurrentPage() ? prefState.currentPage.readName() : null },
                validator,
                userSettingsValue,
                inputSource);
    }

    @CompileStatic
    void validateAfterRun() {
        if (!validator.hasFlag(Flags.DontValidatePreferences)) {
            assert preferencesCalled: "preferences() method was never called or failed. Either way, you can't get the preferences."
        }
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
            @DelegatesTo(biocomp.hubitatCi.api.app_api.Page) Closure makeContents)
    {
        prefState.currentPreferences.pages << prefState.initWithPage(
                new Page(prefState.currentPreferences.pages.size(), name, title, null), makeContents)
    }

    @Override
    def page(Map options, @DelegatesTo(biocomp.hubitatCi.api.app_api.Page) Closure makeContents) {
        prefState.currentPreferences.pages << prefState.initWithPage(
                new Page(prefState.currentPreferences.pages.size(), null, null, options), makeContents)
    }

    @Override
    def page(Map options, String name, String title, @DelegatesTo(biocomp.hubitatCi.api.app_api.Page.class) Closure makeContents)
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
                    options, validator.flags)
        }
        else
        {
            Page.dynamicPageInitialParamValidator.validate("page(${options}) - special case of reference to method",
                    options, validator.flags)
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
            @DelegatesTo(biocomp.hubitatCi.api.app_api.Section.class) Closure makeContents)
    {
        addSectionImpl(null, options, makeContents)
    }

    @Override
    def section(
            String sectionTitle,
            @DelegatesTo(biocomp.hubitatCi.api.app_api.Section) Closure makeContents)
    {
        addSectionImpl(sectionTitle, null, makeContents)
    }

    @CompileStatic
    private void addSectionImpl(String sectionTitle, Map options, Closure makeContents) {
        if (prefState.hasCurrentPage()) {
            prefState.currentPage.sections.add(prefState.initWithSection(
                    new Section(prefState.currentPage.sections.size(), sectionTitle, options), makeContents))
        } else {
            this.prefState.initWithPage(prefState.currentPreferences.specialSinglePage, {
                prefState.currentPage.sections.add(prefState.initWithSection(
                        new Section(prefState.currentPage.sections.size(), sectionTitle, options), makeContents))
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
            @DelegatesTo(biocomp.hubitatCi.api.app_api.Section) Closure makeContents)
    {
        addSectionImpl(sectionTitle, options, makeContents)
    }

    @Override
    def section(
            @DelegatesTo(biocomp.hubitatCi.api.app_api.Section) Closure makeContents)
    {
        addSectionImpl(null, null, makeContents)
    }

    /*

    input()

     */

    @Override
    Object input(Map options, String name, String type) {
        def input = new Input([name: name, type: type], options, validator.flags)
        prefState.currentSection.children << input
    }

    @Override
    Object input(String name, String type) {
        def input = new Input([name: name, type: type], null, validator.flags)
        prefState.currentSection.children << input
    }

    @Override
    Object input(Map options) {
        def input = new Input([:], options, validator.flags)
        prefState.currentSection.children << input
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
        prefState.currentSection.children << new HRef(options, validator.flags)
    }

    @Override
    def href(Map options, String nextPageName)
    {
        prefState.currentSection.children << new HRef(options, nextPageName, validator.flags)
    }

    /*

    label()

     */

    @Override
    def label(Map options) {
        prefState.currentSection.children << new Label(options, validator.flags)
    }


    /*

    mode()

    */


    @Override
    def mode(Map options)
    {
        prefState.currentSection.children << new Mode(options, validator.flags)
    }

    /*

    paragraph()

     */

    @Override
    def paragraph(Map options, String text) {
        prefState.currentSection.children << new Paragraph(text, options, validator.flags)
    }

    @Override
    def paragraph(String text) {
        prefState.currentSection.children << new Paragraph(text, null, validator.flags)
    }

    /*

    app()

     */

    @Override
    def app(Map options) {
        prefState.currentSection.children << new App(options, null, null, null, validator.flags)
    }

    @Override
    def app(String name, String namespace, String title) {
        prefState.currentSection.children << new App(null, name, namespace, title, validator.flags)
    }

    @Override
    def app(Map options, String name, String namespace, String title) {
        prefState.currentSection.children << new App(options, name, namespace, title, validator.flags)
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
        preferencesCalled = true

        preferences_.assignOptions(options)
        prefState.initWithPreferences(preferences_,
                {
                    makeContents()
                    registerDynamicPages(preferences_)
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

    private boolean preferencesCalled = false
    private final Preferences preferences_

    private final HubitatAppScript parentScript;

    @Delegate
    final private AppExecutor delegate;
}