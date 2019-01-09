package biocomp.hubitatCiTest.apppreferences

import biocomp.hubitatCiTest.util.NamedParametersValidator
import biocomp.hubitatCiTest.util.Utility
import groovy.transform.TypeChecked

@TypeChecked
class Preferences implements biocomp.hubitatCiTest.emulation.appApi.Preferences
{
    Preferences(Script parentScript, Map options)
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
     * Used when preferences().section() is used directly.
     * Not compatible with multiple pages.
     */
    Page specialSinglePage = null

    Page getSpecialSinglePage()
    {
        assert pages.size() == 0 : "You can't use single-page section() calls when you already added pages: ${pages}"

        if (this.@specialSinglePage == null) {
            this.@specialSinglePage = Page.makeSinglePage()
            pages << this.@specialSinglePage
        }

        return this.@specialSinglePage
    }

    /**
     * Run validations after all pages and sections were added
     */
    void validate(boolean okIfRegisteredDynamicPages = true)
    {
        if (this.@specialSinglePage != null)
        {
            this.@specialSinglePage.validate()
        }

        if (!okIfRegisteredDynamicPages || dynamicRegistrations.size() == 0) {
            assert (pages.size() != 0 || dynamicPages.size() != 0): "preferences() has to have pages (got ${pages.size()}), dynamic pages (got ${dynamicPages.size()})"
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
}