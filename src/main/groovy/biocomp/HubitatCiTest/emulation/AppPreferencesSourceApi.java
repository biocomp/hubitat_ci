package biocomp.hubitatCiTest.emulation;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

import java.util.Map;

/**
 * Entry point for all preferences() related calls.
 */
public interface AppPreferencesSourceApi {
    /**
     * Adds dynanmic page
     *
     * @param options. Same as for page(), and one extra:
     *                 refreshInterval (Integer) - refreshes the specific page of the SmartApp on
     *                 the mobile device for the integer number of seconds.
     */
    default Map dynamicPage(Map options, Closure makeContents) {
        return null;
    }

    /**
     * Preferences for the app
     */
    default Object preferences(Closure makeContents) {
        return preferences(null, makeContents);
    }

    /**
     * Preferences for the app
     *
     * @param options. Not clear which ones are supported.
     */
    default Object preferences(Map options, Closure makeContents) {
        return null;
    }
}
