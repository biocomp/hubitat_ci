package me.biocomp.hubitat_ci.app.preferences


import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
class Preferences
{
    /**
     * Pages for multiple-page apps.*/
    private final List<Page> pages = []

    /**
     * Dynamic pages for multiple-page apps.*/
    private final List<Page> dynamicPages = []

    /**
     * Pages + dynamic pages in order of declaration
     */
    private final List<Page> allPages = []

    final Map options = [:]

    /**
     * Used when preferences().section() is used directly.
     * Not compatible with multiple pages.*/
    Page specialSinglePage = null

    @CompileStatic
    void assignOptions(Map options) {
        if (options != null) {
            this.options.putAll(options)
        }
    }

    void addPage(Page p)
    {
        pages << p
        allPages << p
    }

    void addDynamicPage(Page p)
    {
        dynamicPages << p
        allPages << p
    }

    List<Page> getPages()
    {
        return pages
    }

    List<Page> getDynamicPages()
    {
        return dynamicPages
    }

    List<Page> getAllPages()
    {
        return allPages
    }

    @CompileStatic
    List<Input> getAllInputs()
    {
        return (List<Input>)allPages.collect{
            it.sections.collect{ it.children.findAll{ it instanceof Input }.collect{ (Input)it } }
        }.flatten()
    }

    boolean hasSpecialSinglePage()
    {
        return this.@specialSinglePage != null
    }

    Page getSpecialSinglePage() {
        assert pages.size() == 0 || pages.size() == 1 && pages[0] == this.@specialSinglePage: "You can't use single-page section() calls when you already added pages: ${pages}"

        if (!hasSpecialSinglePage()) {
            this.@specialSinglePage = Page.makeSinglePage()
            addPage(this.@specialSinglePage)
        }

        return this.@specialSinglePage
    }
}