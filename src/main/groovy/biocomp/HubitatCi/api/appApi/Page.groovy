package biocomp.hubitatCi.api.appApi

interface Page {
    /**
     * Adds section to a page.
     * @see #section(Map, String, Closure)
     */
    abstract def section(String sectionTitle, @DelegatesTo(Section.class) Closure makeContents)

    /**
     * @see #section(Map, String, Closure)
     */
    abstract def section(@DelegatesTo(Section.class) Closure makeContents)

    /**
     * Adds section to a page
     *
     * @param options. Valid options are:
     *                 <ul>
     *                 <li><code>hideable</code> (Boolean) - Pass true to allow the section to be collapsed. Defaults to false.</li>
     *                 <li><code>hidden</code> (Boolean) - Pass true to specify the section is collapsed by default. Used in conjunction with hideable.
     *                 Defaults to false.</li>
     *                 <li><code>mobileOnly</code> (Boolean) - Pass true to suppress this section from the IDE simulator. Defaults to false.</li>
     *                 </ul>
     */
    abstract def section(Map options, String sectionTitle,
                           @DelegatesTo(Section.class) Closure makeContents)

    /**
     * @see #section(Map, String, Closure)
     */
    abstract def section(Map options,
                           @DelegatesTo(Section.class) Closure makeContents)
}

