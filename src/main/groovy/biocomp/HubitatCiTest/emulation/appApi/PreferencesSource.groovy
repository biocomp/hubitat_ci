package biocomp.hubitatCiTest.emulation.appApi;

import groovy.lang.Closure;

import java.util.Map;

/**
 * Entry point for all preferences() related calls.
 */
interface PreferencesSource extends Preferences, Page, Section {
    /**
     * Adds dynanmic page
     *
     * @param options. Same as for page(), and one extra:
     *                 refreshInterval (Integer) - refreshes the specific page of the SmartApp on
     *                 the mobile device for the integer number of seconds
     *                 */
    abstract def dynamicPage(Map options, Closure makeContents)

    /**
     * Preferences for the appApi
     */
    abstract def preferences(Closure makeContents)

    /**
     * Preferences for the appApi
     *
     * @param options. Not clear which ones are supported.
     */
    abstract def preferences(Map options, Closure makeContents)
}
