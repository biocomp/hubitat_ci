package me.biocomp.hubitat_ci.api.app_api

interface Preferences extends Page {
    /**
     * Adds page of settings.
     * @see #page(Map, Closure)
     * */
    abstract Object page(String name, String title, @DelegatesTo(Page.class) Closure makeContents)

    /**
     * @see #page(Map, Closure)
     */
    abstract Object page(Map options, String name, String title, @DelegatesTo(Page.class) Closure makeContents)

    /**
     * Adds page of settings.
     *
     * @param options. Valid options are:
     * <ul>
     *    <li><code>name</code> (required) (String) - Identifier for this page.</li>
     *   <li><code>title</code> (String) - The display title of this page.</li>
     *   <li><code>nextPage</code> (String) - Used on multi-page preferences only. Should be the name of the page to navigate to next.</li>
     *   <li><code>install</code> (Boolean) - Set to true to allow the user to install this app_api from this page. Defaults to false. Not necessary for single-page preferences.</li>
     *   <li><code>uninstall</code> (Boolean) - Set to true to allow the user to uninstall from this page. Defaults to false. Not necessary
     *   for single-page preferences.</li>
     *  </ul>
     */
    abstract Object page(Map options, @DelegatesTo(Page.class) Closure makeContents)


    /**
     * Creates dynamic page. In this case, page's 'name' must point to a method.
     * @param options (see other page() method). But 'name' must be a name of method that creates dynamic page.
     * @see #page(Map, Closure)
     * @return
     */
    abstract Object page(Map options)
}