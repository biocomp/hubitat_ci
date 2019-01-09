package biocomp.hubitatCiTest.emulation.appApi;

import groovy.lang.Closure;

import java.util.Map;

/**
 * Entry point for all preferences() related calls.
 */
public interface PreferencesSource extends Preferences, Page, Section {
    /**
     * Adds dynanmic page
     *
     * @param options. Same as for page(), and one extra:
     *                 refreshInterval (Integer) - refreshes the specific page of the SmartApp on
     *                 the mobile device for the integer number of seconds
     *                 */
    default Map dynamicPage(Map options, Closure makeContents) {
        return null;
    }

    /**
     * Preferences for the appApi
     */
    default Object preferences(Closure makeContents) {
        return preferences(null, makeContents);
    }

    /**
     * Preferences for the appApi
     *
     * @param options. Not clear which ones are supported.
     */
    default Object preferences(Map options, Closure makeContents) {
        return null;
    }
}
