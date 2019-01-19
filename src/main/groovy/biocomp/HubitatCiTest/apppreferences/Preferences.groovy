package biocomp.hubitatCiTest.apppreferences


import biocomp.hubitatCiTest.validation.Flags
import biocomp.hubitatCiTest.validation.Validator
import groovy.transform.TypeChecked

@TypeChecked
class Preferences
{
    Preferences(Script parentScript, Map options) {
        this.parentScript = parentScript
        this.options = options
    }

    final Script parentScript

    /**
     * Pages for multiple-page apps.*/
    final List<Page> pages = []

    /**
     * Dynamic pages for multiple-page apps.*/
    final List<Page> dynamicPages = []


    /**
     * Methods to be called to generate dynamic pages.
     * Called via registerDynamicPages()*/
    final List<Closure> dynamicRegistrations = []

    final Map options = null

    /**
     * Used when preferences().section() is used directly.
     * Not compatible with multiple pages.*/
    Page specialSinglePage = null

    boolean hasSpecialSinglePage()
    {
        return this.@specialSinglePage != null
    }

    List<Page> getAllPages()
    {
        return pages + dynamicPages
    }

    Page getSpecialSinglePage() {
        assert pages.size() == 0 || pages.size() == 1 && pages[0] == this.@specialSinglePage: "You can't use single-page section() calls when you already added pages: ${pages}"

        if (!hasSpecialSinglePage()) {
            this.@specialSinglePage = Page.makeSinglePage()
            pages << this.@specialSinglePage
        }

        return this.@specialSinglePage
    }
}