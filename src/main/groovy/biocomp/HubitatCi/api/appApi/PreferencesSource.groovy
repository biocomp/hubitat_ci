package me.biocomp.hubitat_ci.api.app_api

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
     * Preferences for the app_api
     */
    abstract def preferences(Closure makeContents)

    /**
     * Preferences for the app_api
     *
     * @param options. Not clear which ones are supported.
     *      oauthPage (String) - name of a page that's needed for OAuth: "deviceAuthorization"
     *          Example:
     *          ----
     *          preferences(oauthPage: "deviceAuthorization") {*     // deviceAuthorization page is simply the devices to authorize
     *     page(name: "deviceAuthorization", title: "", nextPage: "instructionPage",
     *          install: false, uninstall: true) {*         section("Select Devices to Authorize") {*             input "switches", "capability.switch", title: "Switches:", required: false
     *             input "motions", "capability.motionSensor", title: "Motion Sensors:", required: false
     *}
     *
     *     }
     *
     *     page(name: "instructionPage", title: "Device Discovery", i: true) {
     *     ction() {
     *             paragraph "Some other information"*         }
     *     }
     * }
     *          ----
     *      
     */
    abstract def preferences(Map options, Closure makeContents)
}
