package biocomp.hubitatCiTest.emulation

/*
Methods from reflected real code:
Methods:
 * # All derived from BaseExecutor skipped #

Meta/Common:

Main methods/getters:
++ public com.hubitat.app.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.addChildApp(java.lang.String,java
.lang.String,java.lang.String),
++ public com.hubitat.app.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.addChildApp(java.lang.String,java
.lang.String,java.lang.String,java.util.Map),
++ public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java
.lang.String,java.lang.String),
++ public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java
.lang.String,java.lang.String,java.lang.Long),
++ public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.addChildDevice(java.lang.String,java
.lang.String,java.lang.String,java.lang.Long,java.util.Map),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.apiServerUrl(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.app(java.util.Map),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.app(java.lang.String,java.lang.String,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.app(java.util.Map,java.lang.String,java.lang.String,java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.appSetting(java.lang.String),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.createAccessToken(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.definition(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.definition(java.util.Map,groovy.lang.Closure),
++ public void com.hubitat.hub.executor.AppExecutor.deleteChildApp(java.lang.Long),
++ public void com.hubitat.hub.executor.AppExecutor.deleteChildDevice(java.lang.String),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.dynamicPage(java.util.Map,groovy.lang.Closure),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.fullApiServerUrl(java.lang.String),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.fullLocalApiServerUrl(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getAllChildApps(),
++ public java.util.List com.hubitat.hub.executor.AppExecutor.getAllChildDevices(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getAllDeviceIds(),
public com.hubitat.app.DeviceWrapperList com.hubitat.hub.executor.AppExecutor.getAllDevicesByCapability(java.lang.String),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.getApiServerUrl(),
public com.hubitat.app.InstalledAppWrapper com.hubitat.hub.executor.AppExecutor.getApp(),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.getAppMappings(),
public com.hubitat.hub.domain.AppType com.hubitat.hub.executor.AppExecutor.getAppType(),
public java.lang.String com.hubitat.hub.executor.AppExecutor.getAppTypeType(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildAppById(java.lang.Long),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildAppByLabel(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getChildApps(),
++ public com.hubitat.app.ChildDeviceWrapper com.hubitat.hub.executor.AppExecutor.getChildDevice(java.lang.String),
public java.util.List com.hubitat.hub.executor.AppExecutor.getChildDevices(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getCurrentPage(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getCurrentSection(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getDashboard(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getDashboardEventsMap(java.util.List,java.lang.Integer),
public com.hubitat.app.DeviceWrapper com.hubitat.hub.executor.AppExecutor.getDeviceById(java.lang.Long),
public static java.lang.String com.hubitat.hub.executor.AppExecutor.getEXECUTOR_TYPE(),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.getFullApiServerUrl(),
++ public java.lang.String com.hubitat.hub.executor.AppExecutor.getFullLocalApiServerUrl(),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.getHubUID(),
public java.util.List com.hubitat.hub.executor.AppExecutor.getInstalledCapabilities(),
++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.getLocalApiServerUrl(),
++ public java.util.List com.hubitat.hub.executor.AppExecutor.getLocationEventsSince(java.lang.String,java.util.Date),
++ public java.util.List com.hubitat.hub.executor.AppExecutor.getLocationEventsSince(java.lang.String,java.util.Date,
java.util.Map),
public java.util.List com.hubitat.hub.executor.AppExecutor.getPages(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.getParent(),
public java.util.Map com.hubitat.hub.executor.AppExecutor.getPreferences(),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.getState(),
++ public com.hubitat.app.DeviceWrapper com.hubitat.hub.executor.AppExecutor.getSubscribedDeviceById(java.lang.Long),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.getSunriseAndSunset(),
++ public java.util.Map com.hubitat.hub.executor.AppExecutor.getSunriseAndSunset(java.util.Map),
public java.util.List com.hubitat.hub.executor.AppExecutor.getThirdPartyHubIPList(),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.href(java.util.Map,java.lang.String),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.lang.String,java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.input(java.util.Map,java.lang.String,java.lang.String),

public boolean com.hubitat.hub.executor.AppExecutor.isAppInstalled(java.lang.String,java.lang.String),
public boolean com.hubitat.hub.executor.AppExecutor.isAppInstalled(java.lang.String,java.lang.String,java.lang.String),
public boolean com.hubitat.hub.executor.AppExecutor.isSystemTypeOrHubDeveloper(),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.label(java.util.Map),

++ public static java.lang.String com.hubitat.hub.executor.AppExecutor.localApiServerUrl(java.lang.String),
public java.lang.Object com.hubitat.hub.executor.AppExecutor.mappings(groovy.lang.Closure),
public void com.hubitat.hub.executor.AppExecutor.metadata(groovy.lang.Closure),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.mode(java.util.Map),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.lang.String,java.lang.String,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.page(java.util.Map,java.lang.String,java.lang.String,groovy.lang.Closure),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.paragraph(java.lang.String),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.paragraph(java.util.Map,java.lang.String),

public java.lang.Object com.hubitat.hub.executor.AppExecutor.path(java.lang.String,groovy.lang.Closure),

++ public void com.hubitat.hub.executor.AppExecutor.pause(java.lang.Long),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.preferences(groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.preferences(java.util.Map,groovy.lang.Closure),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.render(),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.render(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.saveState(),

++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.lang.String,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.util.Map,groovy.lang.Closure),
++ public java.lang.Object com.hubitat.hub.executor.AppExecutor.section(java.util.Map,java.lang.String,groovy.lang.Closure),

++ public void com.hubitat.hub.executor.AppExecutor.sendEvent(java.util.Map),
++ public static void com.hubitat.hub.executor.AppExecutor.sendEvent(com.hubitat.app.DeviceWrapper,java.util.Map),
++ public static void com.hubitat.hub.executor.AppExecutor.sendEvent(java.lang.String,java.util.Map),
++ public void com.hubitat.hub.executor.AppExecutor.sendSms(java.lang.String,java.lang.String),
++ public void com.hubitat.hub.executor.AppExecutor.sendSmsMessage(java.lang.String,java.lang.String),

++ public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.Object),
++ public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.String,java.lang.Object),
++ public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,java.lang.String,java.lang.Object,java
.util.Map),

++ public static java.lang.Long com.hubitat.hub.executor.AppExecutor.timeOffset(java.lang.Number),
++ public static java.lang.Long com.hubitat.hub.executor.AppExecutor.timeOffset(java.lang.String),
++ public static java.util.Date com.hubitat.hub.executor.AppExecutor.timeTodayAfter(java.lang.String,java.lang.String),
++ public static java.util.Date com.hubitat.hub.executor.AppExecutor.timeTodayAfter(java.lang.String,java.lang.String,
java.util.TimeZone),

++ public void com.hubitat.hub.executor.AppExecutor.unsubscribe(),
++ public void com.hubitat.hub.executor.AppExecutor.unsubscribe(com.hubitat.app.DeviceWrapper),
++ public void com.hubitat.hub.executor.AppExecutor.unsubscribe(java.util.List)


Setters:
public void com.hubitat.hub.executor.AppExecutor.setApp(com.hubitat.app.InstalledAppWrapper),
public void com.hubitat.hub.executor.AppExecutor.setAppMappings(java.lang.Object),
public void com.hubitat.hub.executor.AppExecutor.setAppType(com.hubitat.hub.domain.AppType),
public void com.hubitat.hub.executor.AppExecutor.setAppTypeType(java.lang.String),
public void com.hubitat.hub.executor.AppExecutor.setChildApps(java.util.List),
public void com.hubitat.hub.executor.AppExecutor.setCurrentPage(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setCurrentSection(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setLocationMode(java.lang.String),
public void com.hubitat.hub.executor.AppExecutor.setPages(java.util.List),
public void com.hubitat.hub.executor.AppExecutor.setPreferences(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setState(java.util.Map),
public void com.hubitat.hub.executor.AppExecutor.setThirdPartyHubIPList(java.util.List),
*/

trait AppSection {
    /**
     * Allows the user to select devices or enter values to be used during execution of the App.
     *
     * Inputs are the most commonly used preference elements.
     * They can be used to prompt the user to select devices that provide a certain capability, or devices of a specific type, or constants of various kinds.
     * Input element method calls take two forms.
     *
     * The “shorthand” form passes in the name and type unnamed as the required first two parameters,
     * and any other arguments as named options.
     *
     * The second form explicitly specifies the name of each argument.
     *
     * @param options. Valid options are:
     * capitalization(only iOS devices) String - if the input is a text field, this controls the behavior of the
     *     auto-capitalization on the mobile device. "none" specifies to not enable auto-capitalization for any word. "sentences" will capitlize the first letter of each sentence. "all" will use all caps. "words" will capitalize every word. The default is "words".
     * defaultValue (Object) - if specified, a default value for this input.
     * name (String) - name of variable that will be created in this SmartApp to reference this input.
     * title (String) - title text of this element.
     * description (String) - default value of the input element.
     * multiple (Boolean) - true or false to specify this input allows selection of multiple devices of the input type
     * (if you have more than one). Defaults to true. For example, in the motion sensor example above, setting this to true will allow you to select more than one motion sensor, provided you have more than one.
     * range
     *     A range for numeric (number and decimal) that restricts the valid entries to values within the range. For exampe, range: "2..7" will only allow inputs between 2 and 7 (inclusive). range: "-5..8" allows inputs between -5 and 8. A value of “*” will allow any numeric value on that side of the range. Use range: "*..*" to allow the user to enter any value, negative or positive. Note that without specifying a range that allows negative numbers, the mobile clients will only show a keypad to allow positive numeric entries.
     * required (Boolean) - true to require the selection of a device for this input or false to not require selection.
     * submitOnChange (Boolean) - true to force a page refresh after input selection or false to not refresh the page.
     *     This is useful when creating a dynamic input page.
     * options (List) - used in conjunction with the enum input type to specify the values the user can choose from.
     *     Example: options: ["choice 1", "choice 2", "choice 3"].
     * type (String) - one of the names from the following table:
     *     capability.capabilityName:
     *          Prompts for all the devices that match the specified capability.
     *          See the Preferences
     *          Reference column of the Capabilities Reference table for possible values.
     *     device.deviceTypeName
     *          Prompts for all devices of the specified type.
     *          See Using device-specific inputs for more information.
     *     bool 	A true or false value (value returned as a boolean).
     *     boolean 	A "true" or "false" value (value returned as a string). It’s recommended that you use the “bool” input instead, since the simulator and mobile support for this type may not be consistent, and using “bool” will return you a boolean (instead of a string). The “boolean” input type may be removed in the near future.
     *     decimal 	A floating point number, i.e. one that can contain a decimal point
     *     email 	An email address
     *     enum 	One of a set of possible values. Use the options element to define the possible values.
     *     hub 	Prompts for the selection of a hub
     *     icon 	Prompts for the selection of an icon image
     *     number 	An integer number, i.e. one without decimal point
     *     password 	A password string. The value is obscured in the UI and encrypted before storage
     *     phone 	A phone number
     *     time 	A time of day. The value will be stored as a string in the Java SimpleDateFormat (e.g., “2015-01-09T15:50:32.000-0600”)
     *     text 	A text value
     * @param name - name of the input (could also be passed as options)
     * @param type - type of the input (see option's type values)
     * @return
     */
    abstract def input(Map options = null, String name = null, String type = null)

    /**
     * @param name. Name.
     * @param options. Valid options are:
     *
     title (String) - the title of the element.
     required (Boolean) - true or false to specify this input is required. Defaults to false.
     description (String) - the secondary text of the element
     style (String) - Controls how the link will be handled.
     Specify “external” to launch the link in the mobile device’s browser.
     Specify “embedded” to launch the link within the SmartThings mobile application.
     Specify “page” to indicate this is a preferences page.
     If style is not specified, but page is, then style:"page" is assumed.
     If style is not specified, but url is, then style:"embedded" is assumed.
     Currently, Android does not support the “external” style option.
     url (String) - The URL of the page to visit. You can use query parameters to pass additional information to the
     URL (for example, http://someurl.com?param1=value1&param2=value1).
     params (Map) - Use this to pass parameters to other preference pages. If doing this, make sure your page
     definition method accepts a single parameter (that will be this params map). See the page-params-by-href example at the end of this document for more information.
     page (String) - Used to link to another preferences page. Not compatible with the external option.
     image (String) - URL of an image to use, if desired.

     * @return
     */
    abstract def href(String name = null, Map options = null)

    /**
     * Allows the user to name the app installation.
     * Automatically generated by single-page preferences.
     *
     * @param options. Valid values:
     title (String) - the title of the label field.
     description (String) - the text in the input field.
     required (Boolean) - true or false to specify this input is required. Defaults to false. Defaults to true.
     image (String) - URL to an image to use, if desired.
     */
    abstract def label(Map options)

    /**
     * Allows the user to select which modes the app executes in.
     * Automatically generated by single-page preferences.
     *
     * @param options. Valid options are:
     * title (String) - the title of the mode field.
     * required (Boolean) - true or false to specify this input is required. Defaults to false.
     * multiple (Boolean) - true or false to specify this input allows selection of multiple values. Defaults to true.
     * image (String) - URL of an image to use, if desired.
     * @return
     */
    abstract def mode(Map options)

    /**
     * Add a paragraph to the section
     * @param options.Valid options are:
     *
     * title (String) - The title of the paragraph.
     * image (String) - URL of image to use, if desired.
     * required (Boolean) - true or false to specify this input is required. Defaults to false.
     */
    abstract def paragraph(Map options)

    /**
     * Add this text as paragraph to the section*/
    abstract def paragraph(String text)
}

trait AppPage {
    /**
     * Adds section to a page*/
    abstract def section(String sectionTitle = null, @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = AppSection)
            Closure makeContents)

    /**
     * Adds section to a page
     *
     * @param sectionTitle
     * @param options. valid options are:
     *
     * hideable (Boolean) - Pass true to allow the section to be collapsed. Defaults to false.
     * hidden (Boolean) - Pass true to specify the section is collapsed by default. Used in conjunction with hideable.
     *      Defaults to false.
     * mobileOnly (Boolean) - Pass true to suppress this section from the IDE simulator. Defaults to false.
     */
    abstract def section(Map options, String sectionTitle = null,
                         @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = AppSection) Closure makeContents)
}

trait AppDynamicPage extends
        AppPage
{
}

trait AppPreferences {
    /**
     * Adds page of settings.*/
    abstract def page(String name, String title, @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = AppPage) Closure
            makeContents = null)

    /**
     * Adds page of settings.
     *
     * @param options. Valid options are:
     name (required) (String) - Identifier for this page.
     title (String) - The display title of this page.
     nextPage (String) - Used on multi-page preferences only. Should be the name of the page to navigate to next.
     install (Boolean) - Set to true to allow the user to install this app from this page. Defaults to false. Not
     necessary for single-page preferences.
     uninstall (Boolean) - Set to true to allow the user to uninstall from this page. Defaults to false. Not necessary
     for single-page preferences.
     */
    abstract def page(Map options, @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = AppPage) Closure makeContents)


    /**
     * Creates dynamic page. In this case, page's 'name' must point to a method.
     * @param options (see other page() method). But 'name' must be a name of method that creates dynamic page.
     * @return
     */
    abstract def page(Map options)

    /**
     * Adds section to single-page app (one parent page is assumed)*/
    abstract def section(String sectionTitle = null, @DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = AppSection)
            Closure makeContents)

    /**
     * Adds section to a single-page app (one parent page is assumed)
     *
     * @param sectionTitle
     * @param options. valid options are:
     *
     * hideable (Boolean) - Pass true to allow the section to be collapsed. Defaults to false.
     * hidden (Boolean) - Pass true to specify the section is collapsed by default. Used in conjunction with hideable.
     *      Defaults to false.
     * mobileOnly (Boolean) - Pass true to suppress this section from the IDE simulator. Defaults to false.
     */
    abstract def section(Map options, String sectionTitle = null, @DelegatesTo(strategy = Closure.DELEGATE_ONLY,
            value = AppSection) Closure makeContents)

}