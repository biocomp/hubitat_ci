package biocomp.hubitatCi.app.preferences


import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
class Preferences
{
    @CompileStatic
    void assignOptions(Map options) {
        if (options != null) {
            this.options.putAll(options)
        }
    }

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

    @CompileStatic
    List<Input> getAllInputs()
    {
        return (List<Input>)allPages.collect{
            it.sections.collect{ it.children.findAll{ it instanceof Input }.collect{ (Input)it } }
        }.flatten()
    }

    final Map options = [:]

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