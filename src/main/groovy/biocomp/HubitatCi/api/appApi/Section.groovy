package biocomp.hubitatCi.api.appApi

interface Section {
    /**
     * Allows the user to select devices or enter values to be used during execution of the App.
     * <p>
     * Inputs are the most commonly used preference elements.
     * They can be used to prompt the user to select devices that provide a certain capability, or devices of a specific type, or constants of various kinds.
     * Input element method calls take two forms.
     * <p>
     * The "shorthand" form passes in the name and type unnamed as the required first two parameters,
     * and any other arguments as named options.
     * <p>
     * The second form explicitly specifies the name of each argument.
     *
     * @param options. Valid options are:
     * <ul>
     *      <li><code>capitalization</code>(only iOS devices) String - if the input is a text field, this controls the behavior of the
     *          auto-capitalization on the mobile device. "none" specifies to not enable auto-capitalization for any word. "sentences" will capitlize the first letter of each sentence. "all" will use all caps. "words" will capitalize every word. The default is "words".</li>
     *      <li><code>defaultValue</code> (Object) - if specified, a default value for this input.</li>
     *      <li><code>name</code> (String) - name of variable that will be created in this SmartApp to reference this input.</li>
     *      <li><code>title</code> (String) - title text of this element.</li>
     *      <li><code>description</code> (String) - default value of the input element.</li>
     *      <li><code>multiple</code> (Boolean) - true or false to specify this input allows selection of multiple devices of the input type
     *          (if you have more than one). Defaults to true. For example, in the motion sensor example above, setting this to true will allow you to select more than one motion sensor, provided you have more than one.</li>
     *      <li><code>range</code>
     *          A range for numeric (number and decimal) that restricts the valid entries to values within the range. For exampe, range: "2..7" will only allow inputs between 2 and 7 (inclusive). range: "-5..8" allows inputs between -5 and 8. A value of "*" will allow any numeric value on that side of the range. Use range: "*..*" to allow the user to enter any value, negative or positive. Note that without specifying a range that allows negative numbers, the mobile clients will only show a keypad to allow positive numeric entries.</li>
     *      <li><code>required</code> (Boolean) - true to require the selection of a device for this input or false to not require selection.</li>
     *      <li><code>submitOnChange</code> (Boolean) - true to force a page refresh after input selection or false to not refresh the page.
     *          This is useful when creating a dynamic input page.</li>
     *      <li><code>options</code> (List) - used in conjunction with the enum input type to specify the values the user can choose from.
     *          Example: <code>options: ["choice 1", "choice 2", "choice 3"].</code></li>
     *       <li><code>type</code> (String) - one of the names from the following table:
     *       <ul>
     *       <li><code>capability.capabilityName</code>:
     *       <p>
     *          Prompts for all the devices that match the specified capability.
     *          See the Preferences reference column of the Capabilities Reference table for possible values.
     *        </li>
     *       <li><code>device.deviceTypeName</code><br/>
     *                 Prompts for all devices of the specified type.<br/>
     *                 See Using device-specific inputs for more information.</li>
     *        <li><code>bool</code> 	A true or false value (value returned as a boolean).</li>
     *        <li><code>boolean</code> 	A "true" or "false" value (value returned as a string). It’s recommended that you use the "bool" input instead, since the simulator and mobile support for this type may not be consistent, and using "bool" will return you a boolean (instead of a string). The "boolean" input type may be removed in the near future.</li>
     *        <li><code>decimal</code> 	A floating point number, i.e. one that can contain a decimal point</li>
     *        <li><code>email</code> 	An email address</li>
     *        <li><code>enum</code> 	One of a set of possible values. Use the options element to define the possible values.</li>
     *        <li><code>hub</code> 	Prompts for the selection of a hub</li>
     *        <li><code>icon</code> 	Prompts for the selection of an icon image</li>
     *        <li><code>number</code> 	An integer number, i.e. one without decimal point</li>
     *        <li><code>password</code> 	A password string. The value is obscured in the UI and encrypted before storage</li>
     *        <li><code>phone</code> 	A phone number</li>
     *        <li><code>time</code> 	A time of day. The value will be stored as a string in the Java SimpleDateFormat (e.g., "2015-01-09T15:50:32.000-0600")</li>
     *        <li><code>text</code> 	A text</li>
     *            </ul>
     *       </li>
     *       <li>width (int) - default = 12.
     *          <p>
     *          Page has with of 12, and if this value is less than 12,
     *          next input or label can be put on the same line (if their width is small enough).
     *          <p>
     *          ${@link https://community.hubitat.com/t/tabular-inputs-as-in-thermostat-manager/7482 }
     *          </li>
     * <ul>
     * @param name     - name of the input (could also be passed as options)
     * @param type     - type of the input (see option's type values)
     */
    abstract def input(Map options, String name, String type)

    /**
     * @see #input(Map, String, String)
     */
    abstract def input(String name, String type)

    /**
     * @see #input(Map, String, String)
     */
    abstract def input(Map options)

    /**
     * @param name.    Name.
     * @param options. Valid options are:
     *                 <p>
     *                 <ul>
     *                 <li><code>title</code> (String) - the title of the element.</li>
     *                 <li><code>required</code> (Boolean) - true or false to specify this input is required. Defaults to false.</li>
     *                 <li><code>description</code> (String) - the secondary text of the element</li>
     *                 <li><code>style</code> (String) - Controls how the link will be handled.</li>
     *                 <ul>
     *                  <li>Specify "external" to launch the link in the mobile device’s browser.</li>
     *                  <li>Specify "embedded" to launch the link within the SmartThings mobile application.</li>
     *                  <li>Specify "page" to indicate this is a preferences page.</li>
     *                  </ul>
     *                  <p>
     *                  If style is not specified, but page is, then style:"page" is assumed.
     *                  <p>
     *                  If style is not specified, but url is, then style:"embedded" is assumed.
     *                  Currently, Android does not support the "external" style option.
     *                 <li><code>url</code> (String) - The URL of the page to visit. You can use query parameters to pass additional information to the</li>
     *                  URL (for example, http://someurl.com?param1=value1&param2=value1).
     *                 <li><code>params</code> (Map) - Use this to pass parameters to other preference pages. If doing this, make sure your page</li>
     *                  definition method accepts a single parameter (that will be this params map). See the page-params-by-href example at the end of this document for more information.
     *                 <li><code>page</code> (String) - Used to link to another preferences page. Not compatible with the external option.</li>
     *                 <li><code>image</code> (String) - URL of an image to use, if desired.</li>
     *                 </ul>
     */
    abstract def href(Map options, String title)


    /**
     * Creates a link to another page
     * @param nextPageName - name of existing local page.
     * @see #href(Map, String)
     */
    abstract def href(String nextPageName)

    /**
     * @see #href(Map, String)
     */
    abstract def href(Map options)

    /**
     * Allows the user to name the appApi installation.
     * Automatically generated by single-page preferences.
     *
     * @param options. Valid values:
     * <ul>
     * <li><code>title</code> (String) - the title of the label field.</li>
     * <li><code>description</code> (String) - the text in the input field.</li>
     * <li><code>required</code> (Boolean) - true or false to specify this input is required. Defaults to false. Defaults to true.</li>
     * <li><code>image</code> (String) - URL to an image to use, if desired.</li>
     * <li><code>width</code> (int) - default = 12.
     *      <p>Page has with of 12, and if this value is less than 12,
     *      next input or label can be put on the same line (if their width is small enough).
     *      <p>{@link: https://community.hubitat.com/t/tabular-inputs-as-in-thermostat-manager/7482 }
     * </li>
     * </ul>
     */
    abstract def label(Map options)

    /**
     * Allows the user to select which modes the appApi executes in.
     * Automatically generated by single-page preferences.
     *
     * @param options. Valid options are:
     *                 <ul>
     *                 <li><code>name</code> (String) - name (?) of the field</li>
     *                 <li><code>title</code> (String) - the title of the mode field.</li>
     *                 <li><code>required</code> (Boolean) - true or false to specify this input is required. Defaults to false.</li>
     *                 <li><code>multiple</code> (Boolean) - true or false to specify this input allows selection of multiple values. Defaults to true.</li>
     *                 <li><code>image</code> (String) - URL of an image to use, if desired.</li>
     *                 </ul>
     */
    abstract def mode(Map options)

    /**
     * Add a paragraph to the section
     *
     * @param options.Valid options are:
     *                      <p>
     *                      <ul>
     *                      <li><code>title</code> (String) - The title of the paragraph.</li>
     *                      <li><code>image</code> (String) - URL of image to use, if desired.</li>
     *                      <li><code>required</code> (Boolean) - true or false to specify this input is required. Defaults to false.</li>
     *                      </ul>
     */
    abstract def paragraph(Map options, String text)

    /**
     * Add this text as paragraph to the section
     * @see #paragraph(Map, String)
     */
    abstract def paragraph(String text)

    abstract def app(Map options)
    abstract def app(String name, String namespace, String title)
    abstract def app(Map options, String name, String namespace, String title)
}
